package leetcode;

/**
 * Created by yangqifan on 2019/7/4.
 */
public class MyLinkedList {

    class Node {
        private int val;
        private Node next;
    }

    /**
     * Initialize your data structure here.
     */

    private Node head;
    private Node tail;


    /**
     * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
     */
    public int get(int index) {
        //遍历链表
        Node current = head;
        int i = 0;
        while (current != null) {
            if (i == index) {
                return current.val;
            }
            current = current.next;
            ++i;
        }
        return -1;

    }

    /**
     * Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
     */
    public void addAtHead(int val) {
        Node node = new Node();
        node.val = val;
        if (head == null) {
            head = node;
            tail = node;
        } else {
            node.next = head;
            head = node;
        }

    }

    /**
     * Append a node of value val to the last element of the linked list.
     */
    public void addAtTail(int val) {
        Node node = new Node();
        node.val = val;
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
    }

    /**
     * Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted.
     */
    public void addAtIndex(int index, int val) {
        if (index <= 0) {
            addAtHead(val);
            return;
        }
        Node current = head;
        Node pre = null;
        int i = 0;
        while (current != null) {
            if (i == index) {
                Node node = new Node();
                node.val = val;
                if (pre == null) {
                    //首节点append
                    node.next = head;
                    head = node;
                } else {
                    //中间insert
                    node.next = current;
                    pre.next = node;
                }
                break;
            }
            pre = current;
            current = current.next;
            ++i;
            if (current == null) {
                addAtTail(val);
            }
        }


    }

    /**
     * Delete the index-th node in the linked list, if the index is valid.
     */
    public void deleteAtIndex(int index) {
        if (head == null) {
            return;
        }

        Node current = head;
        Node pre = null;
        int i = 0;
        while (current != null) {
            if (i == index) {
                if (pre == null) {
                    //删除head
                    Node temp = head;
                    head = head.next;
                    temp.next = null;
                } else {
                    pre.next = current.next;
                    current.next = null;
                    if(current == tail){
                        tail = pre;
                    }
                }
                break;
            }
            pre = current;
            current = current.next;
            ++i;
        }

    }

    private Node reverse(){
        Node current = head;
        Node pre = null;
        while (current != null){
            Node temp = current.next;
            current.next = pre;
            pre = current;
            current = temp;
        }
        return tail;
    }



    private void print(){
        print(null);
    }

    private void print(Node node) {
        Node current = node == null ? head : node;
        while (current != null) {
            System.out.print(current.val);
            System.out.print("->");
            current = current.next;
        }
        System.out.println();
    }

    public static void main(String[] args) throws InterruptedException {

        Thread.sleep(Integer.MAX_VALUE);
        MyLinkedList list = new MyLinkedList();

        list.addAtHead(8);
        list.addAtTail(81);
        list.deleteAtIndex(2);
        list.print();
        list.addAtHead(26);
        list.print();
        list.deleteAtIndex(2);
        list.print();
        list.addAtTail(24);
        list.print();
        list.addAtHead(15);
        list.print();
        list.addAtTail(0);
        list.addAtTail(13);
        list.addAtTail(1);
        list.print();
        list.addAtIndex(6, 33);
        System.out.println(list.get(6));
        list.print();
        list.reverse();
        list.print(list.tail);





    }
}


