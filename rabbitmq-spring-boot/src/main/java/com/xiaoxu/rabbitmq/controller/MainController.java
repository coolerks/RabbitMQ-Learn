package com.xiaoxu.rabbitmq.controller;

import com.xiaoxu.rabbitmq.service.SendService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send")
public class MainController {
    SendService service;

    @GetMapping("/queue1/{message}")
    public void sendMessageToQueue1(@PathVariable String message) {
        service.sendToQueue1(message);
    }

    @GetMapping("/queue2/{message}")
    public void sendMessagetoQueue2(@PathVariable String message) {
        service.sendToQueue2(message);
    }

    public MainController(SendService service) {
        this.service = service;
    }
}
