import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class Cc {

    public static void main1(String[] args) throws InterruptedException {

        Semaphore semaphore = new Semaphore(1, true);
        new Thread(() -> {
            while (true) {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("A");
                semaphore.release();
            }
        }).start();
        new Thread(() -> {
            while (true) {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("B");
                semaphore.release();
            }
        }).start();

        Thread.sleep(222222222L);

    }

    public static void main2(String[] args) throws InterruptedException {
        Object lock = new Object();
        AtomicBoolean condition = new AtomicBoolean(true);
        //true t2运行, t1挂起,  false t1运行, 交替执行
        new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    //true t2运行,
                    while (condition.get()) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("A");
                    condition.set(true);
                    lock.notifyAll();
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (!condition.get()) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("B");
                    condition.set(false);
                    lock.notifyAll();
                }
            }
        }).start();

        Thread.sleep(222222222L);
    }

    public static void main(String[] args) {
        Node node1 = new Node();
        node1.value = 1;
        Node node2 = new Node();
        node2.value = 2;
        Node node3 = new Node();
        node3.value = 3;
        Node node4 = new Node();
        node4.value = 4;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;

        Node x = node1;
        while (x != null){
            System.out.println(x.value);
            x = x.next;
        }

        //null->A->B->C
        //null<-A <-B<-C
        //双指针
        Node last = null;
        Node cur = node1;
        while (cur != null) {
            Node tmp = cur.next;//只有链表当前节点next
            cur.next = last;
            last = cur;
            cur = tmp;
        }


        while (last != null){
            System.out.println(last.value);
            last = last.next;
        }

    }

    static class Node {

        Node next;
        int value;
    }

}
