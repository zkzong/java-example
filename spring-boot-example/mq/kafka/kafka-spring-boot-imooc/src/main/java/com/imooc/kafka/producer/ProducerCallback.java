package com.imooc.kafka.producer;

import com.google.gson.Gson;
import com.imooc.kafka.common.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.Nullable;

import java.util.function.BiConsumer;

@Slf4j
public class ProducerCallback implements BiConsumer<SendResult<String, MessageEntity>, Throwable> {

    private final long startTime;
    private final String key;
    private final MessageEntity message;

    private final Gson gson = new Gson();

    public ProducerCallback(long startTime, String key, MessageEntity message) {
        this.startTime = startTime;
        this.key = key;
        this.message = message;
    }


    @Override
    public void accept(@Nullable SendResult<String, MessageEntity> result, Throwable ex) {
        if (ex != null) {
            onFailure(ex);
        } else {
            onSuccess(result);
        }
    }

    public void onSuccess(@Nullable SendResult<String, MessageEntity> result) {
        if (result == null) {
            return;
        }
        long elapsedTime = System.currentTimeMillis() - startTime;

        RecordMetadata metadata = result.getRecordMetadata();
        if (metadata != null) {
            StringBuilder record = new StringBuilder();
            record.append("message(")
                    .append("key = ").append(key).append(",")
                    .append("message = ").append(gson.toJson(message)).append(")")
                    .append("sent to partition(").append(metadata.partition()).append(")")
                    .append("with offset(").append(metadata.offset()).append(")")
                    .append("in ").append(elapsedTime).append(" ms");
            log.info(record.toString());
        }
    }

    public void onFailure(Throwable ex) {
        ex.printStackTrace();
    }
}

