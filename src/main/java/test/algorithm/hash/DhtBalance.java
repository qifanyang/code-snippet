package test.algorithm.hash;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 带平衡性的DHT
 * @author yangqf
 * @version 1.0 2016/8/2
 */
public class DhtBalance {

    private List<VirtualNode> list = new LinkedList<>();

    public void remove(Machine machine){
        LinkedList<VirtualNode> lists = new LinkedList<>(list);
        for(Iterator<VirtualNode> iterator = lists.iterator(); iterator.hasNext();){
            VirtualNode next = iterator.next();
            if(next.getMachine().equals(machine)){
                iterator.remove();
            }
        }
        list = lists;
    }

    public void addMachine(Machine machine){
        //添加机器创建虚拟节点,一个机器10个节点,如何保证10个节点在hash空间上分布均匀,同时一个虚拟节点不会
        //映射到不同机器上

        //1.把hash空间分成10个子区间,然后在子区间上随机放入虚拟几点,新加入机器重复这个步骤
        //2.随机虚拟节点要遍历已经存在的虚拟节点,不能重复,如果允许重复.那么新加入的机器就少了一个虚拟节点

        Random random = new Random();
        int segmentNum = 10;
        int step = Integer.MAX_VALUE / segmentNum;
        for(int i = 0; i < segmentNum; i++){
           int hash = step*i + random.nextInt(step);
            VirtualNode virtualNode = new VirtualNode();
            virtualNode.setHashCode(hash);
            virtualNode.setMachine(machine);
            if(!list.contains(virtualNode)){
                list.add(virtualNode);
            }else{
                System.out.println("virtual node conflict ...");
            }
        }

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
        VirtualNode m = null;
        for(VirtualNode vn : list){
            if(vn.getHashCode() >= hashCode){
//                System.out.println("find cache machine :" + machine.getId());
                m = vn;
                break;
            }
        }

        if(null == m){
            m = list.get(0);
        }
            System.out.println("find cache machine :" + m.getMachine().getId());
        return m.getMachine();
    }

    public static class VirtualNode{
        private int hashCode;
        private Machine machine;//虚拟节点指向的机器

        public int getHashCode(){
            return hashCode;
        }

        public void setHashCode(int hashCode){
            this.hashCode = hashCode;
        }

        public Machine getMachine(){
            return machine;
        }

        public void setMachine(Machine machine){
            this.machine = machine;
        }

        @Override
        public boolean equals(Object obj){
            return this.hashCode == obj.hashCode();
        }
    }

    public void status(){
        StringBuilder sb = new StringBuilder();
        for(VirtualNode vn : list){
            sb.append(vn.getHashCode())
                    .append("(")
                    .append(vn.getMachine().getId())
                    .append(")")
                    .append(" : ")
                    .append(vn.getMachine().getCache().size())
                    .append("\r\n");
        }
        System.out.println(sb.toString());
    }

}
