package com.example.business_server.service;

import com.example.business_server.model.dto.ResponseResult;

public interface UserService {
    ResponseResult<String> loginIn(String account, String password);

    Boolean register(String account, String password);

}
