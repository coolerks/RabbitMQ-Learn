package dead;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

// 生产者
public class Product {
    public static final String NORMAL_EXCHANGE = "普通交换机", NORMAL_QUEUE = "普通队列";
    public static final String DEAD_EXCHANGE = "死信交换机", DEAD_QUEUE = "死信队列";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        // 设置这条消息超时时间
        AMQP.BasicProperties props = new AMQP.BasicProperties()
                .builder()
                .expiration("10000")
                .build();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            channel.basicPublish(NORMAL_EXCHANGE, "normal", props, scanner.nextLine().getBytes());
        }
    }
}
