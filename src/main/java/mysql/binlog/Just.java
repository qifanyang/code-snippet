package mysql.binlog;

import java.util.BitSet;

/**
 * @author yangqf
 * @version 1.0 2016/9/4
 */
public class Just{
    public static void main(String[] args){
        BitSet bs = new BitSet(10);
        System.out.println(bs.size());
        bs.set(3);
        System.out.println(bs.length());
        System.out.println(bs.get(0));
        System.out.println(bs.get(3));

        System.out.println(~0);
    }
}
