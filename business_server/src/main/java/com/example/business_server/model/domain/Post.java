package com.example.business_server.model.domain;

import lombok.Data;

import java.sql.Date;

@Data
public class Post {
    private Long postId;
    private String postUrl;
    private String imgUrl;
    private Date createdTime;
    private Date updatedTime;

}
