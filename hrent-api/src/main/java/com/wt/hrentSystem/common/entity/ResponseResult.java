package com.wt.hrentSystem.common.entity;

import java.io.Serializable;

/**
 * 后端同意返回结果
 * @param <T>
 */
public class ResponseResult<T> implements Serializable {
    //状态码
    private String code;
    //数据
    private T data;
    //消息
    private String message;

    public ResponseResult() {
    }

    public ResponseResult(String code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public ResponseResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ResponseResult<?> success(){
        return new ResponseResult("200","成功");
    }
    public static ResponseResult<?> failed(){
        return new ResponseResult("500","失败");
    }
    public static ResponseResult<?> logout() {return new ResponseResult<>("600","未登录");}
}
