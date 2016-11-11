package core.thread;

/**
 * new Thread()---->thread.start()----->run中执行wait/wait(time)--->run中synchronized没有获取到monitor--->run方法结束
 *      NEW    ---->  RUNNABLE    ----->WAITING/TIMED_WAITING   --->                BLOCKED           ---> TERMINATED
 * @author yangqf
 * @version 1.0 2016/11/11
 */
public class ThreadStateShow{
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
                sleepSecond(3);
                while(true){
                    System.out.println("thread state = " + thread.getState());
                    System.out.println("main thread state = " + mainThread.getState());
                    sleepSecond(2);
                }
            }
        }.start();


        sleepSecond(8); //wait之前先sleep,造成main thread 状态TIMED_WAITING
        synchronized(thread){//获取thread对象锁
            //造成当前线程挂起,直到其它线程在thread对象上执行notify
            //并不会导致thread线程被挂起,所以thread线程状态为RUNNABLE
            System.out.println("thread call wait");
            thread.wait();
        }
        System.out.println("after call wait , state = "+thread.getState());

    }

    private static void sleepSecond(int second){
        try{
            Thread.sleep(second*1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
