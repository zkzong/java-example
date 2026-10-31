package com.imooc.kafka.producer;

import com.imooc.kafka.common.MessageEntity;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class SimpleProducer {

    @Autowired
    @Qualifier("kafkaTemplate")
    private KafkaTemplate<String, MessageEntity> kafkaTemplate;

    public void send(String topic, MessageEntity message) {
        kafkaTemplate.send(topic, message);
    }

    public void send(String topic, String key, MessageEntity entity) {
        ProducerRecord<String, MessageEntity> record = new ProducerRecord<>(
                topic,
                key,
                entity);

        long startTime = System.currentTimeMillis();

        CompletableFuture<SendResult<String, MessageEntity>> future = kafkaTemplate.send(record);
        // 执行成功回调
        future.thenAccept(result -> {
            long elapsedTime = System.currentTimeMillis() - startTime;
            System.out.println("Sent message: (" + entity + ") to partition: " + result.getRecordMetadata().partition() + " with offset: " + result.getRecordMetadata().offset() + " in " + elapsedTime + " ms");
        });
        // 执行失败回调
        future.exceptionally(ex -> {
            System.out.println("Unable to send message: " + entity + " due to : " + ex.getMessage());
            return null;
        });
    }

}