package com.fan.apibackend.model.dto.interfaceInfo;

import java.io.Serializable;
import lombok.Data;

/**
 * @author 19677
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {

    private static final long serialVersionUID = 6022189618447952778L;

    /**
     * id
     */
    private Long id;

    /**
     * 请求参数
     */
    private String userRequestParams;


}
