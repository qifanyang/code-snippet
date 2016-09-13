package mysql.binlog;

import java.util.BitSet;

/**
 * @author yangqf
 * @version 1.0 2016/9/4
 */
public class Just{
    public static void main(String[] args){
        System.out.println((2<<23) - 1);
        byte[] bb = {0x5c,0x49, 0x4d, 0x5e,0x4e, 0x58, 0x4f, 0x47};
        System.out.println(new String(bb));
        BitSet bs = new BitSet(10);
        System.out.println(bs.size());
        bs.set(3);
        System.out.println(bs.length());
        System.out.println(bs.get(0));
        System.out.println(bs.get(3));

        System.out.println(~0);

        System.out.println(257&0xff);

        System.out.println(Integer.toBinaryString(5));
        System.out.println(Integer.toBinaryString(Integer.reverse(5)));

        int x = 2;
        System.out.println((2 & ~2) == 0);

        System.out.println(1 << 24);
        System.out.println(256*256*256);

        System.out.println(c(3));
    }

    static int c(int x){
        int r = 0;
        boolean f = false;
        f = ((x!=0) && (r == (r=c(x-1))));
        return r+x;
    }


}
