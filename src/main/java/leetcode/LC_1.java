package leetcode;

import java.util.Arrays;

/**
 * Created by yangqifan on 2020/8/25.
 */
public class LC_1 {

    public static void main(String[] args) {
        LC_1 lc_1 = new LC_1();
        System.out.println(Arrays.toString(lc_1.twoSum(new int[]{3,2,4}, 6)));
    }

    public int[] twoSum(int[] nums, int target) {
        //不要回忆以前的做题答案, 分析当前问题, 不同类型算法都有套路解法
        //类似公式的解决方法


        for (int i = 0; i < nums.length - 1; i++) {
            int v = target - nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                if(nums[j] == v){
                    return new int[]{i, j};
                }
            }
        }
        return nums;
    }
}
