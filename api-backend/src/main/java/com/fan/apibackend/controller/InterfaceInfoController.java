package com.fan.apibackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fan.apibackend.annotation.AuthCheck;
import com.fan.apibackend.model.dto.interfaceInfo.InterfaceInfoAddRequest;
import com.fan.apibackend.model.dto.interfaceInfo.InterfaceInfoInvokeRequest;
import com.fan.apibackend.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.fan.apibackend.model.dto.interfaceInfo.InterfaceInfoUpdateRequest;
import com.fan.apibackend.model.enums.InterfaceInfoStatusEnum;
import com.fan.apibackend.service.InterfaceInfoService;
import com.fan.apibackend.service.UserService;
import com.fan.apiclientsdk.client.ApiClient;
import com.fan.apicommon.api.CommonResult;
import com.fan.apicommon.api.DeleteRequest;
import com.fan.apicommon.api.IdRequest;
import com.fan.apicommon.api.ResultCode;
import com.fan.apicommon.api.ResultUtils;
import com.fan.apicommon.constants.PageConstant;
import com.fan.apicommon.exception.ApiException;
import com.fan.apicommon.model.entity.InterfaceInfo;
import com.fan.apicommon.model.entity.User;
import com.google.gson.Gson;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 19677
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;
    @Resource
    private ApiClient apiClient;

    // region 增删改查

    /**
     * 添加接口
     * @param interfaceInfoAddRequest 接口添加请求dto
     * @param request http请求
     * @return 接口id
     */
    @PostMapping("/add")
    public CommonResult<Long> addInterfaceInfo(@RequestBody  InterfaceInfoAddRequest interfaceInfoAddRequest,
                                               HttpServletRequest request){
        if (interfaceInfoAddRequest == null){
            throw new ApiException(ResultCode.FAILED);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoAddRequest,interfaceInfo);
        //校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo,true);
        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setUserId(loginUser.getId());
        boolean result = interfaceInfoService.save(interfaceInfo);
        if (!result){
            throw new ApiException(ResultCode.FAILED);
        }
        Long interfaceInfoId = interfaceInfo.getId();
        return ResultUtils.success(interfaceInfoId);
    }

    /**
     * 删除接口
     * @param deleteRequest 删除请求类
     * @param request http请求
     * @return 删除布尔结果
     */
    @PostMapping("/delete")
    public CommonResult<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest,HttpServletRequest request){
        if (deleteRequest ==null || deleteRequest.getId() <= 0){
            throw new ApiException(ResultCode.FAILED);
        }
        Long id = deleteRequest.getId();
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null){
            throw new ApiException(ResultCode.FAILED);
        }
        User loginUser = userService.getLoginUser(request);
        if (!loginUser.getId().equals(oldInterfaceInfo.getId()) && !userService.isAdmin(request) ){
            throw new ApiException(ResultCode.FAILED);
        }
        boolean result = interfaceInfoService.removeById(id);
        return ResultUtils.success(result);
    }

    /**
     * 更新接口
     * @param interfaceInfoUpdateRequest 接口更新请求dto
     * @param request HttpServlet请求
     * @return 更新布尔结果封装
     */
    @PostMapping("/update")
    public CommonResult<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest,
                                                     HttpServletRequest request){
        if (interfaceInfoUpdateRequest == null || interfaceInfoUpdateRequest.getId() <= 0){
            throw new ApiException(ResultCode.FAILED);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoUpdateRequest,interfaceInfo);
        interfaceInfoService.validInterfaceInfo(interfaceInfo,false);
        Long id = interfaceInfoUpdateRequest.getId();
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null){
            throw new ApiException(ResultCode.FAILED);
        }
        User loginUser = userService.getLoginUser(request);
        if (!oldInterfaceInfo.getId().equals(loginUser.getId()) && userService.isAdmin(request)){
            throw new ApiException(ResultCode.FAILED);
        }
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据id查询接口
     * @param id 接口id
     * @return 接口封装信息
     */
    @GetMapping("/get")
    public CommonResult<InterfaceInfo> getInterfaceInfo(Long id){
        if (id == null || id <= 0){
            throw new ApiException(ResultCode.FAILED);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null){
            throw new ApiException(ResultCode.FAILED);
        }
        return ResultUtils.success(interfaceInfo);
    }

    /**
     * 查询列表（管理员）
     * @param interfaceInfoQueryRequest 接口查询请求dto
     * @return 接口列表封装
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public CommonResult<List<InterfaceInfo>> listInterfaceInfo(InterfaceInfoQueryRequest interfaceInfoQueryRequest){
        if (interfaceInfoQueryRequest == null){
            throw new ApiException(ResultCode.FAILED);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoQueryRequest,interfaceInfo);
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfo);
        List<InterfaceInfo> result = interfaceInfoService.list(queryWrapper);
        return ResultUtils.success(result);
    }

    @GetMapping("/list/page")
    public CommonResult<Page<InterfaceInfo>> listInterfaceInfoByPage(InterfaceInfoQueryRequest interfaceInfoQueryRequest){
        if (interfaceInfoQueryRequest == null){
            throw new ApiException(ResultCode.FAILED);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoQueryRequest,interfaceInfo);

        long currentPage = interfaceInfoQueryRequest.getCurrentPage();
        long pageSize = interfaceInfoQueryRequest.getPageSize();
        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();

        String interfaceDescription = interfaceInfo.getInterfaceDescription();
        //description 指出模糊查询
        interfaceInfo.setInterfaceDescription(null);
        //防爬虫
        if (pageSize > 50){
            throw new ApiException(ResultCode.FAILED);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfo);
        queryWrapper.like(StringUtils.isNotBlank(interfaceDescription),"interface_description",interfaceDescription);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),sortOrder.equals(PageConstant.SORT_ORDER_ASC),sortField);
        Page<InterfaceInfo> result =
            interfaceInfoService.page(new Page<>(currentPage, pageSize), queryWrapper);
        return ResultUtils.success(result);
    }
    //endregion

    //region 发布、下线、调用测试

    /**
     * 发布接口
     * @param idRequest 接口id请求
     * @return 发布接口布尔接口
     */
    @AuthCheck(mustRole = "admin")
    @PostMapping("/online")
    public CommonResult<Boolean> onlineInterfaceInfo(@RequestBody IdRequest idRequest){
        if (idRequest == null){
            throw new ApiException(ResultCode.FAILED);
        }
        Long id = idRequest.getId();
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null){
            throw new ApiException(ResultCode.FAILED);
        }
        User testUser = new User();
        testUser.setUserName("测试");
        String userName = apiClient.getUserByPost(testUser);
        if (StringUtils.isBlank(userName)){
            throw new ApiException(ResultCode.FAILED);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setInterfaceStatus(InterfaceInfoStatusEnum.ONLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 下线接口
     * @param idRequest 接口id请求
     * @return 下线接口布尔结果
     */
    @AuthCheck(mustRole = "admin")
    @PostMapping("/offline")
    public CommonResult<Boolean> offlineInterfaceInfo(@RequestBody IdRequest idRequest){
        if (idRequest == null){
            throw new ApiException(ResultCode.FAILED);
        }
        Long id = idRequest.getId();
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null){
            throw new ApiException(ResultCode.FAILED);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setInterfaceStatus(InterfaceInfoStatusEnum.OFFLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    @PostMapping("/invoke")
    public CommonResult<Object> invokeInterfaceInfo(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvokeRequest,
                                                    HttpServletRequest request){
        if (interfaceInfoInvokeRequest == null || interfaceInfoInvokeRequest.getId() <= 0){
            throw new ApiException(ResultCode.FAILED);
        }
        Long id = interfaceInfoInvokeRequest.getId();
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null || oldInterfaceInfo.getInterfaceStatus() == InterfaceInfoStatusEnum.OFFLINE
            .getValue()){
            throw new ApiException(ResultCode.FAILED);
        }
        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        ApiClient tempClient = new ApiClient(accessKey, secretKey);
        String userRequestParams = interfaceInfoInvokeRequest.getUserRequestParams();
        Gson gson = new Gson();
        User user = gson.fromJson(userRequestParams, User.class);
        String result = tempClient.getUserByPost(user);
        return ResultUtils.success(result);
    }
    //endregion
}
