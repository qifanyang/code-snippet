package algorithm.binarytree;

/**
 * BinaryTree没有考虑移动, 这个版本考虑移动
 * @author yangqf
 * @version 1.0 2016/9/26
 */
public class BinaryTree1 extends BinaryTree{

    public static void main(String[] args){
        test();
    }

    @Override
    protected void addLeft(Node<? extends Comparable> parent, Node<? extends Comparable> node){
        Node<? extends Comparable> left = parent.getLeft();
        if(null == left){
            parent.setLeft(node);
        }else {
            //左节点不为空,需要判断新加入的节点和左节点比较
            add(left, node);
        }
    }
}
