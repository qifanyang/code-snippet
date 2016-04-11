package test.algorithm;

/**
 * @author yangqf
 * @version 1.0 2016/4/11
 */
public class Sort{

    /**
     * http://www.algolist.net/Algorithms/Sorting/Quicksort
     * @param arr
     * @param left
     * @param right
     */
    public static void quickSort(int arr[], int left, int right){
        int i = left, j = right;
        int tmp;
        int pivot = arr[(left + right) / 2];

      /* partition */
        while(i <= j){//停止条件是i > j
            while(arr[i] < pivot)//扫描左边,直到找到大于pivot, pivot数组中的一个值,所以不会死循环
                i++;
            while(arr[j] > pivot)//扫描右边,直到找到小于pivot,同上
                j--;
            //上面的两个循环找到了需要交换位置的两个数的下标
            if(i <= j){//
                tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
                i++;
                j--;
            }
        }
        ;

      /* recursion */
        if(left < j)//最终递归到两个元素,left==j, 然后终止
            quickSort(arr, left, j);
        if(i < right)
            quickSort(arr, i, right);
    }

    public static void printArrayValue(int arrayValue[]){
        System.out.print("[");
        for(int i = 0; i < arrayValue.length; i++){
            System.out.print(arrayValue[i]);
            if(i < arrayValue.length - 1){
                System.out.print(",");
            }
        }
        System.out.println("]");
    }

    public static void main(String[] args){
        int a[] = {11, 2, 3, 5, 4};
        printArrayValue(a);
        quickSort(a, 0, a.length-1);
        printArrayValue(a);
    }
}
