package com.example.business_server.model.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class LikeDTO {
    private Long userId;
    private Long postId;
    private Short status;//1 like 0 cancel like
    private Date createdTime;
    private Date updatedTime;
}
