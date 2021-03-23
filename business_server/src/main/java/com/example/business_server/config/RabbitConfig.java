package com.example.business_server.config;

import com.example.business_server.common.MsgQueueConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfig {
    @Bean
    public Queue emailQueue(){
        return new Queue(MsgQueueConstant.EMAIL_QUEUE);
    }
}
