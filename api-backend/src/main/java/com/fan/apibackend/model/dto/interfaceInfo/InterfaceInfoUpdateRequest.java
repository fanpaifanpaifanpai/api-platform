package com.fan.apibackend.model.dto.interfaceInfo;

import java.io.Serializable;
import lombok.Data;

/**
 * @author 19677
 */
@Data
public class InterfaceInfoUpdateRequest implements Serializable {
    private static final long serialVersionUID = 5999422310910430792L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 接口描述
     */
    private String interfaceDescription;

    /**
     * 接口地址
     */
    private String interfaceUrl;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;


    /**
     * 请求方法
     */
    private String method;
}
