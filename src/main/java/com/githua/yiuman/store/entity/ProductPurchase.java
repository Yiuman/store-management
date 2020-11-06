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
 * 商品采购表
 *
 * @author yiuman
 * @date 2020/10/13
 */
@Data
@TableName("sm_product_purchase")
public class ProductPurchase {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 关联销售ID
     *
     * @see Purchase
     */
    private Long purchaseId;

    /**
     * 销售单号
     */
    private String purchaseNo;

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
     * 数量
     */
    private Integer amount;

    /**
     * 单价
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
