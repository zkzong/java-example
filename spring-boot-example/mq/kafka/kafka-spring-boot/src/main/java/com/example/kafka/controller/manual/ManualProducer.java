package com.example.kafka.controller.manual;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class ManualProducer {

    private static final String TOPIC = "manual-topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        log.info(String.format("$$ -> Producing message --> %s", message));
        // 自动分区
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(TOPIC, message);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Failed to send message", ex);
            } else {
                log.info("Message sent: " + result.getRecordMetadata().offset());
            }
        });
        // 指定分区
        //kafkaTemplate.send(TOPIC, "1", message);
    }
}
