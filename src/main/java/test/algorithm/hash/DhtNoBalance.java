package test.algorithm.hash;

import java.util.LinkedList;

/**
 * 不平衡的分布式哈希DHT(没有虚拟节点)
 * @author yangqf
 * @version 1.0 2016/8/2
 */
public class DhtNoBalance{
    //节点量大可以使用LinkedHashMap,查找不用遍历链表
    private LinkedList<Machine> list = new LinkedList();

    public void addMachine(Machine machine){
        list.add(machine);
    }

    public void put(String key, Object object){
        Machine machine = determineMachine(key);
        machine.put(key, object);
    }

    public Object get(String key){
        return  determineMachine(key).get(key);
    }

    public Machine determineMachine(String key){
        int hashCode = key.hashCode();
        Machine m = null;
        for(Machine machine : list){
            if(machine.getHash() >= hashCode){
//                System.out.println("find cache machine :" + machine.getId());
                m = machine;
                break;
            }
        }

        if(null == m){
            m = list.get(0);
//            System.out.println("find cache machine :" + m.getId());
        }

        return m;
    }


    public void status(){
        StringBuilder sb = new StringBuilder();
        for(Machine machine : list){
            sb.append(machine.getId())
                    .append("(")
                    .append(machine.getHash())
                    .append(")")
                    .append(" : ")
                    .append(machine.getCache().size())
                    .append("\r\n");
        }
        System.out.println(sb.toString());
    }

}
