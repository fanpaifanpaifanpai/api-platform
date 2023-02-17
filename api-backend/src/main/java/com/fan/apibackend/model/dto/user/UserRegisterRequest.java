package com.fan.apibackend.model.dto.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author 19677
 * 2023年2月2日13点59分
 */
@Data
public class UserRegisterRequest implements Serializable {


    private static final long serialVersionUID = -9117514740063623276L;
    /**
     *
     */
    private String userName;

    /**
     *
     */
    private String userPassword;

    /**
     *
     */
    private String checkPassword;




}
