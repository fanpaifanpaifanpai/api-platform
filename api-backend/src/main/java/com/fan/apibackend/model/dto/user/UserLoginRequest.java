package com.fan.apibackend.model.dto.user;

import java.io.Serializable;
import lombok.Data;

/**
 * @author 19677
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = -9106362664316324429L;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户密码
     */
    private String userPassword;

}
