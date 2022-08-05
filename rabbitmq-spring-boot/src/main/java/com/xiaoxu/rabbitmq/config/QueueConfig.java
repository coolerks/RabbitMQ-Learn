package com.xiaoxu.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {
    public static final String NORMAL_EXCHANGE = "普通交换机", NORMAL_QUEUE1 = "队列1", NORMAL_QUEUE2 = "队列2";
    public static final String DEAD_EXCHANGE = "死信交换机", DEAD_QUEUE = "死信队列";

    @Bean
    public DirectExchange normalExchange() {
        return new DirectExchange(NORMAL_EXCHANGE, false, false);
    }

    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange(DEAD_EXCHANGE, false, false);
    }

    @Bean
    public Queue normalQueue1() {
        return QueueBuilder
                .nonDurable(NORMAL_QUEUE1)
                .ttl(20000)
                .deadLetterExchange(DEAD_EXCHANGE)
                .deadLetterRoutingKey("dead")
                .build();
    }

    @Bean
    public Queue normalQueue2() {
        return QueueBuilder
                .nonDurable(NORMAL_QUEUE2)
                .ttl(10000)
                .deadLetterExchange(DEAD_EXCHANGE)
                .deadLetterRoutingKey("dead")
                .build();
    }

    @Bean
    public Queue deadQueue() {
        return QueueBuilder
                .nonDurable(DEAD_QUEUE)
                .build();
    }

    @Bean
    public Binding normalQueue1Binding(@Qualifier("normalQueue1") Queue queue, @Qualifier("normalExchange") DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("key1");
    }

    @Bean
    public Binding normalQueue2Binding(@Qualifier("normalQueue2") Queue queue, @Qualifier("normalExchange") DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("key2");
    }
    @Bean
    public Binding deadQueueBinding(@Qualifier("deadQueue") Queue queue, @Qualifier("deadExchange") DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("dead");
    }
}
