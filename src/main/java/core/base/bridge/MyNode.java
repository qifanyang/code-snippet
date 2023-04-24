package core.base.bridge;

/**
 * https://docs.oracle.com/javase/tutorial/java/generics/bridgeMethods.html
 * @author yangqf
 * @version 1.0 2016/7/28
 */
public class MyNode extends Node<Integer> {
    public MyNode(Integer data) { super(data); }

    //会生成对应桥接方法, 保持多态
    public void setData(Integer data) {
        System.out.println("MyNode.setData");
        //super.setData(data);
    }

    public void setData(String data) {
        System.out.println("MyNode.setDataString");

    }

    void getx(String x){

    }

    public static void main(String[] args){
        MyNode mn = new MyNode(5);
        //虽然<T>传的是<Integer>,但是Node泛型擦除时变为Node.setData(Object)
        Node n = mn;            // A raw type - compiler throws an unchecked warning
        //所以这里参数为String是合法的,但是运行结果报错了
        //java.lang.String cannot be cast to java.lang.Integer

        //保持多态性, 生成桥接方法, 如果不生成桥接方法无法使用父类引用调用当前方法, 见A2用例
        //因为编译时MyNode新增一个桥接方法,MyNode.setData(Object){setData((Integer)data)}
        //n.setData(1);
        n.setData("hello");
        Integer data = mn.getData();
        Integer x = mn.data;    // Causes a ClassCastException to be thrown.
    }
}
