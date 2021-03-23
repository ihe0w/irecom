package com.example.business_server.security;

import com.example.business_server.model.dto.ResponseResult;
import com.example.business_server.model.dto.ResultCode;
import com.example.business_server.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AnonymousAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.warn("用户需要登录，访问[{}]失败，AuthenticationException={}", request.getRequestURI(), e);
        ServletUtils.render(request,httpServletResponse, ResponseResult.failed(ResultCode.NEED_LOGIN));
    }
}
