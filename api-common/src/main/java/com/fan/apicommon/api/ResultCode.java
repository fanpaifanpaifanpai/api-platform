package com.fan.apicommon.api;

/**
 * 常用API返回对象
 * @author 19677
 */

public enum ResultCode implements IErrorCode{
    /**
     * 操作成功返回
     */
    SUCCESS(200,"操作成功"),
    /**
     *
     */
    VALIDATE_FAILED(404, "参数检验失败"),

    /**
     *
     */
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    /**
     *
     */
    FAILED(500, "操作失败");


    /**
     * 状态码
     */
    private long code;

    /**
     * 信息
     */
    private String message;

    ResultCode(long code,String message){
        this.code = code;
        this.message = message;
    }
    @Override
    public long getCode() {
        return 0;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
