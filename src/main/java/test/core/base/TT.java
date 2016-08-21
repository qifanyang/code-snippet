package test.core.base;

/**
 * @author yangqf
 * @version 1.0 2016/8/20
 */
public class TT{
    public static void main(String[] args){
        int clientParam = 3842703;
        System.out.println(Integer.toBinaryString(clientParam));
        System.out.println(Integer.toBinaryString(~0x00100000));
        clientParam &=~0x00100000;
        System.out.println(clientParam);
        System.out.println(Integer.toBinaryString(clientParam));
        //1011111111111111111111
        //1110101010001010001111
        System.out.println(Integer.toBinaryString(3842703));

        byte[] bytes = "20".getBytes();
        System.out.println( Character.isDigit('2'));
        char c = '2';
//        c -= '0';
        c = (char) (c - '0');
        System.out.println( (int)'2');
        System.out.println();
    }
}
