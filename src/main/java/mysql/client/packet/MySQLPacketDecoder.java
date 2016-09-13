package mysql.client.packet;

import io.netty.buffer.ByteBuf;
import mysql.client.ProtocolUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 类似Netty的各种Coder
 *
 * @author yangqf
 * @version 1.0 2016/9/12
 */
public class MySQLPacketDecoder{
    public static final int HEADER_LENGTH = 4;

    private byte[] packetHeaderBytes = new byte[4];
    private Socket socket;
    private DataInputStream io;
    private int packetSequence;

    public void setPacketSequence(int packetSequence){
        this.packetSequence = packetSequence;
    }

    public MySQLPacketDecoder(Socket socket){
        this.socket = socket;
        try{
            this.io = new DataInputStream(socket.getInputStream());
        }catch(IOException e){
            e.printStackTrace();
            throw new IllegalStateException("get socket input stream err!!!");
        }
    }

    public ByteBuf readPacket() throws IOException{
        //mysqlio重写了DataInputStream.readFully这个方法,加了个读取字节返回值
        //注意read(bytes)和readFully(bytes),前者可能只读取了部分,后者是读取全部
        io.readFully(packetHeaderBytes);

        int packetLength = (this.packetHeaderBytes[0] & 0xff) + ((this.packetHeaderBytes[1] & 0xff) << 8) + ((this.packetHeaderBytes[2] & 0xff) << 16);

        byte[] bytes = new byte[packetLength + 1];
       io.readFully(bytes, 0, packetLength);
        bytes[packetLength] = 0;//其实数据只有packetLength, 末尾补全一个0, 应为c++ 字符串/0 结尾么?
//        if(realReadCount != packetLength){
//            io.close();
//            throw new IllegalStateException("mysql 返回数据不完整,和包头length不比配, excepted length = " + );
//        }

        ByteBuf byteBuf = ProtocolUtils.createLittleByteBuf(bytes.length);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }

    public void sendPacket(Packet packet, int packetLength) throws IOException{
        ByteBuf buf = packet.serialized();
        send(buf, packetLength);
    }

    public void send(ByteBuf buf, int packetLength) throws IOException{
        int position = buf.writerIndex();
        buf.readerIndex(0);
        buf.writerIndex(0);

        int size;
        if(packetLength > 0){
            size = packetLength - 4;
        }else {
            size = position - 4;
        }
        buf.writeByte((byte) (size & 0xff));
        buf.writeByte((byte) (size >>> 8));
        buf.writeByte((byte) (size >>> 16));
        buf.writeByte(packetSequence);

        socket.getOutputStream().write(buf.array(), 0, packetLength);//带上长度,不要多发数据
        socket.getOutputStream().flush();
    }

    /**
     * 根据参数c的class创建对象并调用对应的deserialized方法
     * @param buf
     * @param c
     * @param <T>
     * @return
     */
    public <T extends Packet> T decoder(ByteBuf buf, Class<T> c){
        try{
            T t = c.newInstance();
            t.deserialized(buf);
            return t;
        }catch(InstantiationException e){
            e.printStackTrace();
        }catch(IllegalAccessException e){
            e.printStackTrace();
        }
        return null;
    }

    private void x(){
        decoder(null, Packet.class);
    }

}
