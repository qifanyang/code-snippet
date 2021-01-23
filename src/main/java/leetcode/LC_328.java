package leetcode;

/**
 * https://leetcode-cn.com/problems/odd-even-linked-list/
 * Created by yangqifan on 2020/8/19.
 */
public class LC_328 {

    public static void main(String[] args) {
        ListNode a = LC.makeSingleLinkedList(1, 2, 3, 4, 5, 6, 7, 8);
        LC.print(a);
        LC.print(oddEvenList(a));
    }

    public static ListNode oddEvenList(ListNode head) {
        /**
         * 第一个节点作为基数链表的header 1
         * 第二个节点作为偶数链表的header 2
         *
         * 第一轮
         * 1指向3 1.next = 1.next.next.next
         * 2指向4  2.next = 2.next.next.next
         *
         * 第二轮
         * 1.next.next.next = 1.next.next.next.next.next.next
         * 奇数需要定义一个遍历临时变量temp1, 当temp1.next == null 停止
         * 偶数定义遍历节点temp2, 当temp2.next.next == null 停止
         *
         *
         */
        if(head == null || head.next == null){
            return head;
        }
        ListNode hjishu = head;
        ListNode houshu = head.next;
        ListNode temp1 = hjishu;
        ListNode temp2 = houshu;
        while (temp1.next != null && temp1.next.next != null){
            temp1.next = temp1.next.next;
            temp1 = temp2.next;
            temp2.next = temp2.next.next;
            temp2 = temp1.next;
        }

        temp1.next = houshu;

        return hjishu;
    }
}
