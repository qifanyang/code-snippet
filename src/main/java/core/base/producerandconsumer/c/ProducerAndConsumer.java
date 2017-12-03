package core.base.producerandconsumer.c;

import org.apache.http.annotation.ThreadSafe;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yangqifan on 2017/12/3.
 */
@ThreadSafe
public class ProducerAndConsumer {

    /**
     * 使用阻塞队列{@link java.util.concurrent.BlockingQueue}
     * {@link Queue}提供了add和offer方法, 而阻塞队列增加了具备阻塞的put方法和阻塞时间限制的offer方法
     */

    /**
     * 这里不是阻塞队列,使用cas提供更高的并发,允许多个线程同时offer(), poll(),
     * 不会阻塞
     */
    private Queue<String> messagesQueue = new ConcurrentLinkedQueue<>();
    /**
     * put和take使用两把锁,提高并发,使用原子计数保证生产者和消费者可以看见最新元素数量(用来决定挂起or唤醒对方)
     */
    private AtomicInteger count = new AtomicInteger(0);

    private BlockingQueue<String> messages = new LinkedBlockingQueue<>();


    public void produce(String message) throws InterruptedException {
        messages.put(message);
    }

    public String consume() throws InterruptedException {
        return messages.take();
    }



    public void startProducer(){
        new Producer("dog").start();
    }

    public void startConsumer(){
        new Consumer("elephant").start();
    }

    public class Producer extends Thread{

        private String producerName;

        public Producer(String producerName){
            this.producerName = producerName;
        }

        @Override
        public void run() {
            while (true){
                try {
                    produce(producerName + "-" + count.getAndIncrement());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 1.非static内部类无法在其它地方new, 所以宿主类需要对外提供方法暴露内部类,最好暴露接口方便更改实现
     * 2.static内部类没有宿主类的this引用,无法方法私有属性
     */
    public class Consumer extends Thread{

        private String consumerName;

        public Consumer(String consumerName){
            this.consumerName = consumerName;
        }
        @Override
        public void run() {
            while (true){
                String message = null;
                try {
                    message = consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(message);
            }
        }
    }



}
