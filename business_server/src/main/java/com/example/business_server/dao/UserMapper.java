package com.example.business_server.dao;

import com.example.business_server.model.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT EXISTS(SELECT 1 FROM user WHERE account=#{account})")
    Boolean checkAccountExists(@Param("account")String account);

    @Insert("INSERT INTO user(user_name,account,password) VALUES(#{user.userName},#{user.account},#{user.password})")
    @Options(useGeneratedKeys = true,keyColumn = "user_id",keyProperty = "userId")
    void insertUser(@Param("user")User user);

    @Select("SELECT * FROM user WHERE account=#{account}")
    User selectUserByAccount(@Param("account")String account);

    @Insert("INSERT INTO follow(poster_id,follower_id) VALUES(#{posterId},#{followerId})")
    void insertFollow(Long posterId, Long followerId);

    @Delete("DELETE FROM follow where poster_id = #{posterId} AND follower_id = #{followerId}")
    void deleteFollow(Long posterId, Long followerId);

    @Select("SELECT user_id,user_name,avatar_url FROM user WHERE user_id IN (SELECT poster_id FROM follow WHERE follower_id=#{followerId})")
    List<User> selectPosterIdByFollow(Long followerId);
}
