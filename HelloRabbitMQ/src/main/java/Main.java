import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

public class Main {

    public static void main(String[] args) throws IOException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Properties properties = new Properties();
        properties.load(ClassLoader.getSystemResourceAsStream("info.properties"));
        connectionFactory.setHost(properties.getProperty("host"));
        connectionFactory.setUsername(properties.getProperty("username"));
        connectionFactory.setPassword(properties.getProperty("password"));
        try (
                Connection connection = connectionFactory.newConnection()
        ) {
            System.out.println("connection = " + connection);
            // 创建一个信道
            Channel channel = connection.createChannel();
            // 创建一个队列，参数含义：
            // 参数1：队列名称
            // 参数2：是否要进行持久化，默认消息是保存在内存中的
            // 参数3：是否要将这个队列中的消息共享（true为这个队列仅限于当前连接使用）
            // 参数4：如果长时间不使用这个队列，是否要进行删除
            // 参数5：其他参数
//            channel.queueDeclare("队列名称", true, false, false, null);
            // 发送消息
            // 参数1：交换机名称
            // 参数2：可以为队列的名称
            // 参数3：其他的参数信息
            // 参数4：消息的内容
            channel.basicPublish("", "队列名称", null, "hello world3".getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
