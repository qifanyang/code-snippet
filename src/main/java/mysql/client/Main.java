package mysql.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;

/**
 * @author yangqf
 * @version 1.0 2016/8/19
 */
public class Main{
    public static void main(String[] args) throws Exception{
        Session_Old session = new Session_Old();
        session.doHandshake();
        TimeUnit.SECONDS.sleep(1000);

        testByteBuf();

    }

    private static void testInetAdrress() throws UnknownHostException{


        InetAddress na = InetAddress.getByName("www.baidu.com");
        System.out.println(na.getHostAddress());
        InetAddress[] allByName = InetAddress.getAllByName("www.baidu.com");
        for(InetAddress ia : allByName){
            System.out.println(ia);
        }

        System.out.println(InetAddress.getLocalHost());
        System.out.println(InetAddress.getLoopbackAddress());

    }

    private static void testByteBuf(){
        System.out.println("本地字节序 = " + ByteOrder.nativeOrder());//高位低字节为 BIG-ENDIAN
        byte[] b = new byte[4];
        b[0] = -115;
        System.out.println("141 binary string = "+Integer.toBinaryString(141));//java是带符号扩展, 正数符号位为0,输出不会显示左边的0
        System.out.println("-115 binary string = "+Integer.toBinaryString(-115));//java是带符号扩展
        System.out.println("115 binary string = "+Integer.toBinaryString(115));//java是带符号扩展
        System.out.println((byte) 141);
        System.out.println(Integer.toBinaryString(0xffffffff));

        //输出-128
        //原码 0x80 > 1000 0000 编译是当做整形那么符号位为0,正数反码补码一样
        //反码 0... 1000 0000
        //补码 0... 1000 0000
        //强转byte,则截取低8bit, 1000 0000 ,  java是带符号扩展结果为 1... 1000 0000
        //反码1... 0111 1111
        //原码10.. 1000 0000
        //所以结果为-128
        System.out.println((byte)0x80);

        System.out.println(0x80);

        System.out.println("(int)b[0]&0xff = " + ((int) b[0] & 0x000000ff));//发生了什么?
        //-115原码 1111 0011  > 反码 1000 1100 > 补码 1000 1101
        //java带符号扩展 -115 在内存中扩展为int 11111111111111111111111110001101
        //11111111111111111111111110001101 & 0x000000ff 取低八位 --->  10001101
        //做 | 运算  结果为000000000000000000000000 10001101 --->141
        //mysql readLong 把低字节放在低位
        System.out.println("0xff == 0x000000ff is " + (0xff == 0x000000ff));

        long x = ((long) b[0] & 0xff) | (((long) b[1] & 0xff) << 8) | ((long) (b[2] & 0xff) << 16)
                | ((long) (b[3] & 0xff) << 24);
        System.out.println(x);

//        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.heapBuffer(4);
        ByteBuf byteBuf = UnpooledByteBufAllocator.DEFAULT.heapBuffer(4);
        byteBuf.writeBytes(b);//[-115,0,0,0]
        byteBuf = byteBuf.order(ByteOrder.LITTLE_ENDIAN);//要用返回值,读取会自动反转
        System.out.println("bytebuf order = " + byteBuf.order());
        //大端高位低字节
//        byteBuf = byteBuf.order(ByteOrder.LITTLE_ENDIAN);
        //netty ByteBuf返回的数据永远是BIG-ENDIAN的,虽然调用本地unsafe.getInt()会使用本地字节序来读取数据
        //但是,netty会用来根据ByteOrder.nativeOrder()来判断,如果本地是little-endian,则使用Integer.reverseBytes
        //反转读取的int,这样就把little-endian转为big-endian
        //所以在使用netty处理网络等字节序的时候,需要确定对方字节序,如果不是大端,则读取bytebuf之前修改为小端
        //bytebuf=bytebuf.order(ByteOrder.LITTLE-ENDIAN);
        byteBuf.markReaderIndex();
        System.out.println(Integer.toBinaryString(byteBuf.readInt()));
        byteBuf.resetReaderIndex();
        System.out.println(Integer.toBinaryString(byteBuf.readInt()));
        byteBuf.resetReaderIndex();

    }
}
