package com.example.business_server.model.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class followDTO {
    private Long userId;
    private Long followedId;
    private Short status;//1 follow 0: unfollow
    private Date createdTime;
    private Date updatedTime;
}
