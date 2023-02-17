package com.fan.apibackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fan.apibackend.mapper.InterfaceInfoMapper;
import com.fan.apibackend.service.InterfaceInfoService;
import com.fan.apicommon.api.ResultCode;
import com.fan.apicommon.exception.ApiException;
import com.fan.apicommon.model.entity.InterfaceInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author 19677
* @description 针对表【interface_info】的数据库操作Service实现
* @createDate 2023-02-14 23:52:27
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService {

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null){
            throw new ApiException(ResultCode.FAILED);
        }
        String interfaceName = interfaceInfo.getInterfaceName();
        String interfaceUrl = interfaceInfo.getInterfaceUrl();
        String method = interfaceInfo.getMethod();
        if (add){
            if (StringUtils.isAnyBlank(interfaceName,interfaceUrl,method)) {
                throw new ApiException(ResultCode.VALIDATE_FAILED);
            }
        }

        if (StringUtils.isNotBlank(interfaceName) && interfaceName.length() > 100){
            throw new ApiException(ResultCode.VALIDATE_FAILED);
        }

        if (StringUtils.isNotBlank(interfaceUrl) && interfaceUrl.length() > 100){
            throw new ApiException(ResultCode.VALIDATE_FAILED);
        }

        if (StringUtils.isNotBlank(method) && method.length() > 100){
            throw new ApiException(ResultCode.VALIDATE_FAILED);
        }
    }
}




