package core.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * sleep会导致线程被占用,不归还到线程池中
 * @author yangqf
 * @version 1.0 2016/12/29
 */
public class ThreadPoolForSleep{
    public static void main(String[] args){
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

        new Thread(){
            @Override
            public void run(){
               while(true){
                   System.out.println("active count = " + executorService.getActiveCount());
                   System.out.println("queue size = " + executorService.getQueue().size());
                   mysleep(1,  TimeUnit.SECONDS);
               }
            }
        }.start();

        for(int i = 0; i < 15; i++){
            executorService.execute(() -> mysleep(10,  TimeUnit.SECONDS));
        }

        mysleep(3,  TimeUnit.SECONDS);
        for(int i = 0; i < 15; i++){
            executorService.execute(new Runnable(){
                @Override
                public void run(){
                    mysleep(10, TimeUnit.SECONDS);
                }
            });
        }
    }

    private static void mysleep(int num, TimeUnit tu){
        try{
            Thread.sleep(tu.MILLISECONDS.toMillis(num));
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
