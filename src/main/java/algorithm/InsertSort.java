package algorithm;

/**
 * 1.假如数组是有序的,则不用回溯i之前的元素,时间复杂度则是O(n)
 * 2.加入是逆序,每次都要回溯到起始位置0, 则时间复杂度O(n2), 如果无序的部分在数组前半部分回溯的次数会少于无序数据在后半部分
 * 3.只需要一个临时变量用于交换,空间复杂度O(1)
 * 规模小的时候采用,
 * @author yangqf
 * @version 1.0 2016/4/14
 */
public class InsertSort{
    static int[] arraytoSort = {5,8,2,33};

    public static void sort(){
        int temp;
        for(int i = 1; i<arraytoSort.length; i++){//从数组第二个元素开始遍历,然后一次递增
            //
            for(int j = i-1; j>=0; j--){
            //每次i下移一个元素,都要和前面的元素比较, 所以在i之前的元素都是有顺序的,如果新的元素更小(从小到大排序),
            // 则会像冒泡一样两两交换一直到找到比其更小的元素出现,因为左边的是有序的,所以找到了就停止,没必要继续冒泡小区
                if( arraytoSort[j+1] < arraytoSort[j] ){
                    temp = arraytoSort[j+1];
                arraytoSort[j+1] = arraytoSort[j];
                arraytoSort[j] = temp;
            }
            }
        }
    }

    public static void main(String[] args){
        Sort.printArrayValue(arraytoSort);
        sort();
        Sort.printArrayValue(arraytoSort);
    }
}
