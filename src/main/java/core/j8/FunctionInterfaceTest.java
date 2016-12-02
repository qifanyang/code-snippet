package core.j8;

import java.text.NumberFormat;
import java.util.Calendar;

/**
 * j8新增{@link FunctionalInterface}
 *
 * 1.使用该注解的接口只能且只能包含一个抽象方法
 * 2.函数接口实例还可以通过lambda表达式创建,方法引用(Object::clone),构造方法引用
 * 3.{@link FunctionalInterface}不能使用在注解和类上, 只能使用在接口上,不然编译不通过
 * @author yangqf
 * @version 1.0 2016/10/28
 */
public class FunctionInterfaceTest{
    public static void main(String[] args){
        //每使用一个lambda,编译器都会创建一个新方法的字节码,所以lambda多了字节码也多
        //lambda没法重写,所以采用私有静态方法实现
        FI fi1 = () -> {System.out.println("call fi test");return  1;};
//        FI fi2 = () -> {System.out.println("call fi test");return  1;};
        fi1.test();
//        f(() -> {System.out.println("call fi test");return  1;});
        //1.编译器会根据lambda表达式生成一个方法, private static void lambda$main$0(); flags: ACC_PRIVATE, ACC_STATIC, ACC_SYNTHETIC(表示是编译器自动生成)
        //2.lambda表达式使用invokedynamic指令来调用
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        long timeInMillis = calendar.getTimeInMillis();
        System.out.println(timeInMillis);

        long ll = 45;
        double v = 55;
        System.out.println(ll -v);
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        System.out.println(v);
        System.out.println(percentInstance.format(1.0*(100-30)/100));
    }

    //为了是结合框架使用到lambda表达式,在顶级接口增加default方法,然后参数使用函数接口
    public static void f(FI f){
        f.test();
    }
}
