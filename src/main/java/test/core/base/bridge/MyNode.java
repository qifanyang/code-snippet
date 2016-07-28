package test.core.base.bridge;

/**
 * @author yangqf
 * @version 1.0 2016/7/28
 */
public class MyNode extends Node<Integer> {
    public MyNode(Integer data) { super(data); }

    public void setData(Integer data) {
        System.out.println("MyNode.setData");
        super.setData(data);
    }

    public static void main(String[] args){
        MyNode mn = new MyNode(5);
        //虽然<T>传的是<Integer>,但是Node泛型擦除时变为Node.setData(Object)
        Node n = mn;            // A raw type - compiler throws an unchecked warning
        //所以这里参数为String是合法的,但是运行结果报错了
        //java.lang.String cannot be cast to java.lang.Integer
        //因为编译时MyNode新增一个桥接方法,MyNode.setData(Object){setData((Integer)data)}
        n.setData(1);
        Integer x = mn.data;    // Causes a ClassCastException to be thrown.
    }
}
