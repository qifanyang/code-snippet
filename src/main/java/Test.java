import java.util.List;

/**
 * @author yangqf
 * @version 1.0 2017/2/14
 */
public class Test{
    public static void main(String[] args){

        System.out.println(Integer.toBinaryString(-1));
        System.out.println(Integer.toBinaryString(-1 << 29));
        System.out.println(Integer.toBinaryString((1 << 29)-1));
        System.out.println(Integer.toBinaryString((1 << 29)));
    }
}
