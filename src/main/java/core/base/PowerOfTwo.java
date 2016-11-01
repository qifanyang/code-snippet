package core.base;

import java.util.HashMap;

/**
 * @author yangqf
 * @version 1.0 2016/10/10
 */
public class PowerOfTwo{

    //MUST be a power of two
    //hash map的容量大小为2的倍数,如果初始化容量不会2的指数,那么会转换成大于并且最接近的2的倍数

    public static void main(String[] args){
        System.out.println(tableSizeFor(5));
        System.out.println(tableSizeFor(2));
        System.out.println(tableSizeFor(3));
        //0... 0000 0101
        //n>>>1 0... 0000 0010
        //      0... 0000 0101
        //最终为0.. 0000 0111
        //然后返回值0... 0000 0111 + 1 进位 这里十进制加法和二进制加法效果一样,返回

        PowerOfTwo powerOfTwo = new PowerOfTwo();
        powerOfTwo.test();

    }

    public void test(){
        System.out.println("cc");
        //明确知道map元素为2个,想节约点空间,开辟的数组小一点
        HashMap<Integer, Integer> map = new HashMap<>(3);//设置容量只是对map的threshold,管用,该值为传入容量大于并且最接近的2的倍数
        //传入3 threshold为4
        //第一次放入元素,会resize,使用threshold最为初始容量,所以并不是设置的容量
        //DEFAULT_LOAD_FACTOR 在调用HashMap无参构造是有用,根据DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY来计算threshold

        //所以对于两个元素,传入容量3, 接着计算threshold为4, 初始化数组大小就为4, 当size大于4 才会扩容
        //扩容策略一般为2的倍数,然后threshold为新的容量*LOAD_FACTOR, 这里就是4*0.75 , 为3, 当放入元素达到3就会扩容
        //所以设定初始容量需要手动计算threshold, 比如放入5个元素, 那么要保证threshold为5, initialCapacity*Load_factor为5
        //那么initialCapacity要为7,这样做真心没必要,所以对于小容量默认16就可以了,节约几个数组长度没必要

        //对于map容量很快就会变得很大的,初始化可以采用手动计算threshold,防止多次resize,提升效率
        //当容量大于16, threshold计算不在依赖load_factor,而是直接*2, 所以map容量很大最好不要resize

        map.put(1, 1);// 初始化 resize
        map.put(2, 1);// 不会 resize
        map.put(3, 1);//不会 resize
        map.put(4, 1);//initialCapacity 3, 真是capacity 4, threshold 3 所以这里会resize
    }

    /**
     * Returns a power of two size for the given target capacity.
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    static int test(int cap){
        int cnt = 0;
        while((cap = cap / 2) > 1){
            cnt++;
        }
        if(0 == cnt){
            return 2;
        }else {
            return (int) Math.pow(2, cnt+1);
        }
    }
    static final int MAXIMUM_CAPACITY = 1 << 30;
}
