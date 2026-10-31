package org.example.rabbitmq.controller;

import com.alibaba.fastjson2.JSON;
import org.example.rabbitmq.entity.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class TestController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/test")
    public String test() {
        return "producer ok";
    }

    @GetMapping("/push")
    public String push() {
        for (int i = 1; i <= 5; i++) {
            //这个参数是用来做消息的唯一标识
            //发布消息时使用，存储在消息的headers中
            User user = new User(i, "经理赵");
            // 关联的数据，可以用在消息投递失败的时候，作为一个线索，比如我把当前用户的id放进去，如果user消息投递失败
            // 我后面可以根据id再找到user，再次投递数据
            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString().concat("-") + i);
            if (i == 2) {
                //故意把交换机写错，演示 confirmCallback
                rabbitTemplate.convertAndSend("TestDirectExchange_111", "TestDirectRouting",
                        JSON.toJSONString(user), correlationData);
            } else if (i == 3) {
                //故意把路由键写错，演示 returnCallback
                rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting_111",
                        JSON.toJSONString(user), correlationData);
            } else if (i == 5) {
                MessageProperties messageProperties = new MessageProperties();
                Map map = new HashMap();
                map.put("error", "testError");
                messageProperties.setHeaders(map);
                Message message = new Message(JSON.toJSONString(user).getBytes(), messageProperties);
                rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting",
                        message, correlationData);
            } else {
                //正常发送
                rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting",
                        JSON.toJSONString(user), correlationData);
            }
        }
        return "producer push ok";
    }
}

