package com.example.business_server.model.domain;

import lombok.Data;

import java.sql.Date;

@Data
public class Like {
    private Long postId;
    private Long userId;
    private Date createdTime;
    private Date updatedTime;
}
