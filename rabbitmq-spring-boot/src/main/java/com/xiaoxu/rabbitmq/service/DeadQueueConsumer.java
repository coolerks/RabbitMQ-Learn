package com.xiaoxu.rabbitmq.service;


import com.xiaoxu.rabbitmq.config.DelayedConfig;
import com.xiaoxu.rabbitmq.config.QueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class DeadQueueConsumer {

    @RabbitListener(queues = QueueConfig.DEAD_QUEUE)
    public void receive(Message message) {
        log.info("接收的消息内容：{}，现在的时间：{}", new String(message.getBody()), new Date().toString());
    }

    @RabbitListener(queues = DelayedConfig.DELAYED_QUEUE)
    public void receiveDelayedQueue(Message message) {
        log.info("延迟队列接收到消息，消息的内容为：{}", new String(message.getBody()));
    }
}
