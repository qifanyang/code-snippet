package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Administrator on 2017/2/22.
 */
public class LC226_InvertBinaryTree {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
//        LC.makeTreeNode(root, 2, 7);
//        LC.makeTreeNode(root.left, 1, 3);
//        LC.makeTreeNode(root.right, 6, 9);
        LC.makeTreeNode(root,1, null);
        LC226_InvertBinaryTree test = new LC226_InvertBinaryTree();
        test.invertTree(root);
        System.out.println();
    }

    public TreeNode invertTree(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        if(null == root)return root;
        queue.add(root);
        while (!queue.isEmpty()) {
            //广度优先,需要收集树形结构每一层有多少个节点,然后继续收集下一层有多少节点
            int size = queue.size();
            for(int i = 0; i < size; i++){
                //第一层size为1 只取一次, 第二层size为2 会取两次, 以此类推
                //没有递归,一层一层的遍历
                TreeNode node = queue.poll();
                if(null == node)continue;
                //交换左右节点值
                TreeNode temp = node.left;
                node.left = node.right;
                node.right = temp;
                queue.add(node.left);
                queue.add(node.right);
            }
        }

        return root;
    }
}
