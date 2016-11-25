package core.thread;

/**
 * @author yangqf
 * @version 1.0 2016/11/24
 */
public class NotifyAllTest{

    public static void main(String[] args) throws InterruptedException{
        Object lock = new Object();
        for(int i = 0; i < 3; i++){
            new Thread("i am thread "+ i){
                @Override
                public void run(){
                    synchronized(lock){
                        while(true){
                            try{
                                System.out.println(getName()+" i  wakeup");
                                lock.wait();
                            }catch(InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }.start();
        }

        Thread.sleep(5000);
        System.out.println("--------------------");
        synchronized(lock){
            //这会唤醒所有在lock上wait的线程，然后都去竞争锁，没竞争到锁会被挂起，然后等待持有锁的线程
            //释放锁，这里类似synchronized挂起和自动再次获取锁
            lock.notifyAll();
        }
        Thread.sleep(5000);
        System.out.println("--------------------");
        synchronized(lock){
            //这里只会唤醒某一个在lock上wait的线程
            lock.notify();
        }
    }

}
