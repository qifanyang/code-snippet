package mysql.client;

import com.mysql.jdbc.StringUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * @author yangqf
 * @version 1.0 2016/9/12
 */
public class ProtocolUtils{

    public static Charset charset_utf8 = Charset.forName("utf-8");
    public static ByteBufAllocator allocator = new UnpooledByteBufAllocator(false);

    public static ByteBuf createEmptyPacket(){
        ByteBuf buf = allocator.buffer();
        buf.writeInt(0);
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
}
