package mysql.client;

/**
 * java中的窄化转换,以及java处理网络中的无符号数字
 * @author yangqf
 * @version 1.0 2016/9/14
 */
public class JavaConversion{
    public static void main(String[] args){
        //两个字节,有符号, java中的窄化转换,只会保留低目标数据类型长度bit位数,比如int->short, 保留int的低16bit
        //如果short溢出, java带符号扩展将会保留
        short x = (short) 32769;//z^n   2^n-1  包含0所以要减1
        //int 1000000000000001 只显示低16bit, 当(short)时, 变为short 1000000000000001  符号位为1表示负数,
        System.out.println(Integer.toBinaryString(32769));
        System.out.println(Short.MAX_VALUE);
        System.out.println(x);//从字节数据读取出来的数据是两字节无符号数据,short强转并不会丢失符号位,
        System.out.println(Integer.valueOf(x));//XXX 带符号的转换回去要使用0xffff做&运算
        System.out.println(x & 0xffff);//做&0xffff会将符号位等高32位都去掉,也就是把带符号扩展都去掉,补码就变成0.16.0 1000000000000001
        //然后0开头的补码表示整数,所以同原码,所以结果就是32769,又恢复到原来的值


        /**
         *总结:java没有无符号byte,short,int, 当编写网络程序,或者处理数据文件时, 比如c写入的无符号数字, java端只需要
         * 用对应字节的数据读取,然后使用0xff或者0xffff(对应字节数)做&运算, 然后再使用java默认的带符号扩展(即自动转换即可)
         *
         * 1. c->写入无符号双字节 32769
         * 2. java->读取short, 结果值为-32767
         * 3. int x = (-32767)&0xffff;//不能使用Integer.valueOf(-32767),  & 是针对内存中的补码运算
         * 4. 输出x=32769, 还原数据
         */
    }
}
