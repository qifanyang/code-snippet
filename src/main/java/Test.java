import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yangqf
 * @version 1.0 2017/2/14
 */
public class Test{

    private final static long workerIdBits = 4L;
    public final static long maxWorkerId = -1L ^ -1L << workerIdBits;
    public static void main(String[] args){
        List<String> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();

        System.out.println(Integer.toBinaryString(1 << (35 & 0x1f)));


        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("aaa", "aaa");

        System.out.println(maxWorkerId);
        System.out.println(Integer.toBinaryString((int)maxWorkerId));
        System.out.println(Integer.toBinaryString(-1));
        System.out.println(Integer.toBinaryString(-1 << 29));
        System.out.println(Integer.toBinaryString((1 << 29)-1));
        System.out.println(Integer.toBinaryString((1 << 29)));

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
//        for (String s : list) {
//            if("2".equals(s)){
//                list.remove(s);
//            }
//        }

        //foreach字节码会使用iterator, it.next()会检查modCount, fail-fast
        Iterator var2 = list.iterator();
//        while(var2.hasNext()) {
//            String s = (String)var2.next();
//            if("2".equals(s)) {
//                list.remove(s);
//            }
//        }
        System.out.println(list.toString());
    }
}
