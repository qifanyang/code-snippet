package test.core.lockcost;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/1/25
 */
public class SingleTreadCounterWithLock {

    public static void main(String[] args) {
        long  cnt = 0;
        ReentrantLock lock = new ReentrantLock();
        long start = System.nanoTime();
        for(long i = 0; i < 500000000l; i++){
            lock.lock();
            ++cnt;
            lock.unlock();
        }
        //500000000
        //500 000 000
        System.out.print((System.nanoTime() - start)/1000/1000);//10598/297
        System.out.println("ms");
    }
}
