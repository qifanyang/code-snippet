package test.core.base;

/**
 * @author yangqf
 * @version 1.0 2016/6/7
 */
public class FinallyTest{

    static int y = 0;

    public static int yy(){
        return y++;
    }

    public static int cc(){
        int x = 1;
        return x++;
    }

    public static int t(){
        int x = 5;
        try{
            x = 9;
            return x;
        }finally{
            x = 11;
        }
    }


    public static void main(String[] args){
        System.out.println(t());
        System.out.println(cc());
    }
}
