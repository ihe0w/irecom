package com.example.business_server.model.domain;

import lombok.Data;

import java.sql.Date;

@Data
public class User {
    private Long userId;
    private String userName;
    private String email;
    private String mobile;
    private String password;
    private String avatarUrl;
    private Short sex;
    private Date createdTime;
    private Date updatedTime;
}
