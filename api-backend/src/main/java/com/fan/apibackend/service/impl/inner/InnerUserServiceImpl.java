package com.fan.apibackend.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fan.apibackend.mapper.UserMapper;
import com.fan.apicommon.api.ResultCode;
import com.fan.apicommon.exception.ApiException;
import com.fan.apicommon.model.entity.User;
import com.fan.apicommon.service.InnerUserService;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author 19677
 */
@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getInvokeUser(String accessKey) {
        if (StringUtils.isBlank(accessKey)){
            throw new ApiException(ResultCode.FAILED);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("access_key",accessKey);
        return userMapper.selectOne(queryWrapper);
    }
}
