package com.example.business_server.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ConfigurationProperties(prefix = PermitAllUrlProperties.PREFIX)
public class PermitAllUrlProperties implements InitializingBean {
    public static final String PREFIX="security";

    @Autowired
    private WebApplicationContext applicationContext;

    @Getter
    @Setter
    private List<String> ignoreUrls = new ArrayList<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        RequestMappingHandlerMapping mapping=applicationContext.getBean(RequestMappingHandlerMapping.class);


        String[] authWhiteList = {
                // 放行 swagger 相关路径
                "/swagger-ui.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/**",
                "/csrf",


                // other
                "/open/**",
                "/actuator/**",
                "/assets/**",
                "/instances",
                "/favicon.ico",
                "/api/user/loginIn",
                "/api/user/register"
        };
        ignoreUrls.addAll(Arrays.asList(authWhiteList));
    }
}
