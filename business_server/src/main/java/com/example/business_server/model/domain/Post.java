package com.example.business_server.model.domain;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class Post {
    private Long postId;
    private String postUrl;
    private List<String> imgUrls;
    private Date createdTime;
    private Date updatedTime;

}
