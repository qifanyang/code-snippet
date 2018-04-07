import algorithm.QuickSort;

import java.util.Arrays;

/**
 * 关键步骤:
 * 1.寻找参照点
 * 2.将小于参照点的值放到一边,将大于参照点的值放到一边
 * Created by yangqifan on 2018/3/13.
 */
public class QuickSortRecall {

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
        int low = Integer.MAX_VALUE -10;
        int high = Integer.MAX_VALUE -10;
        System.out.println((low + high));
        System.out.println((low + high) >>> 1);
        int[] s = {1,5,3,70,4};

        int midIdx = s.length / 2;

        int[] arr = {3, 4};

//        QuickSort.quickSort(new int[]{88,99}, 0, 1);
        System.out.println(Arrays.toString(arr));
        sort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));

    }


    private static void sort(int[] s, int start, int end){
        int i = start, j = end;
        int mid = (i + j) / 2;
        //partition
        while (i <= j){
            while (s[i] < s[mid]){//
                i++;
            }
            while (s[j] > s[mid]){
                j--;
            }
            //swap
            if(i <= j){//等号为了让i和j交替
                int temp = s[i];
                s[i] = s[j];
                s[j] = temp;
                //交换值后需要继续移动和j,以完成分区
                i++;
                j--;
            }
        }


        if(start < j){
            sort(s, start, j);
        }

        if(i < end){
            sort(s, i, end);
        }

    }
}
