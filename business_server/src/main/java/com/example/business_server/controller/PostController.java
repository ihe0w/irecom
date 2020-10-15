package com.example.business_server.controller;

import com.example.business_server.model.domain.User;
import com.example.business_server.model.dto.ResponseResult;
import com.example.business_server.service.PostService;
import com.example.business_server.service.RecommendService;
import com.example.business_server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("api/post")
@CrossOrigin
public class PostController {
    final
    PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }






}
