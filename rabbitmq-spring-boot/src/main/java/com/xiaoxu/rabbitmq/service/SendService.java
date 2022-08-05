package com.xiaoxu.rabbitmq.service;

import com.xiaoxu.rabbitmq.config.QueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

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
}
