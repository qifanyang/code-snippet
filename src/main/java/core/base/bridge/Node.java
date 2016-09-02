package core.base.bridge;

/**
 * @author yangqf
 * @version 1.0 2016/7/28
 */
public class Node<T> {

    public T data;

    public Node(T data) { this.data = data; }

    public void setData(T data) {
        System.out.println("Node.setData");
        this.data = data;
    }
}