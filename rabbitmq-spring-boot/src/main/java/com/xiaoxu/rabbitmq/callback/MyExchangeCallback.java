package com.xiaoxu.rabbitmq.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyExchangeCallback implements RabbitTemplate.ConfirmCallback {
    private RabbitTemplate template;

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("交换机收到消息...，尝试查看消息的内容：{}", correlationData);
        } else {
            ReturnedMessage returned = correlationData.getReturned();
            log.info("交换机没有收到消息, 原因：{}，消息内容：{}，发往的交换机：{}，routingKey：{}", cause, new String(returned.getMessage().getBody()),
                    returned.getExchange(), returned.getRoutingKey());
        }
    }

    public MyExchangeCallback(RabbitTemplate template) {
        this.template = template;
//        template.setConfirmCallback(this);
    }
}
