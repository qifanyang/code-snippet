package core.j8;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yangqf
 * @version 1.0 2016/10/28
 */
public class LambdaTest{
    public static void main(String[] args){
        //lambda表达式结构  (形式参数)->{方法体}
        //对于使用了FunctionInterface注解的接口,如果只有一个方法,

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(44);
        list.add(6);
        list.sort((x,y) -> (x-y));//只有一行代码 return都可以省略了
        System.out.println(list.toString());
    }

}
