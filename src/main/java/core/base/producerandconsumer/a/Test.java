package core.base.producerandconsumer.a;

/**
 * Created by yangqifan on 2017/12/3.
 */
public class Test {
    public static void main(String[] args) {
        ProducerAndConsumer pac = new ProducerAndConsumer();
        pac.startProducer();
        pac.startConsumer();
    }
}
