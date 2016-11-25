package core.base;

/**
 * 字节码测试
 * 1.声明变量是初始化，字节码会在每个构造方法中生成
 * 2.静态初始化在cinit方法中
 * @author yangqf
 * @version 1.0 2016/11/22
 */
public class AA{
    private int x = 1;

    private static int y = 5;

    {
        System.out.println(20);
    }

    public AA(){
        System.out.println(2);
    }

    public AA(int x){
        System.out.println(2);
    }

    public static void main(String[] args){
        AA aa = new AA(2);
    }
}
