package com.githua.yiuman.store.dto;

import com.githua.yiuman.store.entity.Sale;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 销售传输类
 *
 * @author yiuman
 * @date 2020/10/20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SaleDto extends Sale {

    @NotNull
    private List<ProductSaleDto> products;

}
