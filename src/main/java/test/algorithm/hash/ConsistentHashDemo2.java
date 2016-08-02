package test.algorithm.hash;

/**
 * @author yangqf
 * @version 1.0 2016/8/2
 */
public class ConsistentHashDemo2{

    public static void main(String[] args){
        //构建
        DhtBalance dht = new DhtBalance();
        Machine m1 = new Machine("m1");
        dht.addMachine(m1);
        Machine m2 = new Machine("m2");
        dht.addMachine(m2);
        Machine m3 = new Machine("m3");
        dht.addMachine(m3);

        //放入数据
        System.out.println(Integer.MAX_VALUE);
        dht.put("m2", "hello");

        dht.remove(m1);

        //获取数据
        System.out.println(dht.get("m2"));
        System.out.println(dht.get("mss2"));

        dht.status();
    }
}
