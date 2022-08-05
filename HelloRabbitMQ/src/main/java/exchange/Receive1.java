package exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

// 临时队列
public class Receive1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("扇出", "fanout");
        String queue2 = channel.queueDeclare().getQueue();
        channel.queueBind(queue2, "扇出", "临时队列1");
        channel.basicConsume(queue2, (consumerTag, message) -> {
            System.out.println("接收队列1的消息：" + new String(message.getBody()));
        }, tag -> {
            System.out.println("取消接收");
        });
    }
}
