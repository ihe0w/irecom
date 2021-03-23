package com.example.business_server.security;

import com.example.business_server.model.dto.ResponseResult;
import com.example.business_server.model.dto.ResultCode;
import com.example.business_server.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;

@Slf4j
@Component
public class SessionInformationExpiredHandler implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {
        ServletUtils.render(sessionInformationExpiredEvent.getRequest(),
                sessionInformationExpiredEvent.getResponse(), ResponseResult.failed(ResultCode.USER_MAX_LOGIN));
    }
}
