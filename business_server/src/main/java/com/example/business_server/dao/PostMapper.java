package com.example.business_server.dao;

import com.example.business_server.model.domain.Post;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PostMapper {
    @Results(id = "postResultMap",value = {
            @Result(property = "postId", column = "post_id",id = true),
            @Result(property = "postUrl", column = "post_url"),
            @Result(property = "imgUrl", column = "img_url"),
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
}
