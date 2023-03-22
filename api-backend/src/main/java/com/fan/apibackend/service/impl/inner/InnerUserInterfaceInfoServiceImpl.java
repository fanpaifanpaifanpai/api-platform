package com.fan.apibackend.service.impl.inner;

import com.fan.apibackend.service.UserInterfaceInfoService;
import com.fan.apicommon.service.InnerUserInterfaceInfoService;
import javax.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author 19677
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;


    @Override
    public Boolean invokeCount(Long interfaceInfoId, Long userId) {
        Boolean result = userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
        return result;
    }
}
