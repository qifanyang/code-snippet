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
        int[] nums1 = {2,0};
        int[] nums2 = {1};
        LC88_MergeSortArray test = new LC88_MergeSortArray();
        test.merge(nums1,1, nums2, 1);
        System.out.println(Arrays.toString(nums1));
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // Make a copy of nums1.
        int [] nums1_copy = new int[m];
        System.arraycopy(nums1, 0, nums1_copy, 0, m);

        // Two get pointers for nums1_copy and nums2.
        int p1 = 0;
        int p2 = 0;

        // Set pointer for nums1
        int p = 0;

        // Compare elements from nums1_copy and nums2
        // and add the smallest one into nums1.
        while ((p1 < m) && (p2 < n))
            nums1[p++] = (nums1_copy[p1] < nums2[p2]) ? nums1_copy[p1++] : nums2[p2++];

        // if there are still elements to add
        if (p1 < m)
            System.arraycopy(nums1_copy, p1, nums1, p1 + p2, m + n - p1 - p2);
        if (p2 < n)
            System.arraycopy(nums2, p2, nums1, p1 + p2, m + n - p1 - p2);

    }
}
