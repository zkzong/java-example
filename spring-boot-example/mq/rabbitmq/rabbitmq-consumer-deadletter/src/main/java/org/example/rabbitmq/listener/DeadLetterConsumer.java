package org.example.rabbitmq.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RabbitListener(queues = "dead-letter-queue")
public class DeadLetterConsumer {
    /**
     * 指定消费的队列
     */
    @RabbitHandler
    public void consume(String msg, Message message, Channel channel) throws IOException {
        log.info("死信队列收到消息: {}, deliveryTag = {}", msg, message.getMessageProperties().getDeliveryTag());
        System.out.println("死信队列收到了消息:" + msg);
        System.out.println("向手动处理数据表添加数据完成！");
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
