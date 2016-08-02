package test.algorithm.hash;

/**
 * 没有虚拟节点的一致性hash
 * @author yangqf
 * @version 1.0 2016/8/2
 */
public class ConsistentHashDemo1{
    public static void main(String[] args){
        //构建
        DhtNoBalance dht = new DhtNoBalance();
        Machine m1 = new Machine("m1");
        dht.addMachine(m1);
        Machine m2 = new Machine("m2");
        dht.addMachine(m2);
        Machine m3 = new Machine("m3");
        dht.addMachine(m3);

        //放入数据
        System.out.println("xxx".hashCode());
        System.out.println("192.168.1.100#1".hashCode());
        System.out.println("192.168.1.100#2".hashCode());
        dht.put("m2", "hello");

        //获取数据
        System.out.println(dht.get("m2"));

        dht.status(); //  m1, m2, m3 他们的hash值很近导致几乎所有缓存都在m1上

        //输出
//        119160
//        hello
//        m1(3428) : 1
//        m2(3429) : 0
//        m3(3430) : 0
    }


}
