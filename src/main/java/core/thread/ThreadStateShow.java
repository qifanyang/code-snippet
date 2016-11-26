package core.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * new Thread()---->thread.start()----->run中执行wait/wait(time)--->run中synchronized没有获取到monitor--->run方法结束
 *      NEW    ---->  RUNNABLE    ----->WAITING/TIMED_WAITING   --->                BLOCKED           ---> TERMINATED
 * @author yangqf
 * @version 1.0 2016/11/11
 */
public class ThreadStateShow{
    static volatile boolean hasPark = false;
    public static void main(String[] args) throws InterruptedException{
        System.out.println("thread id = " + Thread.currentThread().getId());
        Thread thread = new Thread(() -> {
            System.out.println("new thread id = " + Thread.currentThread().getId());
            int i = 0;
            while(true){
                i++;
            }
        });
        //没有调用start,thread只是一个普通的线程对象,需要调用start本地方法,jvm创建本地操作系统线程并关联thread对象
        //赋予thread对象多线程的功能
//        thread.run();
        System.out.println("created and not execute start , state = "+thread.getState());
        //start后,jvm创建操作系统线程,初始化并运行,会自动调用run方法,这里的run方法作为新线程要执行的逻辑代码
        thread.start();
        System.out.println("after call start , state = "+thread.getState());


//        synchronized(ThreadStateShow.class){
//            thread.wait();
//        }
        //上面的synchronized报错java.lang.IllegalMonitorStateException, 该异常是因为当前线程不是同步对象的拥有者
        //就是当前main method thread没有获取到thread对象的锁
        Thread mainThread = Thread.currentThread();
        new Thread("查看thread线程状态"){
            @Override
            public void run(){
                int x = 0;
                sleepSecond(3);
                while(true){
                    System.out.println("thread state = " + thread.getState());
                    System.out.println("main thread state = " + mainThread.getState());
                    sleepSecond(2);
                    if(x++ > 3){
                        LockSupport.unpark(mainThread);
                        //测试结果表示无法唤醒TIMED_WAITING,也无法唤醒WAITING
                        //分析:park和unpark有维护自己曾经操作过的线程,只有park过unpark才会有效,不会去操作内置锁的等待集
                        //所以不要与wait混用
                        System.out.println("unpark main thread");
                    }
                    if(hasPark){
                        System.out.println("main thread had call park, sleep 2 second and then  try get main thread monior");
                        sleepSecond(2);
                        synchronized (mainThread){
                            System.out.println("获取主线程锁成功");
                        }
                    }
                }
            }
        }.start();


        sleepSecond(2); //wait之前先sleep,造成main thread 状态TIMED_WAITING
        synchronized(thread){//获取thread对象锁
            //造成当前线程挂起,直到其它线程在thread对象上执行notify
            //并不会导致thread线程被挂起,所以thread线程状态为RUNNABLE
            System.out.println("thread call wait");
            thread.wait();//主线程等待LockSupport.unpark
            System.out.println("jjjjjjjjjjjjjjjjjjjjjj");
        }
        System.out.println("after call wait , state = "+thread.getState());
        System.out.println("main thread call sleep");
        sleepSecond(3);
        System.out.println("main thread will call LockSupport.park() ");
        /**
         * 该调用导致当前线程挂起,waiting状态, 其它线程可以使用LockSupport.unpark(Thread)来唤醒该线程
         * 或者使用中断,该方法是告诉jvm线程调度将当前线程挂起不参与调度
         */
//        LockSupport.unpark(Thread.currentThread());
        hasPark = true;
        LockSupport.park();
        System.out.println("if before call park had call unpark , will no effect ..");
    }

    private static void sleepSecond(int second){
        try{
            Thread.sleep(second*1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
