package org.example.rabbitmq.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@RabbitListener(queues = "TestDirectQueue")
@Component
@Slf4j
public class DirectConsumer {

    /*@RabbitHandler
    public void process(Object data, Channel channel, Message message) throws IOException {
        log.info("消费者接受到的消息是：{},消息体为：{}", data, message);
        //由于配置设置了手动应答，所以这里要进行一个手动应答。注意：如果设置了自动应答，这里又进行手动应答，会出现double ack，那么程序会报错。
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }*/

    @RabbitHandler
    public void process(Object message, Channel channel, @Headers Map<String, Object> map) {
        System.out.println(message);
        //这里只是模拟业务，其实还有很多可能，比如验证用户的银行卡号已经过期等等，都可以发出nack
        if (map.get("error") != null) {
            System.out.println("错误的消息");
            try {
                //否认消息
                channel.basicNack((Long) map.get(AmqpHeaders.DELIVERY_TAG), false, true);
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            System.out.println("业务在这里执行！");
            //确认消息
            channel.basicAck((Long) map.get(AmqpHeaders.DELIVERY_TAG), false);
        } catch (IOException e) {
            //nack
            e.printStackTrace();

        }
    }

}
