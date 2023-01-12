package com.example.hours.common.constant;

/**
 * rabbitmq 常量
 */
public class RabbitMQConstant {

    //- - - - - - - - - - - - - - - - - - - - -  绑定路由常量 - - - - - - - - - - - - - - - - - - - -

    /**
     * topic 模式下邮件消息交换机与注册邮件队列的绑定 routingKey
     */
    public static final String EMAIL_REGISTER_BINDING_KEY = "email.register.#";

    //- - - - - - - - - - - - - - - - - - - - -  分发路由常量 - - - - - - - - - - - - - - - - - - - -

    /**
     * topic 模式下发送注册邮件的 routingKey
     */
    public static final String EMAIL_REGISTER_ROUTING_KEY = "email.register";

    //- - - - - - - - - - - - - - - - - - - - -  交换机常量 - - - - - - - - - - - - - - - - - - - -

    /**
     * topic 模式下邮件消息交换机
     */
    public static final String TOPIC_EMAIL_EXCHANGE = "topic_email_exchange";

    //- - - - - - - - - - - - - - - - - - - - -  队列常量 - - - - - - - - - - - - - - - - - - - -

    /**
     * 注册邮件队列
     */
    public static final String EMAIL_REGISTER_QUEUE = "email_register_queue";
}
