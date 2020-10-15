package com.example.business_server.model.domain;


import java.sql.Date;

public class Comment {
    private Long postId;
    private Long userId;
    private String commentText;
    private Float polarity;
    private Date createdTime;
    private Date updatedTime;
}
