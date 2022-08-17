package com.xiaoxu.rabbitmq.service;

import com.xiaoxu.rabbitmq.config.DelayedConfig;
import com.xiaoxu.rabbitmq.config.QueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class SendService {
    private final RabbitTemplate rabbitTemplate;

    public void sendToQueue1(String message) {
        rabbitTemplate.convertAndSend(QueueConfig.NORMAL_EXCHANGE, "key1", message);
        log.info("发送到队列1的消息：{}", message);
    }

    public void sendToQueue2(String message) {
        rabbitTemplate.convertAndSend(QueueConfig.NORMAL_EXCHANGE, "key2", message);
        log.info("发送到队列2的消息：{}，现在的时间：{}", message, new Date().toString());
    }

    public SendService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToQueue3(String message, Integer time) {
        log.info("发送到队列3的消息：{}，现在的时间：{}，延迟：{}", message, new Date().toString(), time);
        rabbitTemplate.convertAndSend(QueueConfig.NORMAL_EXCHANGE, "key5", message, msg -> {
            msg.getMessageProperties().setExpiration(String.valueOf(time));
            return msg;
        });
    }

    public void sendToDelayedQueue(String message, Integer time) {
        log.info("消息内容为：{}，发到延迟队列的延迟为：{}", message, time);
        rabbitTemplate.convertAndSend(DelayedConfig.DELAYED_EXCHANGE, DelayedConfig.ROUTING_KEY, message, msg -> {
            msg.getMessageProperties().setDelay(time);
            return msg;
        });
    }

    public void sendToOne(String message) {
        log.info("生产者发到交换机的消息：{}", message);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        correlationData.setReturned(new ReturnedMessage(new Message(message.getBytes()), 404, "",
                "一个交换机", "一个key"));
        rabbitTemplate.convertAndSend("一个交换机", "一个key", message, correlationData);
    }
}
