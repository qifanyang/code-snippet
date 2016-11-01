package core.j8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yangqf
 * @version 1.0 2016/10/27
 */
public class ListTest{
    public static void main(String[] args){
            filterList();
    }

    public static void filterList(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(44);
        list.add(6);
        list.forEach(System.out::println);
        //System.out::println 表示对方法的引用
        System.out.println("------------");
        //返回大于10的sublist
        list = list.stream().filter(x -> x > 3).collect(Collectors.toList());
        list.forEach(x->System.out.println(x));

        Collections.sort(list, (Integer a, Integer b)->{return a.compareTo(b);});
    }
}
