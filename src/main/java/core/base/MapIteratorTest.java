package core.base;

import java.util.*;

/**
 * 各种map的迭代器输出顺序
 * Created by Administrator on 2017/3/10.
 */
public class MapIteratorTest {

    static String a = "aaaaaaaaaa1sdf";
    static String b = "bbbbbbbbbbdf33";
    static String c = "cccccccccc234234df";
    static String d = "ddddddddddfhrthtr66";
    static String e = "eeeeeeeeeeasfsgg44";

    public static void main(String[] args) {
//        test(new HashMap<>());//输出没有顺序
//        test(new LinkedHashMap<>());//输出为插入顺序
        test(new TreeMap<>());//按key的顺序输出,跟插入顺序无关

    }

    private static void test(Map<String, String> map) {
        put(map);
        print(map);
    }



    static void put(Map<String, String> map){
        map.put(b, b);
        map.put(c, c);
        map.put(a, a);
        map.put(d, d);
        map.put(e, e);
    }
    static void print(Map<String, String> map){
        System.out.println("------------"+map.getClass().getName()+"--------------");
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            System.out.println(next.getKey()+":"+next.getValue());
        }
    }

}
