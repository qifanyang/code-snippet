package core.thread;

/**
 * @author yangqf
 * @version 1.0 2016/11/24
 */
public class SynchronizedVolatileReadTest{
    private volatile int x = 0;

    private synchronized void incrX(){
        ++x;
    }

    public static void main(String[] args) throws InterruptedException{
        SynchronizedVolatileReadTest synchronizedVolatileReadTest = new SynchronizedVolatileReadTest();
        int y = 20;
        Thread[] threads = new Thread[y];
        for(int i = 0; i < y; i++){
            threads[i] = new Thread(){
                @Override
                public void run(){
                    int z = 0;
                    while(z++ < 10000){
                        int x = synchronizedVolatileReadTest.x;
                        x++;
                        synchronizedVolatileReadTest.x = x;
                    }
                }
            };
        }
        for(int i = 0; i < y; i++){
            threads[i].start();
        }
        for(int i = 0; i < y; i++){
            threads[i].join();
        }
        System.out.println(synchronizedVolatileReadTest.x);
    }


}
