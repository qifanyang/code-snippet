package core.reflect;

import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandleTest {

    public void print(){
        System.out.println("i am method handle");
        //return null;
    }

    //-Xbootclasspath:bootclasspath ：让jvm从指定的路径中加载bootclass，用来替换jdk的rt.jar。一般不会用到。
    //-Xbootclasspath/a: path ： 被指定的文件追加到默认的bootstrap路径中。否则CallerSensitive不生效
    //-Xbootclasspath/p: path ： 让jvm优先于默认的bootstrap去加载path中指定的class。

    @CallerSensitive
    public static void xx(){
        System.out.println(Reflection.getCallerClass());
    }

    @CallerSensitive
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException {
        MethodHandleTest test = new MethodHandleTest();
        MethodType methodType = MethodType.methodType(void.class);
        MethodHandle methodHandle = MethodHandles.lookup().findVirtual(MethodHandleTest.class, "print", methodType);

        Class x = int.class;
        xx();
        try {
            methodHandle.invoke(test);
        } catch (Throwable e) {
            e.printStackTrace();
        }


    }
}
