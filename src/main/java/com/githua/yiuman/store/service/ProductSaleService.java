package com.githua.yiuman.store.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.githua.yiuman.store.dto.ProductSaleDto;
import com.githua.yiuman.store.entity.ProductSale;
import com.githua.yiuman.store.mapper.ProductSaleMapper;
import com.github.yiuman.citrus.support.crud.mapper.CrudMapper;
import com.github.yiuman.citrus.support.crud.service.BaseDtoService;
import org.springframework.stereotype.Service;

/**
 * @author yiuman
 * @date 2020/10/20
 */
@Service
public class ProductSaleService extends BaseDtoService<ProductSale, Long, ProductSaleDto> {

    private final ProductSaleMapper productSaleMapper;

    public ProductSaleService(ProductSaleMapper productSaleMapper) {
        this.productSaleMapper = productSaleMapper;
    }

    @Override
    protected CrudMapper<ProductSale> getBaseMapper() {
        return productSaleMapper;
    }

    @Override
    public <P extends IPage<ProductSaleDto>> P page(P page, Wrapper<ProductSaleDto> queryWrapper) {
        return productSaleMapper.selectDtoPage(page, queryWrapper);
    }
}
