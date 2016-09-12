package mysql.client.packet;

import io.netty.buffer.ByteBuf;
import mysql.client.ProtocolUtils;

/**
 * @author yangqf
 * @version 1.0 2016/9/12
 */
public class ErrPacket implements Packet{
    private byte header;
    private short errorCode;
    private byte sqlStateMarker;//string[1]
    private byte[] sqlState;//string[5]
    private String errorMessage;//stirng<eof>

    @Override
    public ByteBuf serialized(){
        return null;
    }

    @Override
    public void deserialized(ByteBuf buf){
        header = buf.readByte();
        errorCode = buf.readShort();

//        if capabilities & CLIENT_PROTOCOL_41 {} 一定成立
        sqlStateMarker = buf.readByte();
        sqlState = buf.copy(buf.readerIndex(), 5).array();
        buf.skipBytes(5);

        errorMessage = ProtocolUtils.readFixLengthString(buf, buf.readableBytes());
    }
}
