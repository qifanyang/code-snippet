package core.j8.collection;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by yangqifan on 2017/7/22.
 */
public class MapperTest {

    public static void main(String[] args) {

        testOptional();
        testHashMap();
        testReduce();


        /*
        看过js中的数组方法,包含filter, map, reduce, some, every.
        java 8 stream类似,包含对集合进行一系列操作的方法,因为有了lambda,类似js数组的实现才有了可能
         */
    }

    /**
     * 移除值大于2的元素(筛选)
     */
    private static void testHashMap(){
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        System.out.println(map.toString());
        //now 1行代码
        //有效操作,1.entrySet, 2.filter, 3.创建结果map(还多了一个重复key处理)
        //fluent编码风格,编写更快,单行代码断点调试不方便,比如value为空会抛出空异常,不好调试
        Map<String, Integer> result = map.entrySet().stream().filter(e -> e.getValue() <= 2).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (exist, v) -> v));
        System.out.println(result);


        //before
        //6行代码实现,有效操作,1.entrySet, 2.forEach, 3.判断, 4.移除
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            if (entry.getValue() > 2) {
                map.remove(entry.getKey());
            }
        }
        System.out.println(map.toString());


    }

    /**
     * 计算list中的sum, max, average
     * 元素可以是对象,计算和可以是对象某个属性
     */
    private static void testReduce() {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        System.out.println(list.stream().mapToInt(x -> x).sum());
        System.out.println(list.stream().reduce(0, (x, y)->x+y));

        System.out.println(list.stream().mapToInt(x->x).max().getAsInt());
        System.out.println(list.stream().max(Comparator.comparingInt(x -> x)).get());

        System.out.println(list.stream().mapToInt(x->x).average().getAsDouble());

    }

    /**
     * Optional并不方便,只用作返回值更合适,类似NullObejct
     */
    private static void testOptional(){
        String s = "ff";
        Optional<String> os = Optional.of(s);
        System.out.println(os.get().length());

        String b = null;
//        Optional<String> ob = Optional.of(b);
        System.out.println();

    }
}
