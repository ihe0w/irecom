package com.example.business_server.model.dto;

import lombok.Data;

@Data
public class LikeDTO {
    private Long userId;
    private Long postId;
    private Boolean status;//true like ,false cancel like

}
