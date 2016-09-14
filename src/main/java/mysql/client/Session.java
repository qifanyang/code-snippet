package mysql.client;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import mysql.client.packet.HandshakeResponsePacket;
import mysql.client.packet.InitialHandshakePacket;
import mysql.client.packet.MySQLPacketDecoder;
import mysql.client.packet.Packet;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangqf
 * @version 1.0 2016/9/12
 */
@Data
public class Session{

    private String url;
    private String user = "root";
    private String passwd = "123456";
    private String database = "test";

    private MySQLPacketDecoder decoder;
    private Socket socket;
    private DataInputStream io;
    private int packetSequence;

    public void connect() throws SQLException{
        this.connect(url, user, passwd);
    }

    public void connect(String url, String user, String passwd) throws SQLException{
        if(null == url){
            url = "127.0.0.1";
        }
        socket = new Socket();
        try{
            InetAddress inetAddress = InetAddress.getByName(url);
            InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, 3306);
            socket.connect(inetSocketAddress, 20000);

            if(socket.isConnected()){
                System.out.println("连接到mysql,准备认证");
                io = new DataInputStream(socket.getInputStream());
                decoder = new MySQLPacketDecoder(socket);
            }
        }catch(UnknownHostException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            doHandshake();
        }catch(IOException e){
            e.printStackTrace();
            throw new SQLException("handshake happened exception , e = " + e);
        }
    }

    public void doHandshake() throws IOException, SQLException{
        //连接到server后,服务器会返回InitialHandshakePacket
        ByteBuf buf = decoder.readPacket();
        byte ret = buf.array()[0];
        if((ret & 0xFF) == 0xFF){
            System.out.println("when handshake , server return err packet to reject connect");

            return;
        }
        InitialHandshakePacket initialHandshakePacket = this.decoder.decoder(buf, InitialHandshakePacket.class);

        if((initialHandshakePacket.getServerCapabilities() & Packet.CLIENT_PLUGIN_AUTH) != 0){
            //使用plugin认证
            proceedHandshakeWithPluggableAuthentication(initialHandshakePacket, buf);
        }


    }

    private void proceedHandshakeWithPluggableAuthentication(InitialHandshakePacket initialHandshakePacket, ByteBuf challenge) throws SQLException, IOException{
        HandshakeResponsePacket handshakeResponse = new HandshakeResponsePacket();
        handshakeResponse.setDatabase(database);
        handshakeResponse.setUserName(user);
        boolean skipPassword = false;
        int passwordLength = 16;
        int userLength = (user != null) ? user.length() : 0;
        int databaseLength = (database != null) ? database.length() : 0;

        //*3 是因为使用utf8编码, mysql每个字符最大长度编码为3个字节
        int packLength = ((userLength + passwordLength + databaseLength) * 3) + 7 + Packet.HEADER_LENGTH + Packet.AUTH_411_OVERHEAD;

        //if use SSL 发送SSLConnection

        ByteBuf fromServer = null;
        ArrayList<ByteBuf> toServer = new ArrayList<>();
        Boolean done = null;//是否完成认证
        int counter = 100;
        //server 5.6.28-log
        //默认插件类名,在连接中常量设置 MysqlNativePasswordPlugin
        while(counter-- > 0){//遍历多种插件认证
            if(null == done){
                if(null != challenge){
                    if((initialHandshakePacket.getServerCapabilities() & Packet.CLIENT_PLUGIN_AUTH) != 0){
//                            if (!versionMeetsMinimum(5, 5, 10) || versionMeetsMinimum(5, 6, 0) && !versionMeetsMinimum(5, 6, 2)) {
//                                pluginName = challenge.readString("ASCII", getExceptionInterceptor(), this.authPluginDataLength);
//                            } else {
                        //服务端返回的插件名,如果客户端没有该插件则使用默认插件
                        String pluginName = ProtocolUtils.readNullTerminalString(challenge);
//                            }

                    }
                    byte[] seedBytes = initialHandshakePacket.getSeed().getBytes(ProtocolUtils.charset_utf8);
                    fromServer = ProtocolUtils.createLittleByteBuf(seedBytes.length).writeBytes(seedBytes);
                }else{
                    // no challenge so this is a changeUser call
                    // 改变用户不用challege
                }

            }else{
                //已经done,进行过认证,检查认证结果
                ByteBuf authResult = decoder.readPacket();
                this.packetSequence++;
                byte ret = authResult.readByte();
                if((ret & 0xff) == 0xff){
                    int errno = authResult.readShort();
                    String serverErrorMessage = ProtocolUtils.readNullTerminalString(authResult);
                    System.out.println("errno = " + errno + " , errror message = " + serverErrorMessage);
                }else if((ret & 0xff) == 0){
                    if(!done){
                        throw new SQLException("认证失败");
                    }
                    System.out.println("认证成功...");
                    break;
                }else if((ret & 0xff) == 0xfe){//client和server都具备插件认证能力,服务器可以inform client switch to another auth method
//                        1              [fe]
//                        string[NUL]    plugin name
//                        string[EOF]    auth plugin data
                }

            }

            //call plugin
            MysqlNativePasswordPlugin plugin = new MysqlNativePasswordPlugin();
            done = plugin.nextAuthenticationStep(fromServer, toServer, passwd);
            handshakeResponse.setToServer(toServer.get(0));

            //发送 handshakeRespone, 完成握手
            //注意握手阶段packetSequence需要增加,否则server会断开连接,因为该次会话没有完成,所以序列号需要自增
            ++packetSequence;
            decoder.setPacketSequence(packetSequence);
            decoder.sendPacket(handshakeResponse, packLength);

        }

        if(0 == counter){
            throw new IllegalStateException("认证失败!!!");
        }

    }

    public void resetPacketSequence(){
        this.packetSequence = 0;
        decoder.setPacketSequence(packetSequence);
    }

    public void checkErr(ByteBuf buf) throws SQLException{
        if((buf.getByte(0) & 0xff) == 0xff){
            buf.readByte();
            int errno = buf.readShort();
            String serverErrorMessage = ProtocolUtils.readNullTerminalString(buf);
            System.out.println("errno = " + errno + " , errror message = " + serverErrorMessage);
            throw new SQLException("check err failed !");
        }
    }

    public boolean checkEof(ByteBuf buf){
        if((buf.getByte(0) & 0xff) == 0xfe && buf.writerIndex() < 9){
            short warningCount = buf.readShort();
            short serverStatus = buf.readShort();
            return true;
        }
        return false;
    }


    public ResultSetX executeSQL(String sql) throws SQLException{
        resetPacketSequence();
        int packLength = 4 + 1 + (sql.length() * 3) + 2;
        ByteBuf sendBuf = ProtocolUtils.createEmptyPacket(packLength);
        sendBuf.writeByte(Command.QUERY);
        sendBuf.writeBytes(sql.getBytes(ProtocolUtils.charset_utf8));
        try{
            decoder.send(sendBuf, sendBuf.writerIndex());

            return readResultSet();
        }catch(IOException e){
            throw new SQLException("执行sql发生异常, e = " + e);
        }
    }

    private ResultSetX readResultSet() throws IOException, SQLException{
        ByteBuf resultBuf = decoder.readPacket();
        checkErr(resultBuf);
        //TODO check err packet
        ResultSetX resultSetX = new ResultSetX();
        //读取结果集数据
        int columnCount = (int) ProtocolUtils.readLenencInt(resultBuf);
        //读取字段数据包
        //解析>catalogName,databaseName,tableName,originalTableNameStart,originalTableNameLength 不转换为string
        //Name,originaColum,>skipbyte> charSetNumber>colLength>colTypebyte>colflag>colDecimal>defaultvaluestart(采用string<lenenc>)>
        //
        Field[] fields = new Field[columnCount];
        for(int i = 0; i < columnCount; i++){
            ByteBuf fieldBuf = decoder.readPacket();
            fields[i] = new Field();
            fields[i].deserialized(fieldBuf);
        }
        resultSetX.setFields(fields);

        //checkServerStatusForResult, 也就是eofPacket
        resultBuf = decoder.readPacket();

        //准备读取行数据
        List<RowData> rowDataList = new ArrayList<>();
        resultSetX.setRowDatas(rowDataList);
        RowData rowData = nextRowData(columnCount);
        while(rowData != null){
            rowDataList.add(rowData);
            rowData = nextRowData(columnCount);
        }

        return resultSetX;

    }

    private RowData nextRowData(int columnCount) throws IOException, SQLException{

        //结果集数据可以一次性读取,也可以是流式(We only stream result sets when they are forward-only, read-only, and the fetch size has been set to Integer.MIN_VALUE)
        //准备读取row data, JDBC42ResultSet  RowDataStatic
        byte[][] rowData = new byte[columnCount][];//一行,多条记录使用List
        ByteBuf rowBuf = decoder.readPacket();//每一行数据都是一个packet, 行结束使用EOFPacket作为结束
        checkErr(rowBuf);
        if(checkEof(rowBuf)){
            return null;
        }
        for(int i = 0; i < columnCount; i++){
            int len = (int) ProtocolUtils.readLenencInt(rowBuf);
            if(len == -1){
                rowData[i] = null;
            }else if(len == 0){
                rowData[i] = ProtocolUtils.EMPTY_BYTE_ARRAY;
            }else{
                rowData[i] = new byte[len];
                rowBuf.readBytes(rowData[i]);
            }
        }
        RowData rd = new RowData();
        rd.setInternalRowData(rowData);
        return rd;

    }


}
