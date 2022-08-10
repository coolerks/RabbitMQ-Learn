package com.xiaoxu.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class DelayedConfig {
    public static final String DELAYED_EXCHANGE = "延迟交换机", DELAYED_QUEUE = "延迟队列";
    public static final String ROUTING_KEY = "KEYS";

    @Bean
    public CustomExchange delayExchange() {
        HashMap<String, Object> arguments = new HashMap<>();
        // 设置路由类型
        arguments.put("x-delayed-type", "direct");
        return new CustomExchange(DELAYED_EXCHANGE, "x-delayed-message", false, false, arguments);
    }

    @Bean
    public Queue delayQueue() {
        return QueueBuilder
                .nonDurable(DELAYED_QUEUE)
                .build();
    }

    @Bean
    public Binding delayQueueBinding(@Qualifier("delayExchange") CustomExchange exchange, @Qualifier("delayQueue") Queue queue) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY)
                .noargs();
    }
}
