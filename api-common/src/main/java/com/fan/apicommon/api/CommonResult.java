package com.fan.apicommon.api;

import lombok.Data;

/**
 *通用返回类
 * @author 19677
 */
@Data
public class CommonResult<T> {

    /**
     * 数据封装
     */
    private T data;

    /**
     * 提示信息
     */
    private String message;

    /**
     *状态码
     */
    private long code;

    protected CommonResult() {
    }

    protected CommonResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    protected CommonResult(IErrorCode errorCode){
        this.message = errorCode.getMessage();
        code = errorCode.getCode();
    }

}
