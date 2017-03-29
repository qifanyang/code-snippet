package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/3/11.
 */
public class TreeOrderTraserve {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(9);
        LC.makeTreeNode(root, 7, 99);
        LC.makeTreeNode(root.left, 3, 8);
        LC.makeTreeNode(root.right, 33, 100);
        List<Integer> list = new ArrayList<>();
        in_order_traverse(root, list);
        System.out.println(list.toString());
    }

    static void pre_order_traverse(TreeNode root) {

    }

    static void in_order_traverse(TreeNode root, List<Integer> list) {
        if(root == null)return;
        in_order_traverse(root.left, list);
        list.add(root.val);//这里会访问每个节点
        in_order_traverse(root.right, list);
    }

}
