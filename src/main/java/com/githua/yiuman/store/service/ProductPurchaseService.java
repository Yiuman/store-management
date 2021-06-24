package com.githua.yiuman.store.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.githua.yiuman.store.dto.ProductPurchaseDto;
import com.githua.yiuman.store.entity.ProductPurchase;
import com.githua.yiuman.store.mapper.ProductPurchaseMapper;
import com.github.yiuman.citrus.support.crud.mapper.CrudMapper;
import com.github.yiuman.citrus.support.crud.service.BaseDtoService;
import org.springframework.stereotype.Service;

/**
 * @author yiuman
 * @date 2020/10/13
 */
@Service
public class ProductPurchaseService extends BaseDtoService<ProductPurchase, Long, ProductPurchaseDto> {

    private final ProductPurchaseMapper productPurchaseMapper;

    public ProductPurchaseService(ProductPurchaseMapper productPurchaseMapper) {
        this.productPurchaseMapper = productPurchaseMapper;
    }

    @Override
    protected CrudMapper<ProductPurchase> getBaseMapper() {
        return productPurchaseMapper;
    }

    @Override
    public <P extends IPage<ProductPurchaseDto>> P page(P page, Wrapper<ProductPurchaseDto> queryWrapper) {
        return productPurchaseMapper.selectDtoPage(page, queryWrapper);
    }
}
