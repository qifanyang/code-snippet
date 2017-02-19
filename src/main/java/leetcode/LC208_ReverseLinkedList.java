package leetcode;

/**
 * @author yangqf
 * @version 1.0 2017/2/19
 */
public class LC208_ReverseLinkedList{
    public static void main(String[] args){
        ListNode list = LC.makeSingleLinkedList(1, 2, 3);
        LC208_ReverseLinkedList test = new LC208_ReverseLinkedList();
        LC.print(list);
        ListNode result = test.reverseList(list);
//        ListNode result = test.reverseListIterator(list);
        LC.print(result);
    }

    public ListNode reverseListIterator(ListNode head) {
        ListNode sentinel = new ListNode(0);
        for(;head != null;){
            //将遍历到的节点插入到链表中
            ListNode temp = sentinel.next;
            sentinel.next = new ListNode(head.val);
            sentinel.next.next = temp;

            //迭代移动链表指针
            head = head.next;
        }
        return sentinel.next;
    }

    public ListNode reverseList(ListNode head){
        return reverseListInt(head, null);
    }

    private ListNode reverseListInt(ListNode head, ListNode newHead) {
        if (head == null)
            return newHead;//终止条件
        ListNode next = head.next;//遍历递归,递归的时候改变指针指向

        //第一次: 第一个节点的下一个节点为null
        head.next = newHead;//改变指向,下一个node指向上一个node, 递归参数做临时变量
        return reverseListInt(next, head);
    }
}
