package com.example.business_server.producer;

import com.example.business_server.model.domain.User;
import com.example.business_server.common.MsgQueueConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;

@Component
@Slf4j
public class RabbitProducer implements RabbitTemplate.ConfirmCallback ,RabbitTemplate.ReturnCallback{

    private final RabbitTemplate rabbitTemplate;

    public RabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendRegisterQueue(User user){

        log.info("Rabbit invoked");
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);

        String uuid= String.valueOf(UUID.randomUUID());
        CorrelationData correlationData=new CorrelationData(uuid);

        MessageConverter messageConverter=rabbitTemplate.getMessageConverter();
        MessageProperties properties=new MessageProperties();

        Message message= messageConverter.toMessage(user,properties);
        log.info("message bytes "+ Arrays.toString(message.getBody()));

        this.rabbitTemplate.send(MsgQueueConstant.EMAIL_QUEUE,message);

    }

    private byte[] getBytesFromObject(Serializable obj) throws IOException {
        if (obj==null){
            return null;
        }
        ByteArrayOutputStream bo=new ByteArrayOutputStream();
        ObjectOutputStream oo=new ObjectOutputStream(bo);
        oo.writeObject(obj);
        return bo.toByteArray();
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//        String dataId=correlationData.getId();
//        // todo 12.28 correlationData null, ack true,but not send email to 190
//        // Exception delivering confirm
//        if (ack){
//            log.info("send message"+dataId);
//        }
//        else {
//            log.error("message send error "+dataId+" "+cause);
//        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error("publish msg from "+exchange+"to queue "+routingKey+" replyText: "+replyText+" replyCode: "+replyCode);
    }
}
