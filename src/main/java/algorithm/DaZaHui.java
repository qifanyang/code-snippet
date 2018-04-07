package algorithm;

import java.util.Arrays;

/**
 * Created by yangqifan on 30/10/2017.
 */
public class DaZaHui {

    private static void mergeArray(){
        int[] a = {2, 3, 8, 10};//有序
        int[] b = {4, 5, 17, 20, 80, 90};//有序
        int[] c = new int[a.length+b.length];
        int i = 0, j = 0, z = 0;
        //从两个数组中选择小的数放到数组c中,需要考虑某个数组被选完了,另外一个数组直接拷贝
        while (i < a.length && j < b.length){
            if(a[i] < b[j]){
                c[z++] = a[i++];
            }else {
                c[z++] = b[j++];
            }
        }

        while (i < a.length){
            c[z++] = a[i++];
        }

        while (j < b.length){
            c[z++] = b[j++];
        }

        System.out.println(Arrays.toString(c));
    }

    private static int binarySearch(int[] x, int target, int low, int high){
        if(high < low){
            System.out.println("not fund");
            return -1;
        }
        int mid = (low + high)/2;
        if(target > x[mid]){
            return binarySearch(x, target, mid+1, high);
        }else if(target < x[mid]){
            return binarySearch(x, target, low, mid-1);
        }else if(target == x[mid]){
            return mid;
        }

        return -1;

    }

    public static void main(String[] args) {
        int[]  x = {1,3,4,45,88, 100};
        int index = binarySearch(x, 100, 0, x.length - 1);
        System.out.println(x[index]);
        index = binarySearch(x, 1, 0, x.length - 1);
        System.out.println(x[index]);
        index = binarySearch(x, 40, 0, x.length - 1);
        System.out.println(x[index]);
//        mergeArray();
    }
}
