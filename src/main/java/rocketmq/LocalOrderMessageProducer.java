package rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class LocalOrderMessageProducer {

    public static final String PRODUCER_GROUP = "ProducerGroupName";
    public static final String DEFAULT_NAMESRVADDR = "127.0.0.1:9876";
    public static final String TOPIC = "TopicTest";
    public static final String TAG = "TagA";

    public static void main(String[] args) throws MQClientException, InterruptedException {

        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);

        // Uncomment the following line while debugging, namesrvAddr should be set to your local address
        //producer.setNamesrvAddr(DEFAULT_NAMESRVADDR);

        producer.start();
        for (int i = 0; i < 128; i++) {
            try {
                Message msg = new Message(TOPIC, TAG, "OrderID188", "Hello world".getBytes(StandardCharsets.UTF_8));
                //发送消息走固定的queue
                SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        String orderId = (String) arg;
                        int index = orderId.hashCode() % mqs.size();
                        return mqs.get(index);
                    }
                }, 222);
                System.out.printf("%s%n", sendResult);
                System.out.printf("Product：发送状态=%s, 存储queue=%s ,orderid=%s, type=%s\n", sendResult.getSendStatus(),
                        sendResult.getMessageQueue().getQueueId(), 111, "order.getType()");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        producer.shutdown();

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ExampleConsumer");
        // Subscribe topics
        consumer.subscribe("TestTopic", "*");
        // Register message listener
        consumer.registerMessageListener((MessageListenerOrderly) (messages, context) -> {
            for (MessageExt message : messages) {
                // Print approximate delay time period
                System.out.println("Receive message[msgId=" + message.getMsgId() + "] "
                        + (System.currentTimeMillis() - message.getStoreTimestamp()) + "ms later");
            }
            return ConsumeOrderlyStatus.SUCCESS;
        });
        // Launch consumer
        //注册顺序消息见 : ConsumeMessageOrderlyService.lockMQPeriodically()
        consumer.start();

    }
}
