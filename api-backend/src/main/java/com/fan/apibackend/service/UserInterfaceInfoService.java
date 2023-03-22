package com.fan.apibackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fan.apicommon.model.entity.UserInterfaceInfo;

/**
* @author 19677
* @description 针对表【user_interface_info】的数据库操作Service
* @createDate 2023-02-21 12:53:31
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    /**
     * 接口调用次数统计
     * @param interfaceInfoId 接口id
     * @param userId 用户id
     * @return 调用统计布尔结果
     */
    Boolean invokeCount(Long interfaceInfoId,Long userId);

}
