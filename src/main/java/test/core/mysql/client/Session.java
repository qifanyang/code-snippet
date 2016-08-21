package test.core.mysql.client;

import com.mysql.jdbc.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.Properties;

/**
 * 会话,通过该会话可以连接数据库,执行sql
 * @author yangqf
 * @version 1.0 2016/8/19
 */
public class Session{

    private Socket socket;
    DataInputStream io;

    private String user = "root";
    private String password = "";
    private String database = "test";

    private byte[] packetHeaderBuf = new byte[4];

    static ByteBufAllocator allocator = new UnpooledByteBufAllocator(false);
    private int packetSequence;

    public Session() {
        init();
    }

    private void init(){
        socket = new Socket();
        try{
            InetAddress inetAddress = InetAddress.getByName("127.0.0.1");
            InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, 3306);
            socket.connect(inetSocketAddress, 20000);

            if(socket.isConnected()){
                System.out.println("连接到mysql,准备认证");
                io = new DataInputStream(socket.getInputStream());
            }
        }catch(UnknownHostException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //连接建立后,开始认证
    public void doHandshake() throws Exception{

        ByteBuf buf = readPacket();
        byte protocolVersion = buf.readByte();
        String serverVersion = readString(buf, "ASCII");
        System.out.println("protocolVersion = " + protocolVersion);
        System.out.println("serverVersion = " + serverVersion);

        long threadId = buf.readUnsignedInt();
        System.out.println("threadId = " + threadId);
        String seed;
        if (protocolVersion > 9) {
            // read auth-plugin-data-part-1 (string[8])
             seed = readString(buf,"ASCII", 8);//种子用来认证是加密用的,使用ssl的话不用这个
            // read filler ([00])
            buf.readByte();
            System.out.println("seed=" + seed);
        } else {
            // read scramble (string[NUL])
             seed = readString(buf, "ASCII");
        }


        int serverCapabilities = 0;
        // read capability flags (lower 2 bytes)
        if (buf.readerIndex() < buf.capacity()) {
             serverCapabilities = buf.readShort();
        }

        //每次读取值,都会根据版本等信息做判断,决定是否读取,所以协议版本很重要,有了版本信息,高版本可以返回
        //更多附加信息,客户端可以根据自身协议版本做响应处理,保证兼容


        //接下来不做任何判断,根据debug路径,直接读取信息
        int serverCharsetIndex = buf.readByte();
        int serverStatus = buf.readShort();
        serverCapabilities |= buf.readShort() << 16;

        int clientParam = 3842703;//该值是根据服务器返回值serverCapabilities和本地connection设置共同确定
//        clientParam |= 0x00200000;
        clientParam &=~0x00100000; // ignore connection attributes
        int authPluginDataLength = buf.readByte();

        // next 10 bytes are reserved (all [00])
//        buf.setPosition(buf.getPosition() + 10);
        buf.readerIndex(buf.readerIndex() + 10);
        String seed2 = readString(buf, "ASCII", authPluginDataLength - 8);
        seed += seed2;
        System.out.println("seed =" +seed );

//        if ((this.serverCapabilities & CLIENT_PLUGIN_AUTH) != 0) {
//            proceedHandshakeWithPluggableAuthentication(user, password, database, buf);
//            return;
//        }
        //准备使用可插拔的验证方式验证客户端,类似filter-chain这种形式
        //插件有MysqlOldPasswordPlugin,MysqlNativePasswordPlugin,MysqlClearPasswordPlugin...

        boolean skipPassword = false;
        int passwordLength = 16;
        int userLength = (user != null) ? user.length() : 0;
        int databaseLength = (database != null) ? database.length() : 0;

        int packLength = ((userLength + passwordLength + databaseLength) * 3) + 7 + 4 + 33;

        String pluginName = readString(buf, "ASCII");
        ByteBuf fromServer = buf.alloc().buffer(seed.getBytes().length).writeBytes(seed.getBytes());

        byte[] bytes = Security.scramble411(password, seed, "utf-8");
        ByteBuf authBuf = buf.alloc().buffer(bytes.length).writeBytes(bytes);
        // write Auth Response Packet
        String enc = "utf-8";
        ByteBuf sendBuf = createSendByteBuf(packLength);
        sendBuf = sendBuf.order(ByteOrder.LITTLE_ENDIAN);
//        last_sent.writeLong(this.clientParam);
//        last_sent.writeLong(this.maxThreeBytes);
        //0x00100000

//        "_runtime_version" -> "1.8.0_65"
//        "_client_version" -> "5.1.38"
//        "_client_name" -> "MySQL Connector Java"
//        "_client_license" -> "GPL"
//        "_runtime_vendor" -> "Oracle Corporation"
        sendBuf.writeInt(clientParam);
        sendBuf.writeInt(16777215);//writeLong(this.maxThreeBytes);
        sendBuf.writeByte(33);//CharsetMapping.MYSQL_COLLATION_INDEX_utf8;
        sendBuf.writeBytes(new byte[23]);

        //写user
        sendBuf.writeBytes(user.getBytes());
        sendBuf.writeByte(0);

        //wite toserver length
        sendBuf.writeByte(0);


        //write database
        sendBuf.writeBytes(database.getBytes());
        sendBuf.writeByte(0);

        sendBuf.writeBytes("mysql_native_password".getBytes());
        sendBuf.writeByte(0);


        //写propertie
        ByteBuf propertieBuf = allocator.heapBuffer(100);
        Properties properties = new Properties();
        properties.setProperty("_runtime_version", "1.8.0_65");
        properties.setProperty("_client_version", "5.1.38");
        properties.setProperty("_client_name", "MySQL Connector Java");
        properties.setProperty("_client_license", "GPL");
        properties.setProperty("_runtime_vendor", "Oracle Corporation");
        Buffer lb = new Buffer(100);
        for(Object p : properties.keySet()){
                lb.writeLenString((String) p);
                lb.writeLenString(properties.getProperty((String) p));
            //mysql buffer 写入字符串时, 不是固定使用int 作为字符串数组长度,优化了的,小于251使用一个字节,小于65536用三个字节
        }

//        sendBuf.writeByte((byte) (lb.getPosition() - 4));
//        sendBuf.writeBytes(lb.getByteBuffer(), 4, lb.getBufLength() - 4);

        send(sendBuf);
//      sendBuf.writeBytes(authBuf); //密码为空,所以没有把fromServer东西写到toServer中



    }

    /**
     * 读取mysql server 返回的数据
     * @return
     * @throws IOException
     */
    private ByteBuf readPacket() throws IOException{
        int count = io.read(packetHeaderBuf);
        if(count < packetHeaderBuf.length){
            //mysql 返回的数据有问题,关掉连接
            io.close();
            return null;
        }

        int packetLength = (this.packetHeaderBuf[0] & 0xff) + ((this.packetHeaderBuf[1] & 0xff) << 8) + ((this.packetHeaderBuf[2] & 0xff) << 16);

        byte[] bytes = new byte[packetLength+1];
        int realReadCount = io.read(bytes);
        bytes[packetLength] = 0;//其实数据只有packetLength, 末尾补全一个0, 应为c++ 字符串/0 结尾么?
        if(realReadCount != packetLength){
            io.close();
            throw new IllegalStateException("mysql 返回数据不完整,和包头length不比配");
        }
        ByteBufAllocator allocator = new  UnpooledByteBufAllocator(false);
        ByteBuf byteBuf = allocator.heapBuffer(bytes.length);
        byteBuf.writeBytes(bytes);
        byteBuf = byteBuf.order(ByteOrder.LITTLE_ENDIAN);//mysql 协议用的小端字节序
        return byteBuf;
    }

    final String readString(ByteBuf buf,String encoding) throws Exception {
        int i = buf.readerIndex();
        int len = 0;
        int maxLen = buf.writerIndex();

        while ((i < maxLen) && (buf.getByte(i) != 0)) {
            len++;
            i++;
        }

        try {
            return StringUtils.toString(buf.array(), buf.readerIndex(), len, encoding);
        } catch (UnsupportedEncodingException uEE) {
            throw uEE;
        } finally {
            buf.readerIndex(buf.readerIndex()+(len + 1));
        }
    }

    String readString(ByteBuf buf,String encoding, int expectedLength) throws Exception {
        if (buf.readerIndex() + expectedLength > buf.writerIndex()) {
            throw new RuntimeException();
        }

        try {
            return StringUtils.toString(buf.array(), buf.readerIndex(), expectedLength, encoding);
        } catch (UnsupportedEncodingException uEE) {
            throw uEE;
        } finally {
            buf.readerIndex(buf.readerIndex()+expectedLength);
        }
    }

    //模拟mysql library send Buffer, 请求消息,前4个字节为header,
    public ByteBuf createSendByteBuf(int size){
        ByteBuf byteBuf = allocator.heapBuffer(size);
        byteBuf = byteBuf.order(ByteOrder.LITTLE_ENDIAN);
        byteBuf.writerIndex(packetHeaderBuf.length);
        return byteBuf;
    }


    public void send(ByteBuf packet) throws IOException{
        this.packetSequence++;
        int position = packet.writerIndex();
        packet.readerIndex(0);
        packet.writerIndex(0);

        int size = position - 4;
        packet.writeByte((byte) (size & 0xff));
        packet.writeByte((byte) (size >>> 8));
        packet.writeByte((byte) (size >>> 16));
        packet.writeByte(packetSequence);

        socket.getOutputStream().write(packet.array());
        socket.getOutputStream().flush();

    }

    private void clearInputSteam() throws IOException{
        int len;
        // Due to a bug in some older Linux kernels (fixed after the patch "tcp: fix FIONREAD/SIOCINQ"), our SocketInputStream.available() may return 1 even
        // if there is no data in the Stream, so, we need to check if InputStream.skip() actually skipped anything.
        while ((len = socket.getInputStream().available()) > 0 && socket.getInputStream().skip(len) > 0) {
            continue;
        }
    }


    private void sendComand(){
    }

    //MysqlIO发送实际的命令
    //发出select查询命令,然后server返回结果,如果有数据线返回有多少列,然后在



}
