package com.yt.utils;

import java.io.Serializable;

/**
 * 返回JSON数据的包装实体类
 *
 * @author yt
 * @date 2019/10/9 - 14:14
 */
public class Result implements Serializable {
    private Integer code;
    private String message;
    private Object data;

    public Result() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setResultCode(ResultCodeEnum resultCode) {
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public Result(ResultCodeEnum resultCode, Object data) {
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.data = data;
    }


    //返回方法
    public static Result success() {
        Result result = new Result();
        result.setResultCode(ResultCodeEnum.SUCCESS);
        return result;
    }

    //返回成功
    public static Result success(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCodeEnum.SUCCESS);
        result.setData(data);
        return result;
    }


    //指定返回码方法
    public static Result failure(ResultCodeEnum resultCode) {
        Result result = new Result();
        result.setResultCode(resultCode);
        return result;
    }

    //指定返回码方法加数据
    public static Result failure(ResultCodeEnum resultCode, Object data) {
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }

}
