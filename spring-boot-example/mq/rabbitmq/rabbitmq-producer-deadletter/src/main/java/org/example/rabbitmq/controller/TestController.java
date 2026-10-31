package org.example.rabbitmq.controller;

import org.example.rabbitmq.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/send")
    public void send(@RequestParam String msg) {
        rabbitTemplate.convertAndSend(RabbitConfig.BUSINESS_EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, msg);
    }

}

