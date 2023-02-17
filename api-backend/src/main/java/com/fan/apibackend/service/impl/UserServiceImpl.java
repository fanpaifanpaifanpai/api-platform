package com.fan.apibackend.service.impl;

import static com.fan.apibackend.constants.UserConstant.ADMIN_ROLE;
import static com.fan.apibackend.constants.UserConstant.USER_LOGIN_STATE;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fan.apicommon.api.ResultCode;
import com.fan.apicommon.exception.ApiException;
import com.fan.apicommon.model.entity.User;
import com.fan.apibackend.mapper.UserMapper;
import com.fan.apibackend.service.UserService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
* @author 19677
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-01-27 13:56:40
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    /**
     * 盐值
     */
    private static final String SALT = "FAN";

    @Resource
    private UserMapper userMapper;

    @Override
    public Long userRegister(String userName, String userPassword, String checkPassword) {
        //1参数不能为空
        if (StringUtils.isAnyBlank(userName,userPassword,checkPassword)){
            throw  new ApiException(ResultCode.VALIDATE_FAILED);
        }
        //2参数长度
        if (userName.length() < 4 || userName.length() > 20){
            throw  new ApiException(ResultCode.VALIDATE_FAILED);
        }
        if (userPassword.length() < 4 || userPassword.length() > 20){
            throw  new ApiException(ResultCode.VALIDATE_FAILED);
        }
        if (checkPassword.length() < 4 || checkPassword.length() > 20){
            throw  new ApiException(ResultCode.VALIDATE_FAILED);
        }
        //3密码校验密码相同
        if (!userPassword.equals(checkPassword)){
            throw  new ApiException(ResultCode.VALIDATE_FAILED);
        }
        //4查看用户名是否已存在
        synchronized(userName.intern()){
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_name",userName);
            User user = userMapper.selectOne(queryWrapper);
            if (user != null){
               throw new ApiException(ResultCode.FAILED);
            }
        }
        //5生成ak sk
        String accessKey = DigestUtil.md5Hex(SALT + userName + RandomUtil.randomNumbers(5));
        String secretKey = DigestUtil.md5Hex(SALT + userName + RandomUtil.randomNumbers(8));
        //6加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //7写入数据
        User user = new User();
        user.setUserName(userName);
        user.setAccessKey(accessKey);
        user.setSecretKey(secretKey);
        user.setUserPassword(encryptPassword);
        boolean save = this.save(user);
        if (!save){
            throw new ApiException(ResultCode.FAILED);
        }
        Long id = user.getId();

        return id;
    }

    @Override
    public User userLogin(String userName, String userPassword, HttpServletRequest request) {
        //1参数不能为空
        if (StringUtils.isAnyBlank(userName,userPassword)){
            throw  new ApiException(ResultCode.VALIDATE_FAILED);
        }
        //2参数长度
        if (userName.length() < 4 || userName.length() > 20){
            throw  new ApiException(ResultCode.VALIDATE_FAILED);
        }
        if (userPassword.length() < 4 || userPassword.length() > 20){
            throw  new ApiException(ResultCode.VALIDATE_FAILED);
        }
        //3加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //4查询用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        queryWrapper.eq("user_password",encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null){
            throw new ApiException(ResultCode.FAILED);
        }
        //5记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE,user);
        return user;
    }

    @Override
    public Boolean userLogout(HttpServletRequest request) {
        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null){
            throw new ApiException(ResultCode.UNAUTHORIZED);
        }
        //移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser =(User) userObj;
        if (currentUser == null || currentUser.getId() == null){
            throw new ApiException(ResultCode.UNAUTHORIZED);
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        long userId = currentUser.getId();
        currentUser = this.getById(userId);
        if (currentUser == null){
            throw new ApiException(ResultCode.FAILED);
        }
        return currentUser;
    }

    @Override
    public Boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && ADMIN_ROLE.equals(user.getUserRole());
    }
}




