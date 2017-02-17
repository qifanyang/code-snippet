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
    static class ListNode{
        public int val;
        ListNode next;
        ListNode(int x){
            val = x;
        }
    }

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


    public static void main(String[] args){
        ListNode a = make(9,1,6);
        ListNode b = make(0);

        a = make(5);
        b = make(5);
        print(a);
        print(b);
        print(addTwoNumbers(a, b));
    }

    static ListNode make(int... v){
        if(v.length == 0)return null;
        ListNode h = new ListNode(v[0]);
        ListNode temp = h;
        for(int i = 1; i < v.length; i++){
            temp.next = new ListNode(v[i]);
            temp = temp.next;
        }
        return h;
    }

    static void print(ListNode l){
        for(;l.next != null;l = l.next){
            System.out.print(l.val + "->");
        }
        System.out.println(l.val);
    }


}
