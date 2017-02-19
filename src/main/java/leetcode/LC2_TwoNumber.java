package leetcode;

/**
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * 主要是处理进位和长度不一样,如何停止计算
 *
 * @author yangqf
 * @version 1.0 2017/2/17
 */
public class LC2_TwoNumber{
    /**
     * java中实现BigInteger是采用数组实现
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
//        * Input: (2 -> 4 -> 3)
//               + (5 -> 6 -> 4)
//        * Output: 7 -> 0 -> 8
        ListNode result = null;
        ListNode r = null;
        boolean hasCarry = false;
        do{
            int a = l1 != null ? l1.val : 0;
            int b = l2 != null ? l2.val : 0;
            int c = a + b + (hasCarry ? 1 : 0);
            hasCarry = c > 9 ? true : false;
            if(result == null){
                result = new ListNode(c % 10);
                r = result;
            }else {
                result.next = new ListNode(c % 10);
                result = result.next;
            }

            if(!hasCarry && (l1 == null || l1.next == null) && (l2 ==null || l2.next == null)){
                break;
            }
            l1 = l1!=null?l1.next:null;
            l2 = l2!=null?l2.next:null;
        }while(true);

        return r;
    }

    public ListNode addTwoNumbersGood(ListNode l1, ListNode l2) {
        ListNode c1 = l1;
        ListNode c2 = l2;
        ListNode sentinel = new ListNode(0);
        ListNode d = sentinel;
        int sum = 0;
        while (c1 != null || c2 != null) {//只要有一个不为空就应该继续执行加法
            sum /= 10;//使用整除来作为进位,进位作为高位的初始值
            if (c1 != null) {//可能一个链表长一个链表端,所以需要判断是否为空并移动
                sum += c1.val;
                c1 = c1.next;
            }
            if (c2 != null) {
                sum += c2.val;
                c2 = c2.next;
            }
            d.next = new ListNode(sum % 10);//进位后的值
            d = d.next;//用于继续新增节点存储加法结果
        }
        if (sum / 10 == 1)
            d.next = new ListNode(1);//5 + 5
        return sentinel.next;
    }


    public static void main(String[] args){
        ListNode a = LC.makeSingleLinkedList(9,1,6);
        ListNode b = LC.makeSingleLinkedList(0);

//        a = LC.makeSingleLinkedList(5);
//        b = LC.makeSingleLinkedList(5);
        LC.print(a);
        LC.print(b);
        LC.print(addTwoNumbers(a, b));
    }



}
