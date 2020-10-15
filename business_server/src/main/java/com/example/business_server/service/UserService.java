package com.example.business_server.service;

import com.example.business_server.model.dto.ResponseResult;

public interface UserService {
    public ResponseResult<String> loginIn(String account,String password);
}
