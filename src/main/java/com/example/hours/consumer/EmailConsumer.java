package com.example.hours.consumer;

import com.alibaba.fastjson.JSON;
import com.example.hours.common.constant.RabbitMQConstant;
import com.example.hours.model.vo.EmailVo;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * 邮箱消息消费者
 */
@Component
public class EmailConsumer {

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * TODO 修改 rabbitmq 的提交方式，并确保邮件能正常发送到用户邮箱
     * 监听注册邮件队列
     * @param message 携带注册消息的 Message 对象
     */
    @RabbitListener(queues = {RabbitMQConstant.EMAIL_REGISTER_QUEUE})
    public void processRegister(Message message) {
        EmailVo emailVo = JSON.parseObject(new String(message.getBody()), EmailVo.class);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmail);
        simpleMailMessage.setTo(emailVo.getEmail());
        System.out.println("to: " + emailVo.getEmail());
        simpleMailMessage.setSubject(emailVo.getSubject());
        simpleMailMessage.setText(emailVo.getContent());
        mailSender.send(simpleMailMessage);
    }
}
