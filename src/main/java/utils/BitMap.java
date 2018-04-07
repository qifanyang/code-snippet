package utils;

/**
 * 1.确定第几个int --> 除以32
 * 2.确定第几个bit --> 求除以8的余, 因8是 power of 2, 所以可以使用x&(8-1)
 * Created by yangqifan on 2018/3/18.
 */
public class BitMap {

    private static final int SHIFT = 5; //因为2^5 = 32
    private static final int MASK = 8 - 1;

    private static final int[] bitMap = new int[2];
    public static void main(String[] args) {
        set(45);
        System.out.println(test(45));
        clean(45);
        System.out.println(test(45));
        System.out.println(test(46));
        set(46);
        System.out.println(test(46));
        //<< 比 & 优先级高,要用括号
//        System.out.println(1<<(45&7));
//        System.out.println(1<<45&7);

//        System.out.println(~1<<(45&7));
//        System.out.println(Integer.toBinaryString(~(1<<(45&7))));
//        System.out.println(Integer.toBinaryString((1<<(45&7))));
    }

    private static void set(int x){
        //1.确定第几个int,使用x/32 使用位运算就是 x>>5
        //2.确定几个bit, 使用x%8, 使用位运算就是 x&(8-1)
        //3.设置对应的bit为1, 对1进行位移运算
        bitMap[x>>5] |= 1<<(x&7);
    }

    private static boolean test(int x){
        return (bitMap[x>>5] & (1<<(x&7))) != 0;
    }

    private static void clean(int x){

        bitMap[x>>5] &= ~(1<<(x&7));

    }

}
