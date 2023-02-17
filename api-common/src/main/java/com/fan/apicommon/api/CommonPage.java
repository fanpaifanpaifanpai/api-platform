package com.fan.apicommon.api;

import com.fan.apicommon.constants.PageConstant;
import lombok.Data;

/**
 *
 * @author 19677
 */
@Data
public class CommonPage {

    /**
     *页面大小
     */
    private long pageSize = 10;

    /**
     * 当前页面
     */
    private long currentPage = 1;

    /**
     * 牌序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private  String sortOrder = PageConstant.SORT_ORDER_ASC;
}
