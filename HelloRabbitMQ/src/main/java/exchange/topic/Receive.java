package exchange.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receive {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String queue = channel.queueDeclare().getQueue();
        channel.exchangeDeclare("主题", "topic");
        // # 代表可以收到所有的消息
        channel.queueBind(queue, "主题", "#");
        channel.basicConsume(queue, (consumerTag, message) -> {
            System.out.println("所有的消息：" + new String(message.getBody()));
        }, consumerTag -> {

        });
    }
}
