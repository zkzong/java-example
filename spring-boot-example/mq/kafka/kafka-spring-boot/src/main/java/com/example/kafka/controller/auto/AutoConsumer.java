package com.example.kafka.controller.auto;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AutoConsumer {

    /**
     * 使用 message 接受消息
     */
    @KafkaListener(topics = "auto-topic", groupId = "group_id_1")
    public void consume(String message) {
        log.info(String.format("group_id_1 -> $$ -> Consumed Message -> %s", message));
    }

    /**
     * 使用 record 接受消息
     */
    @KafkaListener(topics = "auto-topic", groupId = "group_id_2")
    public void consume(ConsumerRecord<String, String> record) {
        log.info(String.format("group_id_2 -> $$ -> Consumed Message -> %s", record));
    }

    /**
     * 使用 Payload 接受消息
     */
    @KafkaListener(topics = "auto-topic", groupId = "group_id_3")
    public void consumePayload(@Payload String payload) {
        log.info(String.format("group_id_3 -> $$ -> Consumed Message -> %s", payload));
    }

}
