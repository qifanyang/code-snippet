package core.j8;

/**
 * http://www.techempower.com/blog/2013/03/26/everything-about-java-8/
 *
 * java8接口中可以包含静态方法,
 *
 * 1.约定接口Foo实例使用的utility 方法放在Foos接口中
 * @author yangqf
 * @version 1.0 2016/11/1
 */
public class InterfaceStaticMethod{
    static void s(){
        System.out.println("i am static method in the interface");
    }

}
