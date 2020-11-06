package com.githua.yiuman.store.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 业务统计传输类
 *
 * @author yiuman
 * @date 2020/11/2
 */
@Data
public class StatisticalDto {

    /**
     * 日期
     */
    private String businessDate;

    /**
     * 采购
     */
    private BigDecimal purchase;

    /**
     * 销售
     */
    private BigDecimal sales;

    /**
     * 支出
     */
    private BigDecimal expenses;

    /**
     * 利润
     */
    private BigDecimal profits;

}
