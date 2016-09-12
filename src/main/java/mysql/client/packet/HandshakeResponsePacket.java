package mysql.client.packet;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import mysql.client.ProtocolUtils;

/**
 * 当client收到InitialHandshake包后,如果不使用SSL直接返回该包,使用SSL先返回SSLRequest
 * @author yangqf
 * @version 1.0 2016/9/12
 */
@Data
public class HandshakeResponsePacket implements Packet{
    //客户端具备的功能,CLIENT_PROTOCOL_41 always set
    private int clientCapability = CLIENT_LONG_PASSWORD | CLIENT_LONG_FLAG | CLIENT_CONNECT_WITH_DB
            | CLIENT_PROTOCOL_41 | CLIENT_TRANSACTIONS | CLIENT_SECURE_CONNECTION | CLIENT_PLUGIN_AUTH;
    private int maxPacketSize = 1 << 24;//最大包大小,一般2^24
    private byte charactSet = 33;//utf8
    private byte[] reserved = new byte[23];//string[23]
    private String userName;//string[NULL]

    private ByteBuf toServer;
    private String database;



    @Override
    public ByteBuf serialized(){
        ByteBuf buf = ProtocolUtils.createEmptyPacket();
        buf.writeInt(clientCapability)
                .writeInt(maxPacketSize)
                .writeByte(charactSet)
                .writeBytes(reserved)//string[23]
                .writeBytes(userName.getBytes(ProtocolUtils.charset_utf8))
                .writeByte(0);

        buf.writeByte(toServer.writerIndex()).writeBytes(toServer.array());

        buf.writeBytes(database.getBytes(ProtocolUtils.charset_utf8)).writeByte(0);
        buf.writeBytes("mysql_native_password".getBytes(ProtocolUtils.charset_utf8)).writeByte(0);

        return buf;
    }

    @Override
    public void deserialized(ByteBuf buf){

    }
}
