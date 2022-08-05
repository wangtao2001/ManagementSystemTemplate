package com.example.backend.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 前后端数据交互的包装类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    public static final String SUCCESS = "200";
    public static final String SUCCESS_MSG = "成功";
    public static final String USERNAME_PWD_ERROR = "201";
    public static final String USERNAME_PWD_ERROR_MSG = "用户名或密码错误";
    public static final String USERNAME_REPEAT_ERROR = "202";
    public static final String USERNAME_REPEAT_ERROR_MSG = "用户名已存在";
    public static final String USERNAME_NOTFOUND_ERROR = "203";
    public static final String USERNAME_NOTFOUND_ERROR_MSG = "用户名不存在";
    public static final String USERNAME_FORMAT_ERROR = "204";
    public static final String USERNAME_FORMAT_ERROR_MSG = "用户名或密码格式不正确";
    public static final String USER_ADMIN_ERROR = "205";
    public static final String USER_ADMIN_ERROR_MSG = "用户没有权限登陆，请联系管理员";
    public static final String USER_CANCEL_ERROR = "206";
    public static final String USER_CANCEL_ERROR_MSG = "用户已注销";

    private String code;
    private String msg;
    private T data;

    /**
     * 数据交互成功，无返回数据
     */
    public static Result<?> success() {
        Result<?> result = new Result<>();
        result.setCode(Result.SUCCESS);
        result.setMsg(Result.SUCCESS_MSG);
        return result;
    }

    /**
     * 数据交互成功，返回数据为data
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(Result.SUCCESS, Result.SUCCESS_MSG, data);
    }

    /**
     * 数据交互失败，需要提供错误码与错误信息
     */
    public static Result<?> error(String code, String msg) {
        Result<?> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
