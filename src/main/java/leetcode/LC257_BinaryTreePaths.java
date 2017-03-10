package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Administrator on 2017/2/25.
 */
public class LC257_BinaryTreePaths {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        LC.makeTreeNode(root, 2, 3);
        LC.makeTreeNode(root.left, 4, 5);
        LC.makeTreeNode(root.right, 6, 7);
        LC257_BinaryTreePaths test = new LC257_BinaryTreePaths();
        System.out.println(test.binaryTreePaths(root));
    }

    public List<String> binaryTreePaths(TreeNode root) {
        List<String> list = new ArrayList<>();
        LinkedList<TreeNode> paths = new LinkedList<>();
        if(root!=null){
            paths.add(root);
            searchebt(root, paths, list);
        }
        return list;
    }

    private void searchebt(TreeNode node, LinkedList<TreeNode> paths, List<String> list) {
        if (node.left == null && node.right == null) {
            StringBuilder sb = new StringBuilder();
            paths.forEach(tn -> sb.append(tn.val).append("->"));
            list.add(sb.substring(0, sb.length() - 2));
        }
        if (node.left != null) {
            paths.add(node.left);
            searchebt(node.left, paths, list);
            paths.removeLast();
        }
        if (node.right != null) {
            paths.add(node.right);
            searchebt(node.right, paths, list);
            paths.removeLast();
        }

    }

    //下面字符串拼接快多了

//    public List<String> binaryTreePaths(TreeNode root) {
//        List<String> answer = new ArrayList<String>();
//        if (root != null) searchBT(root, "", answer);
//        return answer;
//    }
    private void searchBT(TreeNode root, String path, List<String> answer) {
        if (root.left == null && root.right == null) answer.add(path + root.val);
        if (root.left != null) searchBT(root.left, path + root.val + "->", answer);
        if (root.right != null) searchBT(root.right, path + root.val + "->", answer);
    }


}
