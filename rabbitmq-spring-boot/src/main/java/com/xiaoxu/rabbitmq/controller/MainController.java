package com.xiaoxu.rabbitmq.controller;

import com.xiaoxu.rabbitmq.service.SendService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;

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

    @GetMapping("/queue/{message}/{time}")
    public void sendMessagetoQueue3(@PathVariable String message, @PathVariable Integer time) {
        service.sendToQueue3(message, time);
    }

    @GetMapping("/delayed/{message}/{time}")
    public void sendMessagetoDelayedQueue(@PathVariable String message, @PathVariable Integer time) {
        service.sendToDelayedQueue(message, time);
    }

    @GetMapping("/one/{message}")
    public void sendToOne(@PathVariable String message) {
        service.sendToOne(message);
    }

    public MainController(SendService service) {
        this.service = service;
    }
}
