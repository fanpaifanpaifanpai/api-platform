package com.fan.apicommon.api;

/**
 * @author 19677
 */
public class ResultUtils {

    public static <T> CommonResult<T> success(T data){
        return new CommonResult(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage(),data);
    }

    public static   CommonResult fail(IErrorCode errorCode){
        return new CommonResult(errorCode);
    };
}
