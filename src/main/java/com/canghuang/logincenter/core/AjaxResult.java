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
public class AjaxResult {

    private final static int SUCCESS = 1;
    private final static int FAILURE = 0;
    private final static int ERROR = -1;

    private int code;
    private String msg;
    private Object data;



    public AjaxResult(int code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static AjaxResult success(String message, Object data) {
        return new AjaxResult(SUCCESS, data, message);
    }

    public static AjaxResult success(String message) {
        return new AjaxResult(SUCCESS, null, message);
    }

    public static AjaxResult failure(String message) {
        return new AjaxResult(FAILURE, null, message);
    }

    public static AjaxResult error() {
        return new AjaxResult(ERROR, null, "服务器发生错误，请联系管理员");
    }
}
