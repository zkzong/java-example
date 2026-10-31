package com.example.kafka.controller.manual;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ManualConsumer {

    /**
     * 使用 message 接受消息
     */
    @KafkaListener(topics = "manual-topic", groupId = "group_id_1")
    public void consume1(String message, Acknowledgment ack) {
        log.info(String.format("group_id_1 -> $$ -> Consumed Message -> %s", message));
        ack.acknowledge();
    }

    /**
     * 使用 record 接受消息
     */
    @KafkaListener(topics = "manual-topic", groupId = "group_id_2")
    public void consume2(ConsumerRecord<String, String> record, Acknowledgment ack) {
        log.info(String.format("group_id_2 -> $$ -> Consumed Message -> %s", record));
        ack.acknowledge();
    }

}
