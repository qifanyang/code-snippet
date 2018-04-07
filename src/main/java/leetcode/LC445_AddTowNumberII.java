package leetcode;

import java.util.Stack;

/**
 * 主要考察链表和递归,  使用栈更简单,栈达到了反转链表的效果
 * @author yangqf
 * @version 1.0 2017/2/19
 */
public class LC445_AddTowNumberII{
    public static void main(String[] args){
        ListNode a = LC.makeSingleLinkedList(5,4,5);
        ListNode b = LC.makeSingleLinkedList(5);
        LC.print(a);
        LC.print(b);
        LC445_AddTowNumberII test = new LC445_AddTowNumberII();
        ListNode c = test.addTwoNumbers(a, b);
        LC.print(c);
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        //two single linked list length maybe is not equal , so need align them
        ListNode h1 = l1;
        ListNode h2 = l2;
        while(l1 != null && l2 != null){
            l1 = l1.next;
            l2 = l2.next;
        }
        if(l1 == null && l2 != null){
            //l1 shortter, l2 longger
            while(l2 != null){
                ListNode temp = new ListNode(0);
                temp.next = h1;
                h1 = temp;
                l2 = l2.next;
            }
        }
        if(l2 == null && l1 != null){
            //l2 shortter, l1 longger
            while(l1 != null){
                ListNode temp = new ListNode(0);
                temp.next = h2;
                h2 = temp;
                l1 = l1.next;
            }
        }

        //the two linked list have the same length, user h1 and h2
        ListNode sentinel = new ListNode(0);
        ListNode th1 = new ListNode(0);
        th1.next = h1;
        ListNode th2 = new ListNode(0);
        th2.next = h2;
        int i = addNext(th1, th2, sentinel);
        if(i > 0){//最高位有进位,要处理进位
            ListNode next = sentinel.next;
            sentinel.next = new ListNode(i);
            sentinel.next.next = next;
        }
        return sentinel.next;
    }

    private int addNext(ListNode next1, ListNode next2, ListNode sentinel){
        if(next1 == null)return -1;//当没有下一个节点是停止递归
        int temp = addNext(next1.next, next2.next, sentinel);//停止递归时返回0, next1, next2 最后一个
        if(temp < 0){
            //说明到达个位数了,从个位数开始计算
            return next1.val + next2.val;
        }else{
            //返回的结果不会负数,说明开始计算
            ListNode r = new ListNode(temp % 10);
            ListNode tempNode = sentinel.next;//从最右边计算,递归退出的时候需要向链表中插入数据
            sentinel.next = r;
            r.next = tempNode;
            return next1.val + next2.val + (temp/10);//如果这里最左边了,需要在递归外部处理进位
        }
    }

    //copy from leetcode
    public ListNode addTwoNumbersLeetCode(ListNode l1, ListNode l2) {
        Stack<Integer> s1 = new Stack<Integer>();
        Stack<Integer> s2 = new Stack<Integer>();

        while(l1 != null) {
            s1.push(l1.val);
            l1 = l1.next;
        };
        while(l2 != null) {
            s2.push(l2.val);
            l2 = l2.next;
        }

        int sum = 0;
        ListNode list = new ListNode(0);
        while (!s1.empty() || !s2.empty()) {
            if (!s1.empty()) sum += s1.pop();
            if (!s2.empty()) sum += s2.pop();
            list.val = sum % 10;
            ListNode head = new ListNode(sum / 10);
            head.next = list;
            list = head;
            sum /= 10;
        }

        return list.val == 0 ? list.next : list;
    }
}
