package core.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yangqifan on 04/11/2017.
 */
public class ConditonObjectTest {
    public static void main(String[] args) throws InterruptedException {
        String s = null;
        String b = ""+s;
        System.out.println(b.equals("null"));

        ReentrantLock lock = new ReentrantLock();
        /**
         * 假如没获取到锁,那么入等待队列,并使用LockSurpport.park挂起当前线程
         * 当在当前lock上调用unlock(),唤醒head的next线程,被唤醒的线程
         * 会检查preNode是否等于head,如果等于执行tryAccquire()
         * for (;;) {
         final Node p = node.predecessor();
         if (p == head && tryAcquire(arg)) {
         setHead(node);
         p.next = null; // help GC
         failed = false;
         return interrupted;
         }
         if (shouldParkAfterFailedAcquire(p, node) &&
         parkAndCheckInterrupt())
         interrupted = true;
         }

         所以会有以上的代码逻辑,如果中间某个线程被唤醒,还是会找head的下一个node


         获取锁公平策略和非公平策略区别就是是否直接检查AQS state,和等待队列

         公平策略不仅检查state,还会检查等待队列,而非公平只检查state,所以公平
         策略开销更大


         */
        lock.lock();
        try{
            Condition condition = lock.newCondition();
//            condition.await();
            condition.signal();


        }finally {
            lock.unlock();
        }

    }
}
