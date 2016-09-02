package algorithm;

/**
 * @author yangqf
 * @version 1.0 2016/4/14
 */
public class MaxHeap{
    public static int left(int i){
        return i * 2 + 1;
    }

    public static int right(int i){
        return i * 2 + 2;
    }

    public static void maxHeapify(int[] a, int i, int length){
        int l = left(i);
        int r = right(i);
        int largest = i;
        while(true){
            if(l < length && a[l] > a[i])
                largest = l;
            if(r < length && a[r] > a[largest])
                largest = r;
            if(i != largest)
                swap(a, i, largest);
            else
                break;
            i = largest;
            l = left(largest);
            r = right(largest);
        }
    }

    public static void buildMaxHeap(int[] a){
        for(int i = a.length / 2 - 1; i >= 0; i--)//使用int i = a.length / 2 满二叉树会去掉叶子节点
            maxHeapify(a, i, a.length);
    }

    public static void swap(int[] a, int i, int j){
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
