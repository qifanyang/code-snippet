package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 *  算法题目要静下心思考,基础算法掌握好解决复杂点的算法思路更多更快
 * @author yangqf
 * @version 1.0 2017/2/19
 */
public class LC102_BinaryTreeLevelOrderTraversal{

    public static void main(String[] args){
        TreeNode root = new TreeNode(1);
        LC.makeTreeNode(root, 2, 3);
        LC.makeTreeNode(root.left, 4, 5);
        LC.makeTreeNode(root.right, 6, 7);
        LC102_BinaryTreeLevelOrderTraversal test = new LC102_BinaryTreeLevelOrderTraversal();
        test.levelOrder(root);
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        int level = 0;
        List<List<Integer>> list = new ArrayList<>(10);
        traversal(root, level, list);
        return list;
    }

    private void traversal(TreeNode node, int level, List<List<Integer>> list){
        if(node == null)return;

        if(list.size() <= level){
            list.add(new ArrayList<>());
        }
        List<Integer> innerList = list.get(level);

        innerList.add(node.val);
        ++level;
        traversal(node.left, level, list);
        traversal(node.right, level, list);
    }

}
