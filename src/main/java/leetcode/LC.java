package leetcode;

/**
 * @author yangqf
 * @version 1.0 2017/2/18
 */
public class LC{
    public static ListNode makeSingleLinkedList(int... v){
        if(v.length == 0)return null;
        ListNode h = new ListNode(v[0]);
        ListNode temp = h;
        for(int i = 1; i < v.length; i++){
            temp.next = new ListNode(v[i]);
            temp = temp.next;
        }
        return h;
    }

    public static void print(ListNode l){
        if(l == null){
            System.out.println("List is NULL");
            return;
        }
        for(;l.next != null;l = l.next){
            System.out.print(l.val + "->");
        }
        System.out.println(l.val);
    }

    public static TreeNode makeTreeNode(TreeNode root, Integer left, Integer right){
        if(left != null){
            root.left = new TreeNode(left);
        }
        if(right != null){
            root.right = new TreeNode(right);
        }
        return root;
    }

    public static void main(String[] args) {
        System.out.println(cc(5));
    }

    private static int cc(int n){
        if(n == 0)return 0;
        return n + cc(n - 1);
    }
}
