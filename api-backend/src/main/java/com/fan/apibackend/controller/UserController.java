package com.fan.apibackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fan.apibackend.model.dto.user.UserLoginRequest;
import com.fan.apibackend.model.dto.user.UserRegisterRequest;
import com.fan.apibackend.model.vo.UserVO;
import com.fan.apibackend.service.UserService;
import com.fan.apicommon.api.CommonResult;
import com.fan.apicommon.api.ResultCode;
import com.fan.apicommon.api.ResultUtils;
import com.fan.apicommon.exception.ApiException;
import com.fan.apicommon.model.entity.User;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 19677
 * 13点36分2023年1月29日
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    // region 登录相关

    /**
     * 注册用户
     * @param userRegisterRequest 用户注册dto
     * @return 用户id
     */
    @PostMapping("/register")
    public CommonResult<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if (userRegisterRequest == null){
            throw new ApiException(ResultCode.FAILED);
        }
        String userName = userRegisterRequest.getUserName();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userName,userPassword,checkPassword)){
            throw new ApiException(ResultCode.VALIDATE_FAILED);
        }
        Long result = userService.userRegister(userName, userPassword, checkPassword);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     * @param userLoginRequest 用户登录请求dto
     * @param request http请求
     * @return 脱敏用户信息
     */
    @PostMapping("/login")
    public CommonResult<User> userLogin(@RequestBody UserLoginRequest userLoginRequest,
                                        HttpServletRequest request){
        if (userLoginRequest == null){
            throw new ApiException(ResultCode.FAILED);
        }
        String userName = userLoginRequest.getUserName();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userName,userPassword)){
            throw new ApiException(ResultCode.VALIDATE_FAILED);
        }
        User result = userService.userLogin(userName, userPassword,request);
        return ResultUtils.success(result);
    }

    /**
     * 用户注销
     * @param request http请求
     * @return 布尔信息
     */
    @PostMapping("/logout")
    public CommonResult<Boolean> userLogout(HttpServletRequest request){
        if (request == null){
            throw new ApiException(ResultCode.FAILED);
        }
        Boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 用户注销
     * @param request
     * @return
     */
    @GetMapping("/user/logout")
    public CommonResult<UserVO> getLoginUser(HttpServletRequest request){
        User user = userService.getLoginUser(request);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        return ResultUtils.success(userVO);
    }


    //endregion

    // region 增删改查

    public void add (User user){
        final boolean save = userService.save(user);
    }

    public void delete(long id){
        User user = new User();
        user.setId(id);
        userService.removeById(user);
    }
    public void update(User user){
        QueryWrapper queryWrapper = new QueryWrapper(user);
        userService.update(queryWrapper);
    }

    public void get(long id){
        userService.getById(id);
    }

    public void list(){
        userService.list();
    }

    public void listByPage(){

    }

    //endregion


}
