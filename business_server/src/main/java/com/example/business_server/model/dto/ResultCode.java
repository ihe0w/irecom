package com.example.business_server.model.dto;

public enum ResultCode {
    SUCCESS(200,"操作成功"),
    FAILED(500,"操作失败"),

    // 登录注册
    NEED_LOGIN(401,"需要先登录"),
    LOGIN_TIMEOUT(402,"登录超时"),
    NO_AUTHENTICATION(1003006, "无权访问"),
    ACCOUNT_NOT_EXIST(11009, "该用户不存在"),
    USER_PASSWORD_ERROR(11006, "用户名或密码错误"),
    USER_IS_EXIST(11012, "用户已存在"),
    USER_MAX_LOGIN(11002, "该用户已在其它地方登录"),

    // 文件
    UPLOAD_FAIL(20001,"文件上传失败"),

    // 关注
    FORBID_FOLLOW_SELF(30001,"不能自己关注自己" );



    private int code;
    private String msg;

    ResultCode(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
    }
}
