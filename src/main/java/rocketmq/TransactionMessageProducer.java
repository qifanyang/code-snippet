package rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class TransactionMessageProducer {

    public static final String PRODUCER_GROUP = "ProducerGroupName";
    public static final String DEFAULT_NAMESRVADDR = "127.0.0.1:9876";
    public static final String TOPIC = "TopicTest";
    public static final String TAG = "TagA";
    public static void main(String[] args) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        TransactionMQProducer producer = new TransactionMQProducer();
        producer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
        producer.setProducerGroup("TRANSACTION_PRODUCER_GROUP");

        producer.setTransactionListener(new TransactionListener(){

            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                //记录本地事务执行结果
                String orderId = (String) arg;

                //记录本地事务执行结果
                boolean success = true;
                //boolean success = persistTransactionResult(orderId);
                //System.err.println("订单服务执行本地事务下单,orderId: " + orderId + ", result: " + success);
                return success ? LocalTransactionState.COMMIT_MESSAGE : LocalTransactionState.ROLLBACK_MESSAGE;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                String orderId = msg.getKeys();
                //查询本地数据库中是否有数据
                boolean success = true;
                return success ? LocalTransactionState.COMMIT_MESSAGE : LocalTransactionState.ROLLBACK_MESSAGE;
            }
        });

        int orderId = 1111;
        TransactionSendResult transactionSendResult = producer.sendMessageInTransaction(new Message(TOPIC, "ddd".getBytes()), orderId);
        //发送成功才表示半消息发送成功
    }

}
