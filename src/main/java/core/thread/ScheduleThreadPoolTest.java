package core.thread;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/11/13.
 */
public class ScheduleThreadPoolTest {
    public static void main(String[] args) {
        ScheduledThreadPoolExecutor s = new ScheduledThreadPoolExecutor(4);

//        for(int i = 0; i < 10; i++)
        s.scheduleAtFixedRate(()->{
            System.out.println(Thread.currentThread().getName());
//            throw new RuntimeException("exp");

        },1, 1 , TimeUnit.SECONDS);
    }
}
