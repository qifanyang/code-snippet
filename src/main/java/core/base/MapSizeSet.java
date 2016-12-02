package core.base;

import java.util.HashMap;

/**
 * @author yangqf
 * @version 1.0 2016/10/10
 */
public class MapSizeSet{

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

        MapSizeSet powerOfTwo = new MapSizeSet();
        powerOfTwo.test();

    }

    public void test(){
        //明确知道map元素为2个,想节约点空间,开辟的数组小一点

        //a.创建map, 指定初始容量initialCapacity, map会对初始容量进行计算,使其值为刚好小于第一个2的倍数, 比如设置3,结果为4
        HashMap<Integer, Integer> map = new HashMap<>(3);
        //b.添加元素, new HashMap创建Map时不会初始化Map中的数组,第一次put时通过resize创建数组
        //第一次创建存放元素的数组,根据初始化容量创建数组,并设置threshold=initialCapacity*loaderFactor 4*0.75=3
        //放入元素后检查++size>threshold则继续resize()
        map.put(1, 1);// first put element, 初始化 resize,
        //c.放入元素
        map.put(2, 1);// size=2, 不大于threshold=3, 不会 resize
        //d.放入元素
        map.put(3, 1);//size=3, 不大于threshold=3, 不会 resize
        //e.放入元素
        map.put(4, 1);//size=4, 大于threshold 3 所以这里会resize
        //非初始化resize规则
        //1.容量大小策略   , oldCap << 1, 采用容量翻倍策略
        //2.threshold策略, 当容量小于16,采用容量*loadFactor, 当容量大于16 , 采用oldThr << 1 , 阙值翻倍

        //总结:
        //1. size大于threshold会resize,
        //2. 初始化resize和非初始化resize行为会不同,当容量大于16采用容量翻倍策略

        //3. 对于map容量很快就会变得很大的,初始化可以采用手动计算threshold,防止多次resize,提升效率
        //4. 当容量大于16, threshold计算不在依赖load_factor,而是直接*2, 所以map容量很大最好不要resize
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
