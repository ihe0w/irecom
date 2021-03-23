package com.example.business_server.service;

import com.example.business_server.model.domain.User;
import com.example.business_server.model.dto.FollowDTO;
import com.example.business_server.model.dto.ResponseResult;

public interface UserService{
    ResponseResult<User> loginIn(String account, String password);

    Boolean register(String account, String password);

    ResponseResult follow(FollowDTO followDTO);
}
