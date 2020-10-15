package com.example.business_server.service.impl;

import com.example.business_server.model.dto.ResponseResult;
import com.example.business_server.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public ResponseResult<String> loginIn(String account, String password) {
        return ResponseResult.success("login success");
    }
}
