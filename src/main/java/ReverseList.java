/**
 * Created by yangqifan on 2018/3/25.
 */
public class ReverseList {

    static class Node{
        private int val;
        private Node next;
    }

    static Node addNextNode(Node node, int... vals){
        for(int val : vals){
            Node next = new Node();
            next.val = val;
            node.next = next;
            node = next;
        }
        return node;
    }

    static void printNodes(Node head){
        while (head != null){
            System.out.print(head.val);
            head = head.next;
            if(head != null){
                System.out.print(",");
            }
        }
        System.out.println();
    }
    public static void main(String[] args) {

        Node head = new Node();
        head.val = 1;
        addNextNode(head, 2, 3, 5, 22, 3);
        printNodes(head);

        //reverse list
        //head -> node1 -> node2 -> node3 -> ...
        //正常逻辑 将第二个node指向第一个node, 需要保留第三个node引用
        //改变第二个节点时又需要持有第二个node引用
        //
        //泛化 head的pre为null, 三个一组操作
        Node pre = null;
//        while (head != null){
//            Node next = head.next; //第三个节点
//            head.next = pre;//改变第二个节点到第一个节点,很重要,修复断开连接
//            pre = head;//改变pre, 前进一步
//            head = next;// head也前进一步
//        }

        //
//        printNodes(pre);

//        head = pre;
        //node1 -> node2 -> node3 -> node4 -> ...
        Node tmp = head;
        Node cur = head.next;
        head.next = null;
        while (cur != null){
            Node next = cur.next;//要改变指向,所以先临时保存
            cur.next = tmp;
            tmp = cur;
            cur = next;
        }
        printNodes(tmp);

    }
}
