package test.mysql.binlog;

import java.util.Arrays;

/**
 * @author yangqf
 * @version 1.0 2016/8/26
 */
public class ByteArrayReversTest{
    public static void main(String[] args){
        byte[] bytes = {1,3,5,6};
        int exceptLength = bytes.length;

        for(int i=0; i < exceptLength/2; i++){
            byte b = bytes[i];
            int end = exceptLength - 1 - i;
            bytes[i] = bytes[end];
            bytes[end] = b;
        }

        System.out.println(Arrays.toString(bytes));

        String s = "dsdddd | dd";
        String[] split = s.split("\\|");
        System.out.println(s.contains("|"));
        System.out.println(s.contains("\\|"));
        System.out.println();
    }
}
