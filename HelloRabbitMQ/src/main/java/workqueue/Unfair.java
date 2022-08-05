package workqueue;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

// 不公平分发
public class Unfair {
    public static void main(String[] args) throws IOException, TimeoutException {

        new Thread(() -> {
            try {
                Connection connection = ConnectionUtil.getConnection();
                Channel channel = connection.createChannel();
                channel.basicQos(5);
                DeliverCallback deliverCallback = (tag, msg) -> {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("第一个线程接收到消息：" + new String(msg.getBody()));
//                    // 手动确认消息
                    channel.basicAck(msg.getEnvelope().getDeliveryTag(), false);
//                    // 否定拒绝
//                    channel.basicNack(msg.getEnvelope().getDeliveryTag(), false, true);
                    // 否定拒绝确认
//                    channel.basicReject(msg.getEnvelope().getDeliveryTag(), true);
                };

                CancelCallback cancelCallback = (tag) -> {
                    System.out.println("取消接收");
                };
                System.out.println("第一个线程等待接收：");
                channel.basicConsume("队列名称", false, deliverCallback, cancelCallback);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                Connection connection = ConnectionUtil.getConnection();
                Channel channel = connection.createChannel();
                channel.basicQos(2);
                DeliverCallback deliverCallback = (tag, msg) -> {
                    System.out.println("第二个线程接收到消息：" + new String(msg.getBody()));
                    channel.basicAck(msg.getEnvelope().getDeliveryTag(), false);
                };

                CancelCallback cancelCallback = (tag) -> {
                    System.out.println("取消接收");
                };
                System.out.println("第二个线程等待接收：");
                channel.basicConsume("队列名称", false, deliverCallback, cancelCallback);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                Connection connection = ConnectionUtil.getConnection();
                Channel channel = connection.createChannel();
                channel.basicQos(1);
                DeliverCallback deliverCallback = (tag, msg) -> {
                    System.out.println("第三个线程接收到消息：" + new String(msg.getBody()));
                    channel.basicAck(msg.getEnvelope().getDeliveryTag(), false);
                };

                CancelCallback cancelCallback = (tag) -> {
                    System.out.println("取消接收");
                };
                System.out.println("第三个线程等待接收：");
                channel.basicConsume("队列名称", false, deliverCallback, cancelCallback);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
