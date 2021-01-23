package leetcode;

/**
 * Created by yangqifan on 2020/8/25.
 */
public class LC_27 {

    public static void main(String[] args) {

    }

    public int removeElement(int[] nums, int val) {
        /**
         * 数组双指针
         * for i 作为游标遍历数组
         * 慢指针用于记录去重后的数组
         *
         * 快指针用于当发现重复元素时skip到下一个元素,而慢指针不动
         *
         *
         */

        if(nums.length == 1){
            if(nums[0] == val){
                return 0;
            }else{
                return 1;
            }
        }
        int last = 0, forward = 0;
        for(int i = 0; i < nums.length; i++){
            if(nums[i] != val){
                if(last != forward){
                    nums[last] = nums[forward];
                }
                last++;
                forward++;
            }else{
                forward++;
            }
        }
        return last;
    }
}
