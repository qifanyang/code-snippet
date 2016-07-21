package test.algorithm;

/**
 * http://shmilyaw-hotmail-com.iteye.com/blog/1775868
 * 最大堆与最小堆
 * 最小堆:父节点值小于等于子节点值
 * 使用数组来表示完全二叉树(除最底层外,每个节点达到最大节点数,最底层节点从左边开始插入),这个数组叫堆,在树形结构中从上到下,从左到右编号,存在以下关系
 * 数组和完全二叉树,只需要将数组索引对应树节点即可,构建最大堆就是按照一定规则重新排列数组元素顺序,这个规则就是完全二叉树和最大堆
 *
 * 左子节点=父节点编号*2
 * 右子节点=父节点编号*2+1
 * 因为数组从0开始,所以采用
 * 左子节点=父节点编号*2+1
 * 右子节点=父节点编号*2+2
 *
 *  最大堆,父节点值要大于其子节点, 根据节点值调整节点之间位置,叫做heapify
 *  如何调整,从根节点开始,如果根节点满足最大堆要求,不用调整,然后深入子节点继续调整,可能子节点调整上来的值比根节点还大
 *  所以还要和根节点比较,所以从根节点开始调整行不通
 *
 *  从子节点开始,最底层的叶子节点没有子节点,不用比较,所以从倒数第二层从下往上调整, 数组长度/2, 作为从下往上调整的起点
 *  每次调整堆后,根节点就会是最大值,然后把最大值放到数组的最后,
 *
 * @author yangqf
 * @version 1.0 2016/4/13
 */
public class HeapSort{
//    public static int left(int i){
//        return i * 2 + 1;
//    }
//
//    public static int right(int i){
//        return i * 2 + 2;
//    }
//
//    public static void maxHeapify(int[] a, int i, int length){
//        int l = left(i);
//        int r = right(i);
//        int largest = i;
//        while(true){
//            if(l < length && a[l] > a[i])
//                largest = l;
//            if(r < length && a[r] > a[largest])
//                largest = r;
//            if(i != largest)
//                swap(a, i, largest);
//            else
//                break;
//            i = largest;
//            l = left(largest);
//            r = right(largest);
//        }
//    }
//
//    public static void buildMaxHeap(int[] a){
//        for(int i = a.length / 2 - 1; i >= 0; i--)//使用int i = a.length / 2 满二叉树会去掉叶子节点
//            maxHeapify(a, i, a.length);
//    }
//
//    public static void swap(int[] a, int i, int j){
//        int temp = a[i];
//        a[i] = a[j];
//        a[j] = temp;
//    }

    public static void heapSort(int[] a){
        MaxHeap.buildMaxHeap(a);//构建完全二叉树,符合父节点值比子节点大
        int length = a.length;
        for(int i = a.length - 1; i > 0; i--){
            MaxHeap.swap(a, i, 0);//将根节点值放到数组末尾
            length--;//相当于末尾的值不参数堆调整了
            MaxHeap.maxHeapify(a, 0, length);//对于一个最大堆可以从根节点开始调整
        }
    }

    public static void main(String[] args){
        int[] a = {4, 1, 3, 2, 16, 9, 10, 9, 8, 7};
        MaxHeap.buildMaxHeap(a); //虽然符合最大堆条件, 但是一个节点的两个子节点没有顺序
        for(int i = 0; i < a.length; i++)
            System.out.print(a[i] + " ");
        System.out.println();

        heapSort(a);
        for(int i = 0; i < a.length; i++)
            System.out.print(a[i] + " ");
        System.out.println();
    }
}

