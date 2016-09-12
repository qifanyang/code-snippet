package mysql.client;

import com.mysql.jdbc.*;
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

/**
 * @author yangqf
 * @version 1.0 2016/9/12
 */
@Data
public class Session{

    private String url;
    private String user = "root";
    private String passwd = "";
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

        //*3 是因为使用utf8编码?
        int packLength = ((userLength + passwordLength + databaseLength) * 3) + 7 + Packet.HEADER_LENGTH + Packet.AUTH_411_OVERHEAD;

        //if use SSL 发送SSLConnection

        ByteBuf fromServer = null;
        ArrayList<ByteBuf> toServer = new ArrayList<ByteBuf>();
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
                        byte[] seedBytes = initialHandshakePacket.getSeed().getBytes();
                        fromServer = ProtocolUtils.allocator.buffer(seedBytes.length).writeBytes(seedBytes);
                    }else {
                        // no challenge so this is a changeUser call
                        // 改变用户不用challege
                    }

                }else {
                    //XXX
                }

                //call plugin
            MysqlNativePasswordPlugin plugin = new MysqlNativePasswordPlugin();
            done = plugin.nextAuthenticationStep(fromServer, toServer, passwd);
            handshakeResponse.setToServer(toServer.get(0));

            //发送 handshakeRespone, 完成握手
            decoder.sendPacket(handshakeResponse, packetSequence);

        }

        if(0 == counter){
            throw new IllegalStateException("认证失败!!!");
        }

    }

}
