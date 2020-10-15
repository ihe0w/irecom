package com.example.business_server.model.dto;

public enum ResultCode {
    SUCCESS(200,"操作成功"),
    FAILED(500,"操作失败");
    private int code;
    private String msg;

    private ResultCode(int code, String message) {
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
