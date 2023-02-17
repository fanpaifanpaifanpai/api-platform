package com.fan.apibackend.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fan.apicommon.model.entity.User;
import javax.servlet.http.HttpServletRequest;

/**
* @author 19677
* @description 针对表【user】的数据库操作Service
* @createDate 2023-01-27 13:56:40
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册接口
     * @param username 用户名
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @return 用户id
     */
    Long userRegister(String username,String userPassword,String checkPassword);

    /**
     * 用户登录接口
     * @param userName 用户名
     * @param userPassword 用户密码
     * @param request http请求
     * @return 脱敏用户信息
     */
    User userLogin(String userName, String userPassword, HttpServletRequest request);

    /**
     * 用户注销接口
     * @param request http请求
     * @return 布尔信息
     */
    Boolean userLogout(HttpServletRequest request);

    /**
     * 获取当前登录用户
     * @param request http请求
     * @return 用户信息
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 是否为管理员
     * @param request http请求
     * @return
     */
    Boolean isAdmin(HttpServletRequest request);

}
