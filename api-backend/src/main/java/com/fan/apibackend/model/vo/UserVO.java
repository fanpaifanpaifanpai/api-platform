package com.fan.apibackend.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author 19677
 */
@Data
public class UserVO implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     *
     */
    private String userName;



    /**
     *
     */
    private String userAvatar;

    /**
     *
     */
    private Integer userRole;


    /**
     *
     */
    private Integer userGender;

    /**
     *
     */
    private Date updateTime;

    /**
     *
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
