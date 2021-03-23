package com.example.business_server.dao;

import com.example.business_server.model.domain.Comment;
import com.example.business_server.model.domain.Post;
import org.apache.ibatis.annotations.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

@Mapper
public interface PostMapper {
    @Results(id = "postResultMap",value = {
            @Result(property = "postId", column = "post_id",id = true),
            @Result(property = "postUrl", column = "post_url"),
            @Result(property = "createdTime", column = "created_time"),
            @Result(property = "updatedTime", column = "updated_time")
    })

    @Select("SELECT * FROM post WHERE post_id = #{postId}")
    Post findPostById(@Param("postId") Long postId);

    @Delete("DELETE FROM post where post_id = #{postId}")
    Integer deletePostById(@Param("postId") Long postId);

    @Insert("INSERT INTO post(post_id,post_url,img_url,created_time,updated_time)" +
            "values(#{postId},#{postUrl},#{imgUrl},#{createdTime},#{updatedTime})")
    @Options(useGeneratedKeys = true,keyProperty = "postId",keyColumn = "post_id")
    Integer insertPost(Post post);

    @Insert("INSERT INTO like(user_id,post_id) values(#{userId},#{postId})")
    void insertLike(Long userId, Long postId);

    @Delete("DELETE FROM like where user_id = #{userId} AND post_id = #{postId} ")
    void deleteLike(Long userId, Long postId);

    @Insert("INSERT INTO comment(comment_id,post_id,reply_comment_id,comment_user_id,content)" +
            "VALUES(#{comment.commentId},#{comment.postId},#{comment.replyCommentId},#{comment.commentUserId},#{comment.content})")
    void insertComment(Comment comment);

    @Delete("DELETE FROM comment where comment_id = #{commentId} ")
    void deleteComment(Long commentId);

    @Select("SELECT post_id,desc FROM post WHERE owner_id =#{userId} ORDER BY create_time")
    List<Post> selectPostsByUserOrderByCreateTime(Long userId);
}
