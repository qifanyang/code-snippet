package leetcode;

/**
 * BST->binary search tree
 * Created by Administrator on 2017/3/11.
 */
public class LC530_MinimumAbsoluteDifferenceInBST {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(236);
        LC.makeTreeNode(root, 104, 701);
        LC.makeTreeNode(root.left, null, 227);
        LC.makeTreeNode(root.right, null, 911);
        LC530_MinimumAbsoluteDifferenceInBST test = new LC530_MinimumAbsoluteDifferenceInBST();
        System.out.println(test.getMinimumDifference(root));
    }

    public int getMinimumDifference(TreeNode root) {
        if(root.left == null && root.right == null){
            return Integer.MAX_VALUE;
        }

        int lmin = Integer.MAX_VALUE;
        int rmin = Integer.MAX_VALUE;
        if(root.left != null){
            int l1 = getMinimumDifference(root.left);
            int l2 = Math.abs(root.val - root.left.val);
            if(l1 > l2){
                lmin = l2;
            }else {
                lmin = l1;
            }
        }
        if(root.right != null){
            int r1 = getMinimumDifference(root.right);
            int r2 = Math.abs(root.val - root.right.val);
            if(r1 > r2){
                rmin = r2;
            }else {
                rmin = r1;
            }
        }

        return lmin > rmin ? rmin : lmin;
    }


}
