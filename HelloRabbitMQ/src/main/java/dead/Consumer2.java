package dead;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

// 作为消费者1（死信队列）
public class Consumer2 {
    public static final String NORMAL_EXCHANGE = "普通交换机", NORMAL_QUEUE = "普通队列";
    public static final String DEAD_EXCHANGE = "死信交换机", DEAD_QUEUE = "死信队列";
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.basicConsume(NORMAL_QUEUE, (consumerTag, message) -> {
            System.out.println("死信队列接收消息：" + new String(message.getBody()));
        }, consumerTag -> {});
    }
}
