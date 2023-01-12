package com.example.hours.config;

import com.example.hours.common.constant.RabbitMQConstant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq 配置类
 */
@Configuration
public class RabbitMQConfig {

    /**
     * 负责邮件消息分发
     * @return email 交换机
     */
    @Bean("emailExchange")
    public Exchange emailExchange() {
        return ExchangeBuilder.topicExchange(RabbitMQConstant.TOPIC_EMAIL_EXCHANGE).durable(true).build();
    }

    /**
     * 负责存储注册信息邮件
     * @return 注册信息队列
     */
    @Bean("registerQueue")
    public Queue registerQueue() {
        return QueueBuilder.durable(RabbitMQConstant.EMAIL_REGISTER_QUEUE).build();
    }

    /**
     * 绑定交换机与队列
     * @param queue 注册邮件队列
     * @param exchange 邮件消息交换机
     * @return 绑定关系
     */
    @Bean
    public Binding bindEmail(@Qualifier("registerQueue") Queue queue, @Qualifier("emailExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RabbitMQConstant.EMAIL_REGISTER_BINDING_KEY).noargs();
    }

    /**
     * 使用 json 转换 message
     * @return Jackson2JsonMessageConverter
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
