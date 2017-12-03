package core.base.producerandconsumer.b;

import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.annotation.ThreadSafe;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by yangqifan on 2017/12/3.
 */
@ThreadSafe
public class ProducerAndConsumer {

    /**
     * produce和consume可以并发访问messages,同步方法保证线程安全,同一时刻只有
     * 一个线程可以生产或者消费.所以吞吐量低
     */
    private List<String> messages = new LinkedList<>();

    public synchronized boolean produce(String message){
        if(messages.size() > 100)return false;
        return messages.add(message);
    }

    public synchronized String consume(){
        if(messages.size() == 0)return null;
        return messages.remove(0);// LinkedList should use removeFirst method
    }

    private void sleep(int seconds){
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void startProducer(){
        new Producer("dog").start();
    }

    public void startConsumer(){
        new Consumer("elephant").start();
    }

    public class Producer extends Thread{

        private String producerName;
        private int count;

        public Producer(String producerName){
            this.producerName = producerName;
        }

        @Override
        public void run() {
            while (true){
                boolean result = produce(producerName + "-" + count++);

                if(result){
                    ProducerAndConsumer.this.sleep(1);
                }else {
                    System.out.println("wait for consuming");
                    ProducerAndConsumer.this.sleep(5);
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
                String message = consume();
                if(null == message){
                    System.out.println("wait for producing");
                    ProducerAndConsumer.this.sleep(2);

                }else {
                    System.out.println(message);
                }
            }
        }
    }



}
