package com.githua.yiuman.store.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 支出表
 *
 * @author yiuman
 * @date 2020/9/29
 */
@Data
@TableName("sm_expenses")
public class Expenses {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long expensesId;

    /**
     * 数量
     */
    private Integer amount;

    /**
     * 单价
     */
    private BigDecimal price;


    /**
     * 支出类型ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long typeId;

    /**
     * 合计
     */
    private BigDecimal total;

    /**
     * 支出日期
     */
    private LocalDate expensesDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

}
