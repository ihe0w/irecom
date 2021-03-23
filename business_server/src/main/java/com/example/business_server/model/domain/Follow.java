package com.example.business_server.model.domain;

import lombok.Data;

@Data
public class Follow {
    private Long posterId;
    private Long followerId;
}
