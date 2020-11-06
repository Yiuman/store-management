package com.githua.yiuman.store.dto;

import com.githua.yiuman.store.entity.ProductSale;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author yiuman
 * @date 2020/9/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductSaleDto extends ProductSale {

    /**
     * 销售人员
     */
    private String seller;

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


    //上部分字段为销售单的
    //下部分字段为产品的

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
