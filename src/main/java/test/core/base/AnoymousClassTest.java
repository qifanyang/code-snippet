package test.core.base;

/**
 * @author yangqf
 * @version 1.0 2016/8/4
 */
public class AnoymousClassTest{
    public static void main(String[] args){

        A a = new A(){{
            //这里在子类构造方法中,父类已经构造完毕,可以调用父类的方法
            System.out.println("匿名内部类快");

        }};

        System.out.println(a);
    }

    public static class A{

        {
            System.out.println("执行初始化快");
        }

        public A(){
            System.out.println("call A()");
        }
        {
            System.out.println("执行初始化快1");
        }

    }
}
