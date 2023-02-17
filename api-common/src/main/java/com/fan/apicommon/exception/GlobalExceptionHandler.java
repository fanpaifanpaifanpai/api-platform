package com.fan.apicommon.exception;

import com.fan.apicommon.api.CommonResult;
import com.fan.apicommon.api.ResultUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * @author 19677
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public CommonResult<?> apiExceptionHandler(ApiException apiException){
        return ResultUtils.fail(apiException.getErrorCode());
    }
}
