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
 * 销售表
 * 此表记录当次销售的通用信息,销售商品的明细信息见"销售商品表"
 *
 * @author yiuman
 * @date 2020/9/29
 * @see ProductSale
 */
@Data
@TableName("sm_sale")
public class Sale {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long saleId;

    /**
     * 销售编码
     */
    private String saleNo;

    /**
     * 当次销售的金额总数
     */
    private BigDecimal saleTotal;

    /**
     * 当次销售的成本总数
     */
    private BigDecimal costTotal;

    /**
     * 当次销售的利润
     */
    private BigDecimal profits;

    /**
     * 销售人ID，若为本系统人员则有
     */
    private Long sellId;

    /**
     * 若非本系统人员主动填入
     */
    private String sellName;

    /**
     * 售货地址
     */
    private String address;

    /**
     * 客户的联系方式信息
     */
    private String contactInfo;

    /**
     * 销售日期
     */
    private LocalDate saleDate;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

}
