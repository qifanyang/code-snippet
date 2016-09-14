package mysql.client;

import com.mysql.jdbc.StringUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;

import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

/**
 * @author yangqf
 * @version 1.0 2016/9/12
 */
public class ProtocolUtils{

    public static Charset charset_utf8 = Charset.forName("utf-8");
    private static ByteBufAllocator allocator = new UnpooledByteBufAllocator(false);
    public static byte[] EMPTY_BYTE_ARRAY = new byte[0];

    /**
     *  3 bytes of packet length is zero, and the forth byte value is zero
     * @return
     */
    public static ByteBuf createEmptyPacket(){
        return createEmptyPacket(256);
    }

    public static ByteBuf createEmptyPacket(int size){
        ByteBuf buf = createLittleByteBuf(size);
        buf.writeInt(0);
        return buf;
    }

    public static ByteBuf createLittleByteBuf(){
        return createLittleByteBuf(256);
    }

    public static ByteBuf createLittleByteBuf(int size){
        ByteBuf buf = allocator.buffer(size);
        buf = buf.order(ByteOrder.LITTLE_ENDIAN);
        return buf;
    }

    public static String readNullTerminalString(ByteBuf buf){
        int i = buf.readerIndex();
        int len = 0;
        int maxLen = buf.writerIndex();

        while ((i < maxLen) && (buf.getByte(i) != 0)) {
            len++;
            i++;
        }

        try {
            return StringUtils.toString(buf.array(), buf.readerIndex(), len, "utf-8");
        } catch (UnsupportedEncodingException uEE) {
            throw new IllegalStateException("decoder failed , e = " + uEE);
        } finally {
            buf.readerIndex(buf.readerIndex()+(len + 1));
        }
    }

    public static String readFixLengthString(ByteBuf buf, int expectedLength){
        if (buf.readerIndex() + expectedLength > buf.writerIndex()) {
            throw new RuntimeException();
        }
        try {
            return StringUtils.toString(buf.array(), buf.readerIndex(), expectedLength, "utf-8");
        } catch (UnsupportedEncodingException uEE) {
            throw new IllegalStateException("decoder failed , e = " + uEE);
        } finally {
            buf.readerIndex(buf.readerIndex()+expectedLength);
        }
    }

    public static String readLenencString(ByteBuf buf){
        long len = readLenencInt(buf);
        return readFixLengthString(buf, (int) len);
    }

    public static long readLenencInt(ByteBuf buf){
        int sw = buf.readByte() & 0xff;

        switch (sw) {
            case 251:
                return -1;//特殊值表示空 null_length

            case 252:
                return buf.readShort();

            case 253:{
                byte b0 = buf.readByte();
                byte b1 = buf.readByte();
                byte b2 = buf.readByte();
                return (b0 | b1 << 8 | b2 << 16);
            }
            case 254:{
                byte b0 = buf.readByte();
                byte b1 = buf.readByte();
                byte b2 = buf.readByte();
                byte b3 = buf.readByte();
                byte b4 = buf.readByte();
                byte b5 = buf.readByte();
                byte b6 = buf.readByte();
                byte b7 = buf.readByte();
                return (b0 | b1 << 8 | b2 << 16| b3 << 24| b4 << 32| b5 << 40| b6 << 48| b7 << 56);
            }
            default:
                return sw;
        }
    }

}
