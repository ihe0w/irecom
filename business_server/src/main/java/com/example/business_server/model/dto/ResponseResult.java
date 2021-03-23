package com.example.business_server.model.dto;

public class ResponseResult<T> {
    private Integer status;
    private String message;
    private T data;

    protected ResponseResult() {
    }
    protected ResponseResult(Integer status, String message, T data){
        this.status=status;
        this.message = message;
        this.data=data;
    }
    public static <T>ResponseResult<T> success(T data){
        return new ResponseResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }
    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> ResponseResult<T> success(T data, String message) {
        return new ResponseResult<>(ResultCode.SUCCESS.getCode(), message, data);
    }
    public static <T> ResponseResult<T> success(String message) {
        return new ResponseResult<>(ResultCode.SUCCESS.getCode(), message, null);
    }
    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>(ResultCode.SUCCESS.getCode(), null, null);
    }
    public static <T> ResponseResult<T> success(ResultCode resultCode) {
        return new ResponseResult<>(resultCode.getCode(), resultCode.getMessage(), null);
    }

    public static <T> ResponseResult<T> success(ResultCode resultCode,T data) {
        return new ResponseResult<>(resultCode.getCode(), resultCode.getMessage(), data);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> ResponseResult<T> failed(String message) {
        return new ResponseResult<>(ResultCode.FAILED.getCode(), message, null);
    }


    public static <T> ResponseResult<T> failed(ResultCode resultCode) {
        return new ResponseResult<>(resultCode.getCode(), resultCode.getMessage(), null);
    }

    public static <T> ResponseResult<T> failed(ResultCode resultCode,T data) {
        return new ResponseResult<>(resultCode.getCode(), resultCode.getMessage(), data);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
