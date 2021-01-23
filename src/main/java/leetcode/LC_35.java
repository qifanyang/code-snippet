package leetcode;

/**
 * Created by yangqifan on 2020/8/24.
 */
public class LC_35 {

    public static void main(String[] args) {
        LC_35 lc_35 = new LC_35();
        int i = lc_35.searchInsert(new int[]{1, 3, 5, 6}, 7);
        System.out.println(i);
    }

    public int searchInsert(int[] nums, int target) {
        //二分查找
        //跟数组中间元素value比较, 相等则返回
        //target小于value则在数组前半部分继续查找
        //target大于value则在数组后半部分继续查找
        //递归和迭代

        //双指针算法
        int start = 0, end = nums.length - 1;
        while (end - start > 1) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }

        if (nums[start] == target) {
            return start;
        }
        if (nums[end] == target) {
            return end;
        }
        if (nums[start] > target) {
            return start == 0 ? 0 : start - 1;
        }
        if (nums[end] < target) {
            return end + 1;
        }


        return start + 1;

    }
}
