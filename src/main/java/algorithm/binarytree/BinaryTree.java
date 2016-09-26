package algorithm.binarytree;

import lombok.Data;

/**
 * @author yangqf
 * @version 1.0 2016/9/26
 */
@Data
public class BinaryTree{

    public static void main(String[] args){
       test();
    }

    public static void test(){
        BinaryTree bt = new BinaryTree();
        Node<Integer> root = new Node<>(10);
        bt.setRoot(root);

        Node<Integer> one = new Node<>(6);
        Node<Integer> two = new Node<>(55);
        Node<Integer> three = new Node<>(3);
        Node<Integer> four = new Node<>(7);
        Node<Integer> five = new Node<>(1);
        Node<Integer> six = new Node<>(22);
        Node<Integer> seven = new Node<>(4);

        bt.add(one);
        bt.add(two);
        bt.add(three);
        bt.add(four);
        bt.add(five);
        bt.add(six);
        bt.add(seven);

        System.out.println();
        bt.preIterator(root);
    }

    private Node<Integer> root;

    public void add(Node<? extends Comparable> node){
        add(root, node);
    }

    public void add(Node<? extends Comparable> parent, Node<? extends Comparable> node){
            if(parent.value.compareTo(node.value) > 0){
                    addLeft(parent, node);
            }else if(parent.value.compareTo(node.value) < 0){
                    addRight(parent, node);
            }else {
                addLeft(parent, node);
            }
            node.setParent(parent);
    }

    protected void addLeft(Node<? extends Comparable> parent, Node<? extends Comparable> node){
        Node<? extends Comparable> left = parent.left;
        if(null == left){
            parent.setLeft(node);
        }else {
            add(left, node);
        }
    }
    protected void addRight(Node<? extends Comparable> parent, Node<? extends Comparable> node){
        Node<? extends Comparable> right = parent.right;
        if(null == right){
            parent.setRight(node);
        }else {
            add(right, node);
        }
    }

    protected void preIterator(Node node){
        System.out.print(node.getValue());
        System.out.print(" ");
        if(node.getLeft() != null){
            preIterator(node.getLeft());
        }
        if(node.getRight() != null){
            preIterator(node.getRight());
        }
    }
    @Data
    static class Node<T extends Comparable>{
        private Node parent;
        private Node left;
        private Node right;

        private T value;

        public Node(T c){
            this.value = c;
        }
    }
}
