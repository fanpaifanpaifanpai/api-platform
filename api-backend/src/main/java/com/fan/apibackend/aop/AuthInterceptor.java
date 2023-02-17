package com.fan.apibackend.aop;

import static com.fan.apibackend.constants.UserConstant.ROLES;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fan.apibackend.annotation.AuthCheck;
import com.fan.apibackend.service.UserService;
import com.fan.apicommon.api.ResultCode;
import com.fan.apicommon.exception.ApiException;
import com.fan.apicommon.model.entity.User;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 权限校验AQP
 * @author 19677
 */
@Aspect
@Component
public class AuthInterceptor {

    @Resource
    private UserService userService;

    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck)
        throws Throwable {
        List<String> anyRole = Arrays.stream(authCheck.anyRole()).filter(StringUtils::isNotBlank)
            .collect(Collectors.toList());
        String mustRole = authCheck.mustRole();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        User loginUser = userService.getLoginUser(request);
        Integer userRole = loginUser.getUserRole();
        String role = ROLES[userRole];
        if (CollectionUtils.isNotEmpty(anyRole)){

            if (!anyRole.contains(role)){
                throw new ApiException(ResultCode.FAILED);
            }
        }

        if (StringUtils.isNotBlank(mustRole)){
            if (!mustRole.equals(role)){
                throw new ApiException(ResultCode.FAILED);
            }
        }
        return  joinPoint.proceed();
    }
}
