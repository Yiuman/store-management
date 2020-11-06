package com.githua.yiuman.store.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.githua.yiuman.store.entity.Product;
import com.github.yiuman.citrus.support.crud.service.BaseService;
import com.github.yiuman.citrus.support.utils.CrudUtils;
import com.github.yiuman.citrus.support.widget.Selections;
import com.github.yiuman.citrus.support.widget.Selects;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 商品/产品，逻辑层
 *
 * @author yiuman
 * @date 2020/9/29
 */
@Service
public class ProductService extends BaseService<Product, Long> {

    public ProductService() {
    }

    public Product getProductByNo(String productNo) {
        return getMapper().selectOne(Wrappers.<Product>lambdaQuery().eq(Product::getProductNo, productNo));
    }

    public Selections getProductSelections() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return CrudUtils.getWidget(this, getClass().getMethod("getProductList"));
    }

    @Selects(bind = "productId", key = "productId", text = "商品")
    public List<Product> getProductList() {
        return list();
    }

}
