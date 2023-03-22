package com.fan.apibackend.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 接口信息状态枚举
 * @author 19677
 */

public enum InterfaceInfoStatusEnum {
    /**
     *接口发布
     */
    ONLINE("上线",1),
    /**
     *接口下线
     */
    OFFLINE("关闭",0);

    private String text;

    private int value;

    InterfaceInfoStatusEnum(String text,int value){
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
