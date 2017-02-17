package leetcode;

import java.util.Arrays;

/**
 * @author yangqf
 * @version 1.0 2017/2/17
 */
public class LC189_RotateArray{

    public static void main(String[] args){
        rotate(reset(), 3);
        roateFirtElement2Right(reset());
        rotate(reset());
    }
    static int[] reset(){
        int[] nums = new int[]{1,2,3,4,5,6,7};
        return nums;
    }

    /**
     * [1,2,3,4,5] -> [2,3,4,5,1]
     * @param nums
     */
    private static void roateFirtElement2Right(int[] nums){
        //为了保证空间复杂组O(1),不能新建数组,自由用一个临时变量
        //从index=0开始,依次和最后一个位置交换
        System.out.println("roateFirtElement2Right before:"+Arrays.toString(nums));
        int temp;
        for(int i = 0; i < nums.length; i++){
            temp = nums[i];
            nums[i] = nums[nums.length - 1];
            nums[nums.length - 1] = temp;
        }
        System.out.println("roateFirtElement2Right after:"+Arrays.toString(nums));
    }

    /**
     * 两两交换,实现顺序与逆序
     * @param nums
     */
    private static void rotate(int[] nums){
        System.out.println("rotate before :"+Arrays.toString(nums));
        int start = 0;
        int end = nums.length - 1;
        while(start < end){
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
        System.out.println("rotate after :"+Arrays.toString(nums));
    }


    /**
     *
     * @param nums
     * @param k
     */
    public static void rotate(int[] nums, int k) {
        System.out.println("before : " + Arrays.toString(nums));
        int temp, previous;
        for (int i = 0; i < k; i++) {
            previous = nums[nums.length - 1];
            for (int j = 0; j < nums.length; j++) {
                temp = nums[j];
                nums[j] = previous;
                previous = temp;
            }
            System.out.println("after : " + Arrays.toString(nums));
        }
    }
}
