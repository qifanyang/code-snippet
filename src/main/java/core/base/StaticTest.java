package core.base;


/**
 * Created by Administrator on 2017/2/15.
 */
public class StaticTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //class.forName 如果不初始化,则类加载不会执行类的初始化方法cinit<>
        //当创建对象时如果class初始化方法没有执行先执行初始化方法,再执行构造方法
        Class<?> bb = Class.forName("core.base.StaticBean", false, ClassLoader.getSystemClassLoader());
        System.out.println(bb.getName());
        Object o = bb.cast(bb.newInstance());//执行类初始化方法
//        Class<?> aa = Class.forName("core.base.StaticBean", true, ClassLoader.getSystemClassLoader());
    }
}
