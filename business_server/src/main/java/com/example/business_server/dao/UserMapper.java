package com.example.business_server.dao;

import com.example.business_server.model.domain.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT EXISTS(SELECT 1 FROM user WHERE account=#{account})")
    Boolean checkAccountExists(@Param("account")String account);

    @Insert("INSERT INTO user(user_name,account,password) VALUES(#{user.userName},#{user.account},#{user.password})")
    @Options(useGeneratedKeys = true,keyColumn = "user_id",keyProperty = "userId")
    void insertUser(@Param("user")User user);
}
