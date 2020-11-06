package com.githua.yiuman.store.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.apache.ibatis.type.ClobTypeHandler;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品/产品
 *
 * @author yiuman
 * @date 2020/9/29
 */
@Data
@TableName("sm_product")
public class Product {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long productId;

    /**
     * 产品编码
     */
    private String productNo;

    /**
     * 产品名
     */
    @NotBlank
    private String productName;

    /**
     * 图片，用BASE64存
     */
    @TableField(typeHandler = ClobTypeHandler.class)
    private String photo;

    /**
     * 厂家
     */
    private String manufacturer;

    /**
     * 型号
     */
    private String model;

    /**
     * 规格
     */
    private String standard;

    /**
     * 库存现有量
     */
    private Integer inventory;

    /**
     * 产品的进货单价
     * 若没有默认为0（单价可能会变），已最新进货为准
     */
    private BigDecimal buyPrice;

    /**
     * 产品的销售单价
     */
    private BigDecimal salePrice;

    /**
     * 存放位置
     */
    private String location;

    /**
     * 版本号、乐观锁
     * 每次变更版本号++
     */
    @Version
    private Integer version;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    @Override
    public String toString() {
        return productName + (StringUtils.isEmpty(model) ? "" : String.format("（%s）", model));
    }
}
