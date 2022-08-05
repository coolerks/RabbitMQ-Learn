package workqueue;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main2 {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        DeliverCallback deliverCallback = (tag, msg) -> {
            System.out.println("第二个程序接收到消息：" + new String(msg.getBody()));
        };

        CancelCallback cancelCallback = (tag) -> {
            System.out.println("取消接收");
        };
        System.out.println("第二个程序等待接收：");
        channel.basicConsume("队列名称", true, deliverCallback, cancelCallback);
    }
}
