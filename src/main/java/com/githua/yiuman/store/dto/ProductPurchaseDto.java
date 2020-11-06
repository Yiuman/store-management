package com.githua.yiuman.store.dto;

import com.githua.yiuman.store.entity.ProductPurchase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 产品采购明细DTO
 *
 * @author yiuman
 * @date 2020/10/22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductPurchaseDto extends ProductPurchase {

    private LocalDate purchaseDate;

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


}
