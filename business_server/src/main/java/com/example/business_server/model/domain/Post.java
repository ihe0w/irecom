package com.example.business_server.model.domain;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
public class Post implements Serializable {
    private static final long serialVersionUID = -7789724911827474291L;
    private Long postId;
    private Long ownerId;
    private String userName;
    private String avatarUrl;
    private String desc;
    private List<String> resUrls;
    private Date createdTime;
    private Date updatedTime;

}
