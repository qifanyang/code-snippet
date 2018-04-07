package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *  算法题目要静下心思考,基础算法掌握好解决复杂点的算法思路更多更快
 *
 *  DFS, 需要一直递归下去,需要注意递归停止条件,
 *  比如 node==null(比左边多调用一次), node.left==null&&node.right==null(少一次方法调用)
 *
 *  BFS, 需要先收集一个节点,然后再来一层一层收集,
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
//        test.levelOrder(root);
        test.breadthFirstSearch(root);
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        int level = 0;
        List<List<Integer>> list = new ArrayList<>(10);
        traversal(root, level, list);
        System.out.println(list);
        return list;
    }

    /**
     * depth firt search 深度优先,先沿着一个节点一直递归下去
      * @param node
     * @param level
     * @param list
     */
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

    private void breadthFirstSearch(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        List<List<TreeNode>> resultList = new ArrayList<>();
        if(null == root)return;

        queue.add(root);
        while (!queue.isEmpty()) {
            //广度优先,需要收集树形结构每一层有多少个节点,然后继续收集下一层有多少节点
            int size = queue.size();
            List<TreeNode> subList = new ArrayList<>();
            for(int i = 0; i < size; i++){
                //第一层size为1 只取一次, 第二层size为2 会取两次, 以此类推
                //没有递归,一层一层的遍历
                TreeNode node = queue.poll();
                //收集下一层节点
                if(node.left != null) queue.add(node.left);
                if(node.right != null) queue.add(node.right);
                subList.add(node);
            }
            resultList.add(subList);
        }

        System.out.println(resultList);
        //交换list中的值
        resultList.forEach(subList->{
            int start = 0, end = subList.size() - 1;
            while (start < end){
                int temp = subList.get(start).val;
                subList.get(start).val = subList.get(end).val;
                subList.get(end).val = temp;
                start++;
                end--;
            }
        });

    }

}
