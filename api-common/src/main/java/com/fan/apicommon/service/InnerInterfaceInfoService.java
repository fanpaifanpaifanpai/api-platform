package com.fan.apicommon.service;

import com.fan.apicommon.model.entity.InterfaceInfo;

/**
 * @author 19677
 */
public interface InnerInterfaceInfoService {

    /**
     * 从数据库中查询模拟接口是否存在（请求路径、请求方法、请求参数）
     * @param path 请求路径
     * @param method 请求方法
     * @return
     */
    InterfaceInfo getInterfaceInfo(String path,String method);
}
