package com.fan.apicommon.api;

/**
 * 常用API返回对象接口
 * @author 19677
 */
public interface IErrorCode {

    /**
     *返回码
     * @return 返回码
     */
    long getCode();

    /**
     * 返回信息
     * @return 返回信息
     */
    String getMessage();
}
