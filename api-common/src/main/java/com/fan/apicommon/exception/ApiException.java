package com.fan.apicommon.exception;

import com.fan.apicommon.api.IErrorCode;

/**
 * 自定义API异常
 * @author 19677
 */
public class ApiException extends RuntimeException {
    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    public IErrorCode getErrorCode(){
        return errorCode;
    }
}
