package com.example.business_server;

import com.example.business_server.model.nosql.Recommendation;
import com.example.business_server.service.MailService;
import com.example.business_server.service.RecommendService;
import com.example.business_server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;


import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class ServiceTest extends BusinessServerApplicationTests{
    @Autowired
    RecommendService recommendService;
    @Autowired
    MailService mailService;
    @Autowired
    UserService userService;

    @Test
    public void testMDb(){
        log.info("is there log?");
        List<Recommendation> results=recommendService.getCollaborativeFilteringRecommendations((long) 123,2);
        assertThat(results,nullValue());
    }

    @Test
    public void testSendSimpleEmail(){
        mailService.sendSimpleMail("1904618021@qq.com","send email test","just test");
    }
    @Test
    public void testRegister(){
        String account="1904618021@qq.com";
        String password="772345";
        userService.register(account,password);
    }
}
