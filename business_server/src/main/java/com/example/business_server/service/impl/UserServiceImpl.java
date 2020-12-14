package com.example.business_server.service.impl;

import com.example.business_server.dao.UserMapper;
import com.example.business_server.model.domain.User;
import com.example.business_server.model.dto.ResponseResult;
import com.example.business_server.producer.RabbitProducer;
import com.example.business_server.service.MailService;
import com.example.business_server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RabbitListener(queues = "emailQueue")
public class UserServiceImpl implements UserService {
    final
    MailService mailService;
    final
    RabbitProducer rabbitProducer;
    final
    UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper, RabbitProducer rabbitProducer, MailService mailService) {
        this.userMapper = userMapper;
        this.rabbitProducer = rabbitProducer;
        this.mailService = mailService;
        log.info("mailService "+mailService);
        log.info("this in constrc "+this);
    }

    @Override
    public ResponseResult<String> loginIn(String account, String password) {
        return ResponseResult.success("login success");
    }

    /**
     * @param account 用户账号 暂时只能邮箱注册*/
    private boolean isAccountValid(String account) {
        // 检查邮件格式是否正确
        if (!checkEmailFormat(account)) {
            return false;
        }
        // 检查账号是否在
        if (checkAccountExists(account)){
            return false;
        }
        return true;
    }
    private boolean checkEmailFormat(String email){
        String REGEX="^\\w+((-\\w+)|(\\.\\w+))*@\\w+(\\.\\w{2,3}){1,3}$";
        Pattern p = Pattern.compile(REGEX);
        Matcher matcher=p.matcher(email);
        return matcher.matches();
    }
    private boolean checkAccountExists(String account){
        return userMapper.checkAccountExists(account);
    }

    @Override
    public Boolean register(String account, String password) {
        if (isAccountValid(account)){
            User user=new User();
            user.setUserName(account);
            user.setAccount(account);
            user.setEmail(account);
            user.setPassword(password);
            userMapper.insertUser(user);
            log.info(String.valueOf(mailService));
            rabbitProducer.sendRegisterQueue(user);
            return true;
        }
        return false;

    }



    @RabbitHandler
    public void sendRegisterSuccessEmail(Message message){
        byte[] userBytes=message.getBody();
        log.info("user bytes "+ Arrays.toString(userBytes));

        User user;
        try {
            user = (User) getObjectFromBytes(userBytes);
            log.info("user email "+user.getEmail());
            log.info("in handler "+ mailService);
            log.info("in handler um"+ userMapper);
            log.info("in handler rp"+ rabbitProducer);
            log.info("this "+this);
            mailService.sendSimpleMail(user.getEmail(),"注册成功","恭喜你——"+user.getUserName()+"成功注册本网站");
        } catch (IOException e) {
            log.error("convert bytes to object wrong");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private Object getObjectFromBytes(byte[] objBytes) throws IOException, ClassNotFoundException {
        if (objBytes == null || objBytes.length == 0) {
            return null;
        }
        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
        ObjectInputStream oi = new ObjectInputStream(bi);
        return oi.readObject();
    }


}
