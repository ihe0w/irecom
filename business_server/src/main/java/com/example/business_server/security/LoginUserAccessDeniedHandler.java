package com.example.business_server.security;

import com.example.business_server.model.dto.ResponseResult;
import com.example.business_server.model.dto.ResultCode;
import com.example.business_server.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class LoginUserAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.warn("用户无权访问", accessDeniedException);
        ServletUtils.render(request, response, ResponseResult.failed(ResultCode.NO_AUTHENTICATION));
    }
}
