package com.example.business_server.security;

import com.example.business_server.model.dto.ResponseResult;
import com.example.business_server.model.dto.ResultCode;
import com.example.business_server.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class InvalidSessionHandler implements InvalidSessionStrategy {
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletUtils.render(request, response, ResponseResult.failed(ResultCode.LOGIN_TIMEOUT.getMessage()));

    }
}
