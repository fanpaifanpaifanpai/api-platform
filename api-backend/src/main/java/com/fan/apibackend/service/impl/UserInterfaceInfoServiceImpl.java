package com.fan.apibackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fan.apibackend.mapper.UserInterfaceInfoMapper;
import com.fan.apibackend.service.UserInterfaceInfoService;
import com.fan.apicommon.api.ResultCode;
import com.fan.apicommon.exception.ApiException;
import com.fan.apicommon.model.entity.UserInterfaceInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* @author 19677
* @description 针对表【user_interface_info】的数据库操作Service实现
* @createDate 2023-02-21 12:53:31
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService {



    @Override
    public Boolean invokeCount(Long interfaceInfoId, Long userId) {
        if (interfaceInfoId == null || userId == null){
            throw new ApiException(ResultCode.FAILED);
        }

        if (interfaceInfoId <= 0 || userId <= 0){
            throw new ApiException(ResultCode.FAILED);
        }
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interface_info_id",interfaceInfoId);
        updateWrapper.eq("user_id",userId);
        updateWrapper.setSql("left_num = left_num - 1,total_num = total_num + 1");
        return this.update(updateWrapper);
    }
}




