package dead;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeoutException;

// 作为消费者1（普通的队列）
public class Consumer1 {
    public static final String NORMAL_EXCHANGE = "普通交换机", NORMAL_QUEUE = "普通队列";
    public static final String DEAD_EXCHANGE = "死信交换机", DEAD_QUEUE = "死信队列";
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);

        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "dead");

        HashMap<String, Object> arguments = new HashMap<>();
//        // 所有消息的超时时间
//        arguments.put("x-message-ttl", 10000);
        // 设置死信交换机
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        // 设置死信routingKey
        arguments.put("x-dead-routing-key", "dead");
        // 指定队列的最大长度
        arguments.put("x-max-length", 5);
        channel.queueDeclare(NORMAL_QUEUE, false, false, false, arguments);
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "normal");
        Random random = new Random();

        channel.basicConsume(NORMAL_QUEUE, true, (consumerTag, message) -> {
            if (random.nextInt(100) % 2 == 0) {
                System.out.println("模拟拒绝消息....");
                channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
                return;
            }
            System.out.println("常规队列接收消息：" + new String(message.getBody()));
//            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        }, consumerTag -> {});
    }
}
