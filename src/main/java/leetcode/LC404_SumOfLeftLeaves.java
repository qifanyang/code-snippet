package leetcode;

/**
 * Created by Administrator on 2017/2/25.
 */
public class LC404_SumOfLeftLeaves {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        LC.makeTreeNode(root, 2, 5);
        LC.makeTreeNode(root.right, 6, 7);
        LC404_SumOfLeftLeaves test = new LC404_SumOfLeftLeaves();
        System.out.println(test.sumOfLeftLeaves(root));
    }

    public int sumOfLeftLeaves(TreeNode root) {
        if(root == null)return 0;//递归停止

        int reslut = 0;//最外层准备收集最终结果
        //递归
        int l = sumOfLeftLeaves(root.left);//开始递归
        int r = sumOfLeftLeaves(root.right);


        //递归到达没有子节点的时候,开始处理逻辑
        //比如这里是收集左叶子节点的值,判断左边节点是不是叶子节点,收集值即可
        //加上l和r是因为,l和r存储了其它节点返回值,所以加上
        //只计算左边节点的值,并且是子节点
        if(root.left != null){
            if(root.left.left == null && root.left.right == null){
                return root.left.val+l+r;
            }
        }

        reslut += (l + r);//结果汇总

        return reslut;
    }
}
