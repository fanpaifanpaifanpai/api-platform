package com.fan.apicommon.service;

/**
 * @author 19677
 */
public interface InnerUserInterfaceInfoService {

    /**
     * 接口调用次数统计
     * @param interfaceInfoId 接口id
     * @param userId 用户id
     * @return
     */
    Boolean invokeCount(Long interfaceInfoId,Long userId);
}
