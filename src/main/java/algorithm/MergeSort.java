package algorithm;

/**
 * 归并排序是唯一O(nlogn)稳定的排序算法.java collections.sort使用归并来排序引用类型顺序
 * 1.分解输入到两个子序列,
 * 2.递归继续分解,然后排序
 * 3.两两子序列合并,从两个子序列的最左边开始,比较大小,然后输出到一个中间数组(辅助空间,浪费内存)
 * @author yangqf
 * @version 1.0 2016/4/13
 */
public class MergeSort{

    /**
     * John von Neumann in 1945 invented
     * @param a
     */
    public static void mergeSort(int a[], int b[], int n){
        topDownSplitMerge(a, 0, n, b);
    }

    /**
     * 唯一一个稳定O(nlogn)
     * 归并排序,先是split到只剩下一个元素, 然后再合并, 两路合并
     * B用来存放归并结果,比较耗内存
     * @param A
     * @param iBegin
     * @param iEnd
     * @param B
     */
    private static void topDownSplitMerge(int[] A, int iBegin, int iEnd, int[] B){
        if(iEnd - iBegin < 2)                       // if run size == 1, 只剩下一个元素
            return;                                 //   consider it sorted
        // recursively split runs into two halves until run size == 1,
        // then merge them and return back up the call chain
        int iMiddle = (iEnd + iBegin) / 2;              // iMiddle = mid point
        //当只剩1个元素就停止了,拆分时类似深度优先,递归把左边的分解,类似下图横线,每次合并,紧接着拆分对应的右边部分
        //-----------------------
        //------------
        //-----
        //--
        //-
        topDownSplitMerge(A, iBegin, iMiddle, B);  // split / merge left  half, 递归将左边拆分
        topDownSplitMerge(A, iMiddle, iEnd, B);  // split / merge right half
        topDownMerge(A, iBegin, iMiddle, iEnd, B);  // merge the two half runs
        copyArray(B, iBegin, iEnd, A);              // copy the merged runs back to A
    }

    private static void topDownMerge(int[] A, int iBegin, int iMiddle, int iEnd, int[] B){
        int i = iBegin, j = iMiddle;

        //将两个子序列合并成一个序列,循环iend次,比较两个子序列左边的值得大小,放入B中
        //并同时增加对应其索引值(i或者j)
        // While there are elements in the left or right runs...
        for (int k = iBegin; k < iEnd; k++) {
            // If left run head exists and is <= existing right run head.
            if (i < iMiddle && (j >= iEnd || A[i] <= A[j])) {//j>=iEnd说明右边的序列全部放入B中了,直接把左边的序列放入B中
                B[k] = A[i];
                i = i + 1;
            } else {
                B[k] = A[j];
                j = j + 1;
            }
        }
    }

    private static void copyArray(int[] B, int iBegin, int iEnd, int[] A){
        for(int k = iBegin; k < iEnd; k++)
            A[k] = B[k];
    }

    //merge代码实现方式有很多种, 这种归并比较直观
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi)
    {
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];//拷贝到辅助数据,应为输入数组要用来存放最终排序结果
        int i = lo, j = mid + 1;//i为左边序列的起始位置, j为右边序列的起始位置
        for(int k = lo; k <= hi; k++)
        {
            if (i > mid) a[k] = aux[j++];//i > mid 说明左边已经全部放入辅助数组中了,而右边剩下也是有序的,所以直接放入辅助数组中
            else if(j > hi) a[k] = aux[i++];//j > hi, 说明右边已经全部放入辅助数组中,左边也是有序的直接放入辅助数组,
            else if (less(aux[j], aux[i])) a[k] = aux[j++];//比较i和j对应的值得大小,如果aux[j] < aux[i] 将小的放入输出数组中,j++, 这里用的是小于,稳定
            else a[k] = aux[i++];
        }
    }

    /**
     * a是否小于b
     * @param a
     * @param b
     * @return
     */
    private static boolean less(Comparable a, Comparable b){
        int i = a.compareTo(b);
        if(i < 0){
            return true;
        }else {
            return false;
        }
    }

    public static void main(String[] args){
        int a[] = Sort.a;
        Sort.printArrayValue(a);
        long start = System.nanoTime();
        //传a.length还是a.length-1会影响middle的计算,导致递归产生子序列有点不一样,但是最终结果都一样
        //减1这种划分得更均匀
        //比如有6个元素的数组, 6/2=3,  然后前四个元素划分为一组, 后两个一组,   5/2=2, 前三个一组后三个一组
        MergeSort.mergeSort(a, new int[a.length], a.length-1);
        System.out.println(System.nanoTime() - start);
        Sort.printArrayValue(a);
//     rayValue(a);
    }


}
