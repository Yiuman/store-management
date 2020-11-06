package com.githua.yiuman.store.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品销售
 *
 * @author yiuman
 * @date 2020/9/29
 */
@Data
@TableName("sm_product_sale")
public class ProductSale {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 关联销售ID
     *
     * @see Sale
     */
    private Long saleId;

    /**
     * 销售单号
     */
    private String saleNo;

    /**
     * 关联的产品
     *
     * @see Product
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long productId;

    /**
     * 冗余、产品名称+型号
     */
    private String productName;

    @TableField(exist = false)
    private String productNo;

    @TableField(exist = false)
    private Integer inventory;

    /**
     * 销售数量
     */
    private Integer amount;

    /**
     * 成本单价
     */
    private BigDecimal costPrice;

    /**
     * 销售价
     */
    private BigDecimal salePrice;

    /**
     * 成本合计
     */
    private BigDecimal costTotal;

    /**
     * 销售合计
     */
    private BigDecimal saleTotal;

    /**
     * 利润
     */
    private BigDecimal profits;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

}
