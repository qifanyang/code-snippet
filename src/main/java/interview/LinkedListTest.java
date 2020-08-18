package interview;


/**
 * Created by yangqifan on 2019/7/6.
 */
public class LinkedListTest {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    private void print(ListNode node) {
        ListNode current = node;
        while (current != null) {
            System.out.print(current.val);
            System.out.print("->");
            current = current.next;
        }
        System.out.println();
    }

    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode node = new ListNode(0);
        node.next = head;
        removeNthFromEndX(node, n);
        return node.next;
    }

    public int removeNthFromEndX(ListNode head, int n) {
        if (head == null) {
            return 0;
        }
        int i = removeNthFromEndX(head.next, n);
        if (i == n) {
            head.next = head.next.next;
        }
        return i + 1;
    }


    //1->3->5
    //2->6->7
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {

        ListNode l1Cur = l1;
        ListNode l2Cur = l2;
        ListNode pre = null;
        while (l1Cur != null) {
            while (l1Cur.val <= l2Cur.val) {
                pre = l1Cur;
                l1Cur = l1Cur.next;
                if(l1Cur == null){
                    break;
                }
            }
            pre.next = l2Cur;
            ListNode temp = l2Cur.next;
            l2Cur.next = l1Cur;
            l2Cur = temp;
        }

        while (l2Cur != null) {
            l1Cur.next = l2Cur;
        }

        return l1;
    }


    public static void main(String[] args) {
        LinkedListTest test = new LinkedListTest();

        ListNode head = new ListNode(1);
        ListNode second = new ListNode(3);
        ListNode third = new ListNode(4);
        ListNode forth = new ListNode(5);
        head.next = second;
        second.next = third;
        third.next = forth;

        ListNode l2 = new ListNode(2);
        l2.next = new ListNode(5);

        ListNode listNode = test.mergeTwoLists(head, l2);
        test.print(listNode);

        test.removeNthFromEnd(head, 1);


        System.out.println();

    }


}
