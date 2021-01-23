package leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by yangqifan on 2020/8/30.
 */
public class PrintBreadthTree {

    public static void main(String[] args) {
        TreeNode treeNode = LC.makeTree(1, 2, 3);
        treeNode.left.left = LC.makeTree(4, 5, 6);
//        print(treeNode);


        depthPrint(treeNode);

    }

    static void depthPrint(TreeNode node){
        if(null == node){
            return;
        }
        System.out.println(node.val);
        depthPrint(node.left);
        depthPrint(node.right);
    }

    static void print(TreeNode treeNode) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(treeNode);
        System.out.println(treeNode.val);
        while (!queue.isEmpty()) {

            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if(node.left != null){
                    System.out.print(node.left.val);
                    System.out.print(" ");
                    queue.add(node.left);
                }
                if(node.right != null){
                    System.out.print(node.right.val);
                    System.out.print(" ");
                    queue.add(node.right);
                }
                System.out.println();
            }
        }
    }
}
