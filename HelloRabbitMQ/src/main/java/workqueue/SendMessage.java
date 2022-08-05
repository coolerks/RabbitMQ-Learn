package workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SendMessage {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.confirmSelect();
        long before = System.currentTimeMillis();
        channel.queueDelete("持久化测试3");
        for (int i = 0; i < 100000; i++) {
            channel.basicPublish("", "持久化测试3", null, ("0.0-消息" + i).getBytes());
            channel.waitForConfirms();
        }
        System.out.println("发送时间：" + (System.currentTimeMillis() - before));
    }
}
