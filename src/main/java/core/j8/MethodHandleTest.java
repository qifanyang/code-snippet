package core.j8;

import sun.reflect.Reflection;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @author yangqf
 * @version 1.0 2016/11/1
 */
public class MethodHandleTest{

    public static void main(String[] args) throws Throwable{
        //invokedynamic 返回一个调用点CallSite, name_and_type为 run ()Ljava/lang/runnable
        //所以为函数接口 , lambda表达式创建函数接口对象,该对象实现该接口,然后执行接口调用就执行lambda表达式
        Runnable runnable = () -> System.out.println("i am runnable function interface");
        //使用lambda 编译器会自动创建private static 方法,然后并调用
        //
        runnable.run();

        //集合api,有带有函数接口的参数,当使用lambda时,其实是编译器帮用户做了函数对象的创建
        //集合api中使用lambda缺点有,遍历时无法提前return或break
        //因为遍历的时候执行了方法嵌套调用,foreach中执行accept(就是lambda),return只能终止accept执行
        //使用异常可以跳出for, 但是很丑陋
        //    default void forEach(Consumer<? super T> action) {
        //        Objects.requireNonNull(action);
        //        for (T t : this) {
        //            action.accept(t);
        //        }
        //    }
        String a="abcd";
        //String substring(int beginIndex, int endIndex) 匹配该方法
        MethodType mt= MethodType.methodType(String.class,int.class,int.class);
        //根据MethodType和方法名,从类中查找方法
        MethodHandle handle= MethodHandles.lookup().findVirtual(String.class,"substring",mt);
        //在目标对象上执行方法,类似method.invoke(...)
        System.out.println(handle.invoke(a,1,2)); //输出b

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        System.out.println(lookup.lookupClass());
    }

}
