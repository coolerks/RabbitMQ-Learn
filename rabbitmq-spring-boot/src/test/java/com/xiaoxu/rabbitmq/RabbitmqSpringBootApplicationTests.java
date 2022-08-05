package com.xiaoxu.rabbitmq;

import com.xiaoxu.rabbitmq.service.SendService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitmqSpringBootApplicationTests {

    @Autowired
    SendService service;
    @Test
    void contextLoads() {
        service.sendToQueue1("发往queue1的消息，等待40s");
        service.sendToQueue1("发往queue2的消息，等待10s");
    }

}
