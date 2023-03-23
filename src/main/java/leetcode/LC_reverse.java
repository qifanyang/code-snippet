package leetcode;

/**
 * Created by yangqifan on 2020/8/18.
 */
public class LC_reverse {
    public static void main(String[] args) {
        ListNode header = LC.makeSingleLinkedList(1, 3, 5, 7, 8, 9);
        LC.print(header);

        //双指针
        ListNode pre = null, next = null;
        ListNode cur = header;
        while (cur != null) {
            next = cur.next;//存储next, 用于移动cur
            cur.next = pre;//断开并指向前驱节点, 第一次前驱为空, 后面不为空
            pre = cur;//移动前驱节点
            cur = next;//移动当前节点指针
        }
        LC.print(pre);
        /**
         * 使用最小节点数推理 a->b->c->null
         * 效果: 反转链表, b指向a, c指向b, a指向null
         * a指向null的时候, a指向b断开, 需要临时变量指向b, 即 tmp = a.next ; a.next = null
         *
         * 需要遍历链表, 需要定义临时变量cur, 表示当前操作的节点, 默认为header,  cur = a
         *
         * 所以 tmp = a.next ; a.next = null 转换为 temp = cur.next; cur.next=null;
         *
         * 现在a b之间的链接断开, b 将要指向 a, b作为当前操作节点, 所以cur应指向b, 即cur = temp
         *
         * 修改cur指向, 需要将b的下一节点存储temp = cur.next, 然后修改cur.next = a, 但是当修改cur指向b
         * 时, 已经丢失a的引用, 所以还需要在将cur执行b时存储a的引用, 定义变量为pre, 当修改cur值时用于存储cur值
         *
         *
         *
         *
         *
         */

        ListNode l1 = LC.makeSingleLinkedList(1, 3, 5, 6);
        ListNode l2 = LC.makeSingleLinkedList(2, 8, 10);
        ListNode temp = new ListNode(-1);
        ListNode h = temp;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                temp.next = l1;
                l1 = l1.next;
            } else {
                temp.next = l2;
                l2 = l2.next;
            }
            temp = temp.next;
        }

        temp.next = l1 == null ? l2 : l1;

        LC.print(h.next);
        /**
         * 思路:
         * l1, l2 既是header, 也可用作offset, 用于遍历链表
         * merge操作就是遍历两个链表, 将两个链表串联上, 就像穿针引线一样
         * 所以需要引入穿针引线的变量, 默认值新建ListNode temp,
         * temp.next指向为l1和l2较小的节点, 然后分别单独移动l1, l2
         * 第一步temp就指向了l1的值1, 为了继续串联下一个, 需要将temp=temp.next
         * 然后重复上面的过程, 知道l1或l2至少有一个链表被遍历完毕
         *
         * 最后至少有一个链表为空, 另一个链表直接连接在temp.next即可
         *
         * 因为temp一直在移动,更改指向. 初始值为merge链表的header, 所以需要定义个临时变量
         *
         */

        /**
         * 链表操作需要特点:
         * 1.移动的变量, 用于遍历和更改链表指向
         * 2.链表断开时的临时变量, 更改指向时, 需要存储next, pre, cur
         */
    }
}
