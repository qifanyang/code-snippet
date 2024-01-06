package leetcode;

/**
 * Created by yangqifan on 2020/8/17.
 */
public class LC21_mergeSortLinkedList {

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode cur1 = l1, cur2 = l2, head = new ListNode(0), cur = new ListNode(0);
        while (cur1.next != null && cur2.next != null) {
            if (cur1.val < cur2.val) {
                if (head.next == null) {
                    head = cur;
                }
                cur.next = cur1;
                cur = cur1;
                cur1 = cur1.next;
            } else {
                if (head.next == null) {
                    head.next = cur;
                }
                cur.next = cur2;
                cur = cur2;
                cur2 = cur2.next;
            }
        }

        while (cur1.next != null) {
            cur.next = cur1;
            cur1 = cur1.next;
            cur = cur.next;
        }
        while (cur2.next != null) {
            cur.next = cur2;
            cur2 = cur2.next;
            cur = cur.next;
        }


        return head.next;

    }

    /**
     * 类似使用栈排序, 把较小的值节点压栈
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode mergeRecursive(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        if (l1.val < l2.val) {
            l1.next = mergeRecursive(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeRecursive(l1, l2.next);
            return l2;
        }

    }

    public static void main(String[] args) {
        ListNode l1 = LC.makeSingleLinkedList(1, 3, 5);
        ListNode l2 = LC.makeSingleLinkedList(2, 4, 6);
        ListNode recusive = mergeRecursive(l1, l2);
        LC.print(recusive);
    }
}
