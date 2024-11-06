package com.hewie.home.entity;

import lombok.Data;

@Data
public class ResponseResult {
    public static final int CODE_SUCCESS = 20000;
    public static final int CODE_FAILED = 40000;
    public static final int NO_PERMISSION = 40001;

    private boolean success;
    private int code;
    private String msg;
    private Object data;
    public static ResponseResult SUCCESS() {
        ResponseResult responseResult = new ResponseResult();
        responseResult.code = CODE_SUCCESS;
        responseResult.success = true;
        return responseResult;
    }
    public static ResponseResult SUCCESS(String msg) {
        ResponseResult responseResult = SUCCESS();
        responseResult.msg = msg;
        return responseResult;
    }

    public static ResponseResult SUCCESS(String msg, Object data) {
        ResponseResult responseResult = SUCCESS(msg);
        responseResult.data = data;
        return responseResult;
    }
    public static ResponseResult FAILED(String msg) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.code = CODE_FAILED;
        responseResult.msg = msg;
        responseResult.success = false;
        return responseResult;
    }

    public static ResponseResult FAILED(String msg, Object data) {
        ResponseResult responseResult = FAILED(msg);
        responseResult.data = data;
        return responseResult;
    }

    public static ResponseResult NO_PERMISSION() {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(NO_PERMISSION);
        responseResult.setMsg("无访问权限");
        responseResult.setData("无访问权限");
        responseResult.setSuccess(false);
        return responseResult;
    }

    public ResponseResult setData(Object data) {
        this.data = data;
        return this;
    }
}

