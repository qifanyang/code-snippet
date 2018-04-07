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

        //测试core size为0
        //提交任务到线程池时,线程池需要再次检测线程数量,可能某些线程异常退出,
        //当把任务放到workqueue后再次检测工作线程数量,因为coreSize为0,所以
        //workerCount值为0,调用addWorker(null, false),添加一个非core线程
        //这样就会有一个线程来执行workeQueue中的任务.
        //结论:如果core size为0,在workQueue满之前,只有一个线程来执行
        //同core size为1有同样的效果
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 3, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1));


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
