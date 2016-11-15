package core.base;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author yangqf
 * @version 1.0 2016/11/8
 */
public class BigDecimalTest{
    public static void main(String[] args){
        /**
         * 浮点数会有精度问题,因为整数转换为二进制小数会存在类似十进制1/3这种无限循环的问题,或者很长导致无法存储,
         * 然后计算就会导致精度问题
         */

        /**
         * 采用BigDecimal,金融计算都是使用该类
         */
        //BigDecimal 使用一个整数加上一个精度scale来表示, 之所以没有精度问题,是因为都是采用整数计算
        //支持任意精度的原因是这个整数是BigInteger, 任意大整数运算都支持
        //比如3.3+4.5555 可以等价(33000+45555)/10000 精度为4
        //当数字比较小时BigDecimal内部可以不用BigInteger, 使用long就可以了,效率更高
        //BigDecimal常用方法BigDecimal(String s)或者BigDecimal.valueOf(double d)

        BigDecimal a = new BigDecimal("0.1");
        BigDecimal b = new BigDecimal("0.10");
        //结果false, BIgDecimal equals不仅比较值而且还比较scale, 所以0.1 != 0.10
        //感觉有点不合理,但是从BigDecimal实现来看,本来就是由整形部分和scale来组成
        //如果要让0.1==0.10 那么就先要将scale调整到一样,如同浮点数减法的对阶,然后再来比较整形部分
        //compareTo实现方法就是类似这么做的
        //其实equals返回false还有一个原因,因为java定义equals为true的话,那么hashCode一定相等
        //0.1和0.10相等的话则这这两个数的hashcode也要相等,那么全部都要转换到BIgInteger去计算hashcode
        //才能保证0.1和0.10000... 后面任意多个零的hashcode相等,那么对于常用long来实现BigDecimal就没法用了
        //equls语意也是每一项属性都相等,综合考虑所以equals不能判断0.1和0.10
        System.out.println("BigDecimal 比较 (0.1)equals(0.10) = "+a.equals(b));
        System.out.println("BigDecimal 比较 (0.1)compareTo(0.10) = "+(a.compareTo(b)==0));


        BigDecimal bd = new BigDecimal(4);//使用intCompact存储值,scale为0
//        this.intCompact = val;
//        this.scale = 0;
//        this.intVal = null;
        System.out.println(bd);
        BigDecimal bb1 = new BigDecimal("0.55");

        //BigDecimal内部使用了BigInteger,

        //BigInteger

        System.out.println(Long.MAX_VALUE);//九十亿亿  19个数字
        System.out.println(Long.MAX_VALUE+1);//溢出

        //设计一个类支持任意大小的数字计算,采用分组计算,然后额外处理是否有进位和借位
        //Java BigInteger支持
        BigInteger bi = BigInteger.valueOf(Long.MAX_VALUE);
        //使用长度为2的int数组存储
//        mag[0] = highWord;  高位存在低字节
//        mag[1] = (int)val;
        BigInteger intBi = BigInteger.valueOf(Integer.MAX_VALUE);
        //两个biginteger相加时,先要确定符号,如果符号一样那么将两个整形数据相加
        //如同笔算一样,将两个数位数多的写在上面,然后开始从低位开始计算
        //
        System.out.println(bi.add(intBi));

    }
}


    /**
     * Adds the contents of the int arrays x and y. This method allocates
     * a new int array to hold the answer and returns a reference to that
     * array.
     */
//    private static int[] add(int[] x, int[] y) {
//        // If x is shorter, swap the two arrays
//        if (x.length < y.length) {
//            int[] tmp = x;
//            x = y;
//            y = tmp;
//        }
//
//        int xIndex = x.length;
//        int yIndex = y.length;
//        int result[] = new int[xIndex];
//        long sum = 0;
//        if (yIndex == 1) {
//            sum = (x[--xIndex] & LONG_MASK) + (y[0] & LONG_MASK) ;
//            result[xIndex] = (int)sum;
//        } else {
//            // Add common parts of both numbers
//            while (yIndex > 0) {
//   数组高位存的数值低位部分,也遵循高位存低字节规律
// 两个整形的数字相加一定不会大于long, sum>>>32 用于保留进位, result[xIndex]保留没有进为的值, 而且进位会保留
//在sum中,用于参与下一步的计算,而且会参与更高位的运算,可能造成向更高位的进位
//                sum = (x[--xIndex] & LONG_MASK) +
//                        (y[--yIndex] & LONG_MASK) + (sum >>> 32);
//                result[xIndex] = (int)sum;
//            }
//        }
//        // Copy remainder of longer number while carry propagation is required
//        boolean carry = (sum >>> 32 != 0);
//        while (xIndex > 0 && carry)
//            carry = ((result[--xIndex] = x[xIndex] + 1) == 0);
// 将x[]中的值和进位一起加到result中, 可能还会造成进位,而且result[]也无法存储了,就要增长result[]
//进行加法运算遵循十进制规则,进位最多1 所以用x[xIndex]+1, int[0]数组为-1, 其实值为42忆
//
//        // Copy remainder of longer number
//        while (xIndex > 0)
//            result[--xIndex] = x[xIndex];
//
//        // Grow result if necessary
//        if (carry) {
//            int bigger[] = new int[result.length + 1];
//            System.arraycopy(result, 0, bigger, 1, result.length);
//            bigger[0] = 0x01;//进位
//            return bigger;
//        }
//        return result;
//    }