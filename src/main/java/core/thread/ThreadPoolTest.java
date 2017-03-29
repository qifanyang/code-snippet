package core.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/3/25.
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
        //1.提交任务小于coreSize则新建线程
        //2.达到coreSize则入队,其它core线程会从队列取任务执行
        //3.队列满了新建线程执行,不能超过maxSize
        //4.队列满了,也达到maxSize,执行reject策略
        //先提交的任务不一定先执行?
        //a.线程上下文切换
        //b.任务入队刚好任务队列满了,下一个任务会直接新建线程执行
        //c.
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 3, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1));


        for(int i = 0; i < 6; i++){
            executor.execute(()->{
                try {
                    Thread.sleep(TimeUnit.DAYS.toMillis(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }


    }
}
