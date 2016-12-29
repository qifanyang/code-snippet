package core.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

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
                   mysleep(1);
               }
            }
        }.start();

        for(int i = 0; i < 15; i++){
            executorService.execute(new Runnable(){
                @Override
                public void run(){
                    mysleep(10);
                }
            });
        }

        mysleep(3);
        for(int i = 0; i < 15; i++){
            executorService.execute(new Runnable(){
                @Override
                public void run(){
                    mysleep(10);
                }
            });
        }
    }

    private static void mysleep(int seconds){
        try{
            Thread.sleep(seconds*1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
