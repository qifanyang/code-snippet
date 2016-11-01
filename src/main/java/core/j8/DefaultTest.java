package core.j8;

/**
 * java8接口新增default方法(非抽象方法),会遇到c++多重继承时继承重复的方法签名,java编译器提供检查
 * 1.默认方法不能重写Object中的方法
 * 2.如果在函数接口中声明一个抽象方法重写Object中方法,改方法比计入函数接口抽象方法数量中,也就是还可以定义一个抽象方法
 *
 * @author yangqf
 * @version 1.0 2016/10/27
 */
public class DefaultTest{

    public static void main(String[] args){
        A sunA = new SunA();
        sunA.d();//调用默认方法,还是使用invokevirtual指令,动态确定调用方法时,不仅要在父类中搜索,还要要加上在接口中搜索
        System.out.println(sunA.equals(1));;
    }

    interface A{
        //java 8接口允许非抽象方法了,叫做虚拟扩展方法,关键字default如同synchronize一样
        //编译后字节码在接口类中,方法调用使用invokevirtual,方法查找行为发生改变,最后到接口中查找
        default void d(){
            System.out.println("i am default method in A....");
        }
        //default方法用来实现lamda表达式,每次写lamda就是像接口中添加default方法,接口中必须存在对应的抽象方法

        //接口中无法重写Object中的方法,这里声明为抽象子类可以不重写,直接调用Object中的方法
        boolean equals(Object obj);

    }

    interface B{
        default void d(){
            System.out.println("i am default method in B....s");
        }
    }

    //无法从两个接口同时继承相同的默认方法,编译会报错,子类必须重写改方法
    static class SunBoth implements A,B{
        @Override
        public void d(){
            A.super.d();//调用其中一个像使用的方法,用户代码需要区分要调用那个版本
        }
    }

    static class SunA extends Father implements A{

    }

    static class Father{
        public void d(){
            System.out.println("i am father d method...");
        }
        //https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.invokevirtual
        //查找方法调用时,父类中的方法优先于接口中的default方法,这样也不会对以前方法产生影响
    }

}
