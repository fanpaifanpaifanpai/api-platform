package com.fan.apibackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fan.apicommon.model.entity.InterfaceInfo;

/**
* @author 19677
* @description 针对表【interface_info】的数据库操作Service
* @createDate 2023-02-14 23:52:27
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    /**
     * 接口参数检验
     * @param interfaceInfo 接口信息
     * @param add 是否为创建
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo,boolean add);
}
