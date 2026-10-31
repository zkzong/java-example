package com.example.kafka.controller;

import com.example.kafka.controller.auto.AutoProducer;
import com.example.kafka.controller.manual.ManualProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

    @Autowired
    private AutoProducer autoProducer;
    @Autowired
    private ManualProducer manualProducer;


    @PostMapping(value = "/auto/send")
    public void autoSend(@RequestParam("message") String message) {
        autoProducer.sendMessage(message);
    }

    @PostMapping(value = "/manual/send")
    public void manualSend(@RequestParam("message") String message) {
        manualProducer.sendMessage(message);
    }

}
