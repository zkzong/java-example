package org.example.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitConfig {

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);

        //设置消息投递失败的策略，有两种策略：自动删除或返回到客户端。
        //我们既然要做可靠性，当然是设置为返回到客户端(true是返回客户端，false是自动删除)
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack) {
                    log.info("ConfirmCallback 关联数据：{},投递成功,确认情况：{}", correlationData, ack);
                } else {
                    log.info("ConfirmCallback 关联数据：{},投递失败,确认情况：{}，原因：{}", correlationData, ack, cause);
                }
            }
        });

        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                // 请注意!如果你使用了延迟队列插件，那么一定会调用该callback方法，因为数据并没有提交上去，
                // 而是提交在交换器中，过期时间到了才提交上去，并非是bug！你可以用if进行判断交换机名称来捕捉该报错
                /*if (exchange.equals("你声明的延迟队列的交换机")) {
                    return;
                }*/
                log.info("ReturnsCallback 消息被退回：{},回应码：{},回应信息：{},交换机：{},路由键：{}"
                        , returnedMessage.getMessage(), returnedMessage.getReplyCode()
                        , returnedMessage.getReplyText(), returnedMessage.getExchange()
                        , returnedMessage.getRoutingKey());
            }
        });

        return rabbitTemplate;
    }
}
