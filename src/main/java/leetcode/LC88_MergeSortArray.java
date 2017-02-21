package leetcode;

import java.util.Arrays;

/**
 * @author yangqf
 * @version 1.0 2017/2/20
 */
public class LC88_MergeSortArray{

    public static void main(String[] args){
        System.out.println(1^1);
        System.out.println(1^0);
        int[] nums1 = {1};
        int[] nums2 = {};
        LC88_MergeSortArray test = new LC88_MergeSortArray();
        test.merge(nums1,nums1.length, nums2, nums2.length);
        System.out.println(Arrays.toString(nums1));
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if(n == 0){
            return;
        }
        int[] result = new int[m+n];
        int start1 = 0, start2 = 0, i = 0;
        while(start1 < m || start2 < n){
            if(start1 < m && nums1[start1] < nums2[start2]){
                result[i++] = nums1[start1++];
            }else {
                result[i++] = nums2[start2++];
            }

        }
        System.arraycopy(result, 0, nums1,0, result.length);
    }
}
