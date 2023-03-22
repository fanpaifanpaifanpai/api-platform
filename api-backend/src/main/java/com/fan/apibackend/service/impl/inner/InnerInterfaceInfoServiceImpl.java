package com.fan.apibackend.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fan.apibackend.mapper.InterfaceInfoMapper;
import com.fan.apicommon.api.ResultCode;
import com.fan.apicommon.exception.ApiException;
import com.fan.apicommon.model.entity.InterfaceInfo;
import com.fan.apicommon.service.InnerInterfaceInfoService;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import sun.dc.pr.PRError;

/**
 *
 * @author 19677
 */
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        if (StringUtils.isAnyBlank(url,method)) {
            throw new ApiException(ResultCode.FAILED);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url",url);
        queryWrapper.eq("method",method);
        return interfaceInfoMapper.selectOne(queryWrapper);
    }
}
