package com.githua.yiuman.store.dto;

import com.githua.yiuman.store.entity.Purchase;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 采购相关传输类
 *
 * @author yiuman
 * @date 2020/9/30
 */
@Getter
@Setter
public class PurchaseDto extends Purchase {

    @NotNull
    private List<ProductPurchaseDto> products;
}
