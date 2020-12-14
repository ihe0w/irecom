package com.example.business_server.producer;

import com.example.business_server.model.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;

@Component
@Slf4j
public class RabbitProducer {
    private final AmqpTemplate template;

    public RabbitProducer(AmqpTemplate template) {
        this.template = template;
    }

    public void sendRegisterQueue(User user){
        try {
            log.info("Rabbit invoked");
            Message message= MessageBuilder.withBody(getBytesFromObject(user)).setContentType(MessageProperties.CONTENT_TYPE_BYTES).setContentEncoding("UTF-8").setMessageId(UUID.randomUUID()+"").build();;
            log.info("message bytes "+ Arrays.toString(message.getBody()));
            this.template.convertAndSend("emailQueue",message);
        } catch (IOException e) {
            log.error("convert object to byte array wrong");
            e.printStackTrace();
        }
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
}
