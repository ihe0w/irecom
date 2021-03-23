package com.example.business_server.security;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**session解析器
 * */
@Slf4j
@Component
public class SessionResolver implements HttpSessionIdResolver{
    public static final String AUTH_TOKEN="irecomSessionID";

    private String sessionIdName =AUTH_TOKEN;

    private CookieHttpSessionIdResolver resolver;

    public SessionResolver() {
        initResolver();
    }

    public SessionResolver(String sessionIdName) {
        this.sessionIdName = sessionIdName;
        initResolver();
    }

    private void initResolver() {
        this.resolver=new CookieHttpSessionIdResolver();
        DefaultCookieSerializer serializer=new DefaultCookieSerializer();
        serializer.setCookieName(this.sessionIdName);
        this.resolver.setCookieSerializer(serializer);
    }

    /**解析获得sessionId*/
    @Override
    public List<String> resolveSessionIds(HttpServletRequest httpServletRequest) {
        List<String> cookies=resolver.resolveSessionIds(httpServletRequest);
        // 判断cookies是否有效
        if (cookies==null || cookies.isEmpty()){
            return cookies;
        }

        // 解析获得header中的sessionId
        String header=httpServletRequest.getHeader(this.sessionIdName);
        if (StrUtil.isNotBlank(header)){
            return Collections.singletonList(header);
        }

        // 解析获得request中的sessionId
        String sessionId=httpServletRequest.getParameter(this.sessionIdName);
        return (sessionId!=null) ? Collections.singletonList(sessionId) : Collections.emptyList();

    }

    /** response header设置sessionId*/
    @Override
    public void setSessionId(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String sessionId) {
        log.info(AUTH_TOKEN+"={}",sessionId);
        httpServletResponse.setHeader(this.sessionIdName,sessionId);
        this.resolver.setSessionId(httpServletRequest,httpServletResponse,sessionId);
    }

    @Override
    public void expireSession(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader(this.sessionIdName, "");
        this.resolver.setSessionId(request, response, "");
    }
}
