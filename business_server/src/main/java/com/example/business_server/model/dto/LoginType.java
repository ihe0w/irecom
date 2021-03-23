package com.example.business_server.model.dto;

public enum LoginType {
    PASSWORD(1,"密码");

    private int code;
    private String msg;

    LoginType(int code, String message) {
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
