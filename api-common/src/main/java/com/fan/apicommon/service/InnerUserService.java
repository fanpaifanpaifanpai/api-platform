package com.fan.apicommon.service;

import com.fan.apicommon.model.entity.User;

/**
 * 用户服务
 * @author 19677
 */

public interface InnerUserService {
    /**
     * 查询数据库中已分配accessKey的用户
     * @param accessKey 密钥
     * @return 用户实体类
     */
    User getInvokeUser(String accessKey);
}
