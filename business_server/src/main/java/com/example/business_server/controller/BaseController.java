package com.example.business_server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("api")
@CrossOrigin
public class BaseController {
    @GetMapping("/hello")
    public String hello(){
        return "Hello,world";
    }
}
