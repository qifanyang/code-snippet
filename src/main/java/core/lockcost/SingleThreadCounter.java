package core.lockcost;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/1/25
 */
public class SingleThreadCounter {
    public static void main(String[] args) {
        long cnt = 0;
        long start = System.nanoTime();
        for(long i = 0; i < 500000000l; i++){
            ++cnt;
        }
        System.out.print((System.nanoTime() - start)/1000/1000);//297ms
        System.out.println("ms");
    }
}
