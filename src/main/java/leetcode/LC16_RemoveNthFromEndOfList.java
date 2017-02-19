package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangqf
 * @version 1.0 2017/2/18
 */
public class LC16_RemoveNthFromEndOfList{
    public static void main(String[] args){
        LC16_RemoveNthFromEndOfList test = new LC16_RemoveNthFromEndOfList();
        LC.print(test.removeNthFromEnd(LC.makeSingleLinkedList(1, 2, 3, 4, 5), 2));
        LC.print(test.removeNthFromEnd(LC.makeSingleLinkedList(5), 1));
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        List<ListNode> list = new ArrayList<>();
        ListNode temp = head;
        //one pass
        for(;temp.next != null;){
            list.add(temp);
            temp = temp.next;
        }
        list.add(temp);

        //寻找倒数第n+1个元素, if have three elements in list, the index value is 2,
        //so the index value is based zero, but the n is based one,
        int targetIndex = list.size() - n - 1;
        if(targetIndex < 0){
            //remove head
            return head.next;
        }else {
            ListNode listNode = list.get(targetIndex);
            listNode.next = listNode.next.next;
        }

        return head;
    }


}
