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
 * 退货表
 *
 * @author yiuman
 * @date 2020/9/29
 */
@Data
@TableName("sm_returned")
public class Returned {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long returnedId;

    /**
     * 退货原因
     */
    private String returnReason;

    /**
     * 退货日期
     */
    private LocalDate returnedDate;

    /**
     * 关联销售单
     *
     * @see Sale
     */
    private Long saleId;

    /**
     * 销售单号
     */
    private String saleNo;

    /**
     * 销售日期
     */
    private LocalDate saleDate;

    /**
     * 关联的单商品销售记录
     *
     * @see ProductSale
     */
    private Long productSaleId;

    /**
     * 关联商品
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long productId;

    /**
     * 退货数量（单商品退货时有用）
     */
    private Integer amount;

    /**
     * 商品退货单价
     */
    private BigDecimal price;

    /**
     * 合计
     */
    private BigDecimal total;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

}
