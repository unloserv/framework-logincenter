package com.canghuang.logincenter.core;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author cs
 * @date 2018/9/11
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
public class Result {

    private final static int SUCCESS = 1;
    private final static int FAILURE = 0;
    private final static int ERROR = -1;

    private int code;
    private String msg;
    private Object data;



    public Result(int code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static Result success(String message, Object data) {
        return new Result(SUCCESS, data, message);
    }

    public static Result success(String message) {
        return new Result(SUCCESS, null, message);
    }

    public static Result failure(String message) {
        return new Result(FAILURE, null, message);
    }

    public static Result error() {
        return new Result(ERROR, null, "服务器发生错误，请联系管理员");
    }
}
