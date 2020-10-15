package com.example.business_server.model.dto;

public class ResponseResult<T> {
    private long status;
    private String msg;
    private T data;

    protected ResponseResult() {
    }
    protected ResponseResult(long status, String msg, T data){
        this.status=status;
        this.msg=msg;
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

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> ResponseResult<T> failed(String message) {
        return new ResponseResult<>(ResultCode.FAILED.getCode(), message, null);
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
