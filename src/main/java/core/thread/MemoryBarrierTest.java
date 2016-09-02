package core.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @author yangqf
 * @version 1.0 2016/7/5
 */
public class MemoryBarrierTest{

    CountDownLatch cdl = new CountDownLatch(1);

    int a = 0;
    int b = 0;

    private void foo(){
        a = 1;
        b = 1;
    }

    private void bar(){
        while(b == 0)continue;
        if(a == 0){
            System.out.println(a);
        }
    }

    private void test() throws InterruptedException{
        new Thread(){
            @Override
            public void run(){
                System.out.println("thead 1 ready ...");
                try{
                    cdl.await();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                int i = 0;
//                while(i++ < Integer.MAX_VALUE)
                    foo();
                System.out.println("thead 1 run ...");
            }
        }.start();


        new Thread(){
            @Override
            public void run(){
                System.out.println("thead 2 ready ...");
                try{
                    cdl.await();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                int i = 0;
//                while(i++ < Integer.MAX_VALUE)
                    bar();
                System.out.println("thead 2 run ...");
            }
        }.start();

        System.out.println("sleep 3 second ...");
        Thread.sleep(3000);
        cdl.countDown();
        System.out.println("sleep 3 second end ...");
    }



    public static void main(String[] args) throws InterruptedException{
        MemoryBarrierTest memoryBarrierTest = new MemoryBarrierTest();
        memoryBarrierTest.test();
//        Thread.sleep(Integer.MAX_VALUE);
    }
}
