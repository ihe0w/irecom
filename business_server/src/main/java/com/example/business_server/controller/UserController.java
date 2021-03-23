package com.example.business_server.controller;

import com.example.business_server.model.domain.User;
import com.example.business_server.model.dto.FollowDTO;
import com.example.business_server.model.dto.ResponseResult;
import com.example.business_server.model.dto.ResultCode;
import com.example.business_server.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("api/user")
@CrossOrigin
public class UserController {
    final
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/info")
    @ResponseBody
    public ResponseResult<User> userInfo(){
        User user=new User();
        user.setUserName("wang");
        user.setEmail("123@gmail.com");
        user.setAvatarUrl("https://www.google.com/search?q=%E5%A4%B4%E5%83%8F&newwindow=1&sxsrf=ALeKk00zci4p2tFLBYMKYs-RnXJKqR4h1A:1596943219674&tbm=isch&source=iu&ictx=1&fir=6MGSCbuFz24PIM%252Cg5eteB6Z1EzlOM%252C_&vet=1&usg=AI4_-kQF7AbcLe3YmJu75bqvnP9GB2iKvQ&sa=X&ved=2ahUKEwjX5daKlY3rAhXWxIsBHSyEASkQ9QEwCXoECAwQIA#imgrc=6MGSCbuFz24PIM");
        user.setMobile("13788889999");
        user.setSex((short) 1);
        user.setUserId((long) 123456);

        return ResponseResult.success(user);
    }

    @GetMapping("/userPage/{user}")
    @ResponseBody
    public ResponseResult<User> userPage(@PathVariable String user){
        return ResponseResult.success(user);
    }

//    @GetMapping('/updateUserInfo')
//
//    @GetMapping('/userPersonalInfo')
//
//    @PostMapping('/register')


    @PostMapping("/loginIn")
    @ResponseBody
    public ResponseResult<User> loginIn(@RequestBody User user){
        String account=user.getAccount();
        String password=user.getPassword();
        return userService.loginIn(account,password);
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseResult<String> register(@RequestBody User user){
        String account=user.getAccount();
        String password=user.getPassword();

        if (userService.register(account, password)){
            return ResponseResult.success();
        }
        return ResponseResult.failed(ResultCode.USER_IS_EXIST);
    }

    @PostMapping("/follow")
    @ResponseBody
    @ApiOperation(value = "关注")
    public ResponseResult follow(@RequestBody FollowDTO followDTO){
        return userService.follow(followDTO);
    }

//    @GetMapping('/signOut')





}
