package algorithm;

import java.util.Arrays;
import java.util.Random;

/**
 * https://segmentfault.com/a/1190000002595152
 * @author yangqf
 * @version 1.0 2016/4/11
 */
public class Sort{

    public static int[] a = {5,8,2,33};

    /**
     * divide-and-conquer 分而治之
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





    /**
     * 复杂度n+k, 其实2n+k
     * k 为数据最大值
     * link 根据登录下线日志,统计每秒在线人数
     * @param A
     * @param B
     * @param k
     */
    public static void countingSort(int[] A,  int[] B, int k){

        int[] C = new int[k + 1];//C值为出现次数, 索引为待排序的值
        B = new int[A.length];

        for (int j = 0; j < A.length; j++){
            C[A[j]]++;//扫描计算每个数字的出现次数
        }

        for (int i = 1; i <= k; i++){
            C[i] += C[i-1];//为展开计算展开时每个元素的索引(这里是结束位置)C[3,2,4]--->C[3,5,9]
        }

        for (int j = A.length - 1; j >= 0; j--){
            B[C[A[j]]-1] = A[j];//A[j]为待排序数组元素, 确定A[j]将要放到的位置,C[A[j]]改元素出现次数, 减1是因为次数从1开始, 比如出现3次  代表 0 1 2 三个索引位置
            C[A[j]]--;//出现次数减1
        }
        //去掉B, 存储在A中, 勇哥临时变量存储被覆盖的A值
        printArrayValue(B);
    }

    /**
     * 计数排序需要知道最大值k, 对于很大的k采用计数排序则不可行, 比如java数组最大长度只能为Integer.MAX_VALUE
     * 这是因为jvm规范规定了创建数组长度为int, jvm字节码指令只支持int长度, 21亿int~~ 8.4G, 已经完全够了
     * 这种方法也是2n+k, 但是更简单, 少了B
     * @param A
     * @param k 最大值
     */
    public static void countingSortBetter(int A[], int k){
        int[] C = new int[k + 1];
        int[] cxxx = new int[Integer.MAX_VALUE/40];
        for (int j = 0; j < A.length; j++)
        {
            C[A[j]]++;
        }

        int z = 0;

        for (int i = 0; i <= k; i++)
        {
            while (C[i]-- > 0)
            {
                A[z++] = i;//C的下标就是A中的值,C[i]是出现次数
            }
        }
    }

    //多路归并排序

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

    public static int[] reset(){
       return new int[]{11, 2, 4, 8, 3};
    }

    public static void main(String[] args){
        int a[] = reset();
//        a = new int[1000000];
//        Random rand = new Random();
//        for (int i = 0; i < a.length; i++)
//        {
//            a[i] = rand.nextInt(1000);
//        }
//        Arrays.sort(a);
        int[] B = Arrays.copyOf(a, a.length);
//        printArrayValue(a);
        long start = System.nanoTime();
        quickSort(a, 0, a.length-1);
        printArrayValue(a);
        System.out.println(System.nanoTime() - start);
        a = reset();
        MergeSort.mergeSort(a, new int[a.length], a.length);
//        countingSort(a, new int[a.length], 11);
        start = System.nanoTime();
        countingSortBetter(B, 1000);//100w数据计数排序快于快排
        System.out.println(System.nanoTime() - start);
//        printArrayValue(a);
    }
}
