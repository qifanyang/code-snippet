package core.base;

import java.lang.ref.WeakReference;

/**
 * 测试线程退出,ThreadLocalMap的中的Entry是否可以被垃圾回收
 * 结论:线程退出后,gc可以回收threadLocalMap中的线程本地变量,不论线程对象是否指向null
 * gc可以知道线程退出,然后回收其线程本地变量
 *
 *
 * @author yangqf
 * @version 1.0 2016/7/20
 */
public class WeakRefrenceTest{
    public static void main(String[] args) throws InterruptedException {
//        WeakReference<String> wr = new WeakReference<>(new String("ffff"));
//        System.out.println(wr.get());
//        System.gc();
//        System.out.println(wr.enqueue());
//        System.out.println(wr.get());

        showFreeMemory("初始化空闲内存");

        Service service = new Service();

        WorkThread workThread = new WorkThread(service);
        workThread.start();

        System.out.println("主线程暂停3s,等待工作线程执行");
        Thread.sleep(3000L);

        showFreeMemory("工作线程存储大数据对象后空闲内存");

        workThread.interrupt();//这一步表示线程异常退出
//        workThread = null;//线程异常退出需要将线程引用置null, gc工作.线程池会移除线程引用
        System.gc();

        showFreeMemory("线程异常退出后空闲内存");
        Thread.sleep(3000L);
        System.gc();
        showFreeMemory("暂停3s,再次gc后空闲内存");

        /*
        结论:线程退出后,gc可以回收threadLocalMap中的线程本地变量



         */
    }

    /**
     * 在workThread中执行的service
     */
    static class Service{
        private static ThreadLocal<byte[]> key = new ThreadLocal(){
            @Override
            protected byte[] initialValue() {
                int size = 100*1024*1024;
                return new byte[size];
            }
        };

        public void doSomeThing(){
            byte[] bytes = key.get();
            System.out.println(bytes.length);
        }

    }

    /**
     * 工作线程,存储一个测试大数据对象
     */
    static class WorkThread extends Thread{
        private Service service;

        public WorkThread(Service service){
            this.service = service;
        }

        @Override
        public void run() {
            while (true){
                try {
                    service.doSomeThing();
                    sleep(100000000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException("test");
                }
            }
        }
    }


    static void showFreeMemory(String desc){

        System.out.println(desc + " : " + Runtime.getRuntime().freeMemory()/1024/1024+"M");
    }
}
