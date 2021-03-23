package com.example.business_server.service.impl;

import com.example.business_server.dao.UserMapper;
import com.example.business_server.model.dto.FollowDTO;
import com.example.business_server.model.dto.ResultCode;
import com.example.business_server.model.nosql.LoginUser;
import com.example.business_server.utils.ServletUtils;
import com.example.business_server.model.domain.User;
import com.example.business_server.model.dto.LoginType;
import com.example.business_server.model.dto.ResponseResult;
//import com.example.business_server.model.nosql.LoginUser;
import com.example.business_server.producer.RabbitProducer;
import com.example.business_server.service.MailService;
import com.example.business_server.service.UserService;
import com.example.business_server.utils.IpUtils;
import com.example.business_server.common.MsgQueueConstant;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    final
    MailService mailService;
    final
    RabbitProducer rabbitProducer;
    final
    UserMapper userMapper;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder bcryptPasswordEncoder;

    public UserServiceImpl(UserMapper userMapper, RabbitProducer rabbitProducer, MailService mailService) {
        this.userMapper = userMapper;
        this.rabbitProducer = rabbitProducer;
        this.mailService = mailService;
        log.info("mailService "+mailService);
        log.info("this in constrc "+this);
    }

    /** 根据账号密码进行登录*/
//    @Override
//    public ResponseResult<String> loginIn(String account, String password) {
//        // 根据account查询user
//        User user= userMapper.selectUserByAccount(account);
//        if (user==null){
//            String msg=account+ "不存在";
//            log.debug(msg);
//            return ResponseResult.failed(ResultCode.ACCOUNT_NOT_EXIST);
//        }
//        // 比对密码
//        if (user.getPassword().equals(password))
//            return ResponseResult.success();
//        else
//            return ResponseResult.failed(ResultCode.USER_PASSWORD_ERROR);
//
//    }
    @Override
    public ResponseResult<User> loginIn(String account, String password) {
        // todo 在这个步骤中唯独少了将认证信息放入session，我很奇怪
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(account,password);
        Authentication authentication=authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        LoginUser loginUser=(LoginUser) authentication.getPrincipal();
        return ResponseResult.success(loginUser.getUser());
        // 根据account查询user
//        try {
//            LoginUser loginUser= (LoginUser) loadUserByUsername(account);
//            User user=loginUser.getUser();
//            // 比对密码
//            if (user.getPassword().equals(password))
//                return ResponseResult.success();
//            else
//                return ResponseResult.failed(ResultCode.USER_PASSWORD_ERROR);
//        }
//        catch (UsernameNotFoundException e){
//            String msg=account+ "不存在";
//            log.debug(msg);
//            return ResponseResult.failed(ResultCode.ACCOUNT_NOT_EXIST);
//        }



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

    @Override
    public ResponseResult follow(FollowDTO followDTO) {
        // 判断是否自己关注自己
        if (followDTO.getFollowerId().equals(followDTO.getPosterId())){
            return ResponseResult.failed(ResultCode.FORBID_FOLLOW_SELF);
        }

        // 未关注时，进行关注
        // todo 注意可能出现关注已经关注的，交予前端进行处理
        if (followDTO.getStatus()){
            userMapper.insertFollow(followDTO.getPosterId(),followDTO.getFollowerId());
        }
        // 关注时，取消关注
        else {
            userMapper.deleteFollow(followDTO.getPosterId(),followDTO.getFollowerId());
        }
        return ResponseResult.success();
    }


    @SneakyThrows
    @RabbitListener(queues = MsgQueueConstant.EMAIL_QUEUE)
    @RabbitHandler
    public void sendRegisterSuccessEmail(@Payload User user){
        log.info("user email "+user.getEmail());
        log.info("in handler "+ mailService);
        log.info("in handler um"+ userMapper);
        log.info("in handler rp"+ rabbitProducer);
        log.info("this "+this);
        mailService.sendSimpleMail(user.getEmail(),"注册成功","恭喜你——"+user.getUserName()+"成功注册本网站");
        log.info("test success");

    }
//    @SneakyThrows
//    @RabbitListener(queues = MsgQueueConstant.EMAIL_QUEUE)
//    @RabbitHandler
//    public void sendRegisterSuccessEmail(Message message, Channel channel){
//
//
////            log.info(str);
////            byte[] userBytes=message.getBody();
////            log.info("user bytes "+ Arrays.toString(userBytes));
////            user = (User) getObjectFromBytes(userBytes);
//        User user= (User) getObjectFromBytes(message.getBody());
//        log.info("user email "+user.getEmail());
//        log.info("in handler "+ mailService);
//        log.info("in handler um"+ userMapper);
//        log.info("in handler rp"+ rabbitProducer);
//        log.info("this "+this);
////            mailService.sendSimpleMail(user.getEmail(),"注册成功","恭喜你——"+user.getUserName()+"成功注册本网站");
//        log.info("test success");
//        try {
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }


    private Object getObjectFromBytes(byte[] objBytes) throws IOException, ClassNotFoundException {
        if (objBytes == null || objBytes.length == 0) {
            return null;
        }
        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
        ObjectInputStream oi = new ObjectInputStream(bi);
        return oi.readObject();
    }




//    private List getAuthority(){
//        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
//    }
}
