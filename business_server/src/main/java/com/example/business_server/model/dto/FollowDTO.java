package com.example.business_server.model.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class FollowDTO {
    private Long posterId;
    private Long followerId;
    private Boolean status;//true follow false: unfollow

}
