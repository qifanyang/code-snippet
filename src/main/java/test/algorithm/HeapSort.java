package test.algorithm;

/**
 * http://shmilyaw-hotmail-com.iteye.com/blog/1775868
 *
 * @author yangqf
 * @version 1.0 2016/4/13
 */
public class HeapSort{
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
        for(int i = a.length / 2; i >= 0; i--)
            maxHeapify(a, i, a.length);
    }

    public static void swap(int[] a, int i, int j){
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void heapSort(int[] a){
        buildMaxHeap(a);
        int length = a.length;
        for(int i = a.length - 1; i > 0; i--){
            swap(a, i, 0);
            length--;
            maxHeapify(a, 0, length);
        }
    }

    public static void main(String[] args){
        Object o = new Object();
        try{
            o.wait();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        int[] a = {4, 1, 3, 2, 16, 9, 10, 14, 8, 7};
        buildMaxHeap(a);
        //maxHeapify(a, 2);
        for(int i = 0; i < a.length; i++)
            System.out.print(a[i] + " ");
        System.out.println();

        heapSort(a);
        for(int i = 0; i < a.length; i++)
            System.out.print(a[i] + " ");
        System.out.println();
    }
}

