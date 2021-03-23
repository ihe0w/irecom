package com.example.business_server.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "评论传输对象")
public class CommentDTO {
    private Long commentId;
    private Long postId;
    private Long replyCommentId;
    private Long commentUserId;
    private String content;
}
