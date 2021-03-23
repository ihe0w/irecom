package com.example.business_server.model.domain;


import lombok.Data;


import java.sql.Date;
@Data
public class Comment {
    private Long commentId;
    private Long postId;
    private Long replyCommentId;
    private Long commentUserId;
    private String content;
}
