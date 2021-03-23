package com.example.user_manage.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value = "/user")
    public String hello(){
        return "hello,nacos";
    }
}
