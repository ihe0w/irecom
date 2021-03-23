package com.example.business_server.model.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.sql.Date;

@Data
public class User implements Serializable {
    private static final long serialVersionUID=1L;
    @Id
    private Long userId;
    private String userName;
    private String account;
    private String email;
    private String mobile;
    private String password;
    private String avatarUrl;
    private Short sex;
    private String role;
}
