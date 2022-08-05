import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

public class Consumer {

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
            Channel channel = connection.createChannel();
            // 参数1：队列名称，参数2：是否自动应答，参数3：接收消息的回调，参数4：取消接收的回调
            channel.basicConsume("队列名称", true, (tag, msg) -> {
//                System.out.println(msg);
                String s = new String(msg.getBody());
                System.out.println("s = " + s);
            }, tag -> {
                System.out.println("用户取消了操作");
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }

    }
}
