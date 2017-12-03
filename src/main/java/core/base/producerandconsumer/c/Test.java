package core.base.producerandconsumer.c;

/**
 * Created by yangqifan on 2017/12/3.
 */
public class Test {
    public static void main(String[] args) {
        ProducerAndConsumer pac = new ProducerAndConsumer();
        pac.startProducer();
        pac.startProducer();
        pac.startProducer();
//        pac.startConsumer();
        pac.startConsumer();
    }
}
