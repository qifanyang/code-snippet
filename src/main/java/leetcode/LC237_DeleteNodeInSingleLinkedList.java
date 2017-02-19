package leetcode;

/**
 * Naver China interview problem
 * 没办法删除最后一个节点,如果需要删除最后一个节点还是需要根据head遍历,
 * 如果拷贝节点值开销不大,可以作为一种删除单向链表的优化措施
 * @author yangqf
 * @version 1.0 2017/2/19
 */
public class LC237_DeleteNodeInSingleLinkedList{

    public void deleteNode(ListNode node) {
        if(node.next == null){
            //删除尾部节点,单向链表没办法根据tail,删除tail
            //not surpported tail node delete
        }else {
            node.val = node.next.val;
            node.next = node.next.next;
        }
    }

    public static void main(String[] args){
        LC237_DeleteNodeInSingleLinkedList test = new LC237_DeleteNodeInSingleLinkedList();
        ListNode list = LC.makeSingleLinkedList(1, 2, 3, 4, 5);
        LC.print(list);
        test.deleteNode(list.next.next);
        LC.print(list);
    }
}
