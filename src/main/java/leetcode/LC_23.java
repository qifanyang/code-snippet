package leetcode;

/**
 * Created by yangqifan on 2020/8/20.
 */
public class LC_23 {

    public static void main(String[] args) {
        LC_23 lc_23 = new LC_23();
        int[] nums = {0,0,1,1,1,2,2,3,3,4};
        lc_23.removeDuplicates(nums);
    }



    public int removeDuplicates(int[] nums) {
        //分为两步
        //删除一个元素(移动后面的元素到前面)
        //发现重复元素
        int newlength = 1;
        int deleteCount = 0;
        for(int i = 0; i < nums.length - 1;){
            if(nums[i] == nums[i+1]){
                //delete(i, nums);
                //使用System.arraycopy 删除数组中一个元素
                int numMoved = nums.length - i - 1;
                if (numMoved > 0)
                    System.arraycopy(nums, i+1, nums, i,
                            numMoved);
                if(++deleteCount > nums.length){
                    break;
                }
            }else {
                i++;
            }
        }
        for(int i = 0; i < nums.length-1; i++){
            if(nums[i] != nums[i+1]){
                newlength++;
            }
        }
        return newlength;

    }

    /**
     * 删除数组中某个元素, 将后面的元素向前移动覆盖, 最后一个元素无法移除, 如果是对象可以使用null移除
     * @param i
     * @param nums
     */
    void delete(int i, int[] nums){
        for(int start = i; start < nums.length - 1;start++){
            nums[start] = nums[start+1];
        }
    }
}
