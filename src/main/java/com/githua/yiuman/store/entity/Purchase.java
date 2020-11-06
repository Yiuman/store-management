package com.githua.yiuman.store.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 采购表（购置、调入、调出）
 *
 * @author yiuman
 * @date 2020/9/29
 */
@Data
@TableName("sm_purchase")
public class Purchase {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long purchaseId;

    /**
     * 进货单号
     */
    private String purchaseNo;

    /**
     * 采购合计
     */
    private BigDecimal total;

    /**
     * 操作时间
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate purchaseDate;

    /**
     * 采购类型/操作类型
     * 0:进货
     * 1:调入
     * 2:调出
     */
    private Integer type;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

}
