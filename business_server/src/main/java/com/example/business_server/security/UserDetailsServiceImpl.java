package com.example.business_server.security;

import com.example.business_server.dao.UserMapper;
import com.example.business_server.model.domain.User;
import com.example.business_server.model.dto.LoginType;
import com.example.business_server.model.nosql.LoginUser;
import com.example.business_server.utils.IpUtils;
import com.example.business_server.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        User user=userMapper.selectUserByAccount(account);
        if (user==null){
            String msg=account+ "未登录";
            throw new UsernameNotFoundException(msg);
        }
        // 我之前竟然都没想到new 一个这个user进行封装
        return new LoginUser(user, IpUtils.getIpAddr(ServletUtils.getRequest()), LocalDateTime.now(), LoginType.PASSWORD);

    }
}
