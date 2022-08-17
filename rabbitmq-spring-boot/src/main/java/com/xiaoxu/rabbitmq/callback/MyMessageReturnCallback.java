package com.xiaoxu.rabbitmq.callback;

import com.sun.source.tree.ReturnTree;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyMessageReturnCallback implements RabbitTemplate.ReturnsCallback {
    private RabbitTemplate template;

    public MyMessageReturnCallback(RabbitTemplate template) {
        this.template = template;
        template.setReturnsCallback(this);
    }

    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.error("回退消息，回退的内容为：{}，所在交换机：{}，routingKey：{}，reply：{}", new String(returned.getMessage().getBody()),
                returned.getExchange(), returned.getRoutingKey(), returned.getReplyText());
    }
}
