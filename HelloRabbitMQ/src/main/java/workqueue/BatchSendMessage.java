package workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

public class BatchSendMessage {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.confirmSelect();
        // 记录所发送的消息
        Map<Long, String> record = new ConcurrentSkipListMap<>();
        long before = System.currentTimeMillis();
        channel.queueDelete("持久化测试3");
        channel.queueDeclare("持久化测试3", false, false, true, null);
        ConfirmCallback ackCallback = (deliveryTag, multiple) -> {
            // 发送成功的回调
            record.remove(deliveryTag);
        };
        ConfirmCallback nackCallback = (deliveryTag, multiple) -> {
            // 发送失败的回调
            System.out.println(deliveryTag + "发送失败");
        };
        channel.addConfirmListener(ackCallback, nackCallback);
        for (int i = 0; i < 100000; i++) {
            // 获取下一次发送的序号
            record.put(channel.getNextPublishSeqNo(), ("0.0-消息" + i));
            channel.basicPublish("", "持久化测试3", null, ("0.0-消息" + i).getBytes());
        }
        System.out.println("发送时间：" + (System.currentTimeMillis() - before));
    }
}
