package com.githua.yiuman.store.service;

import com.githua.yiuman.store.dto.ProductSaleDto;
import com.githua.yiuman.store.dto.SaleDto;
import com.githua.yiuman.store.entity.Product;
import com.githua.yiuman.store.entity.Sale;
import com.githua.yiuman.store.mapper.SaleMapper;
import com.github.yiuman.citrus.support.crud.mapper.CrudMapper;
import com.github.yiuman.citrus.support.crud.service.BaseDtoService;
import com.github.yiuman.citrus.support.utils.LambdaUtils;
import com.github.yiuman.citrus.system.service.UserService;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 销售单Service
 *
 * @author yiuman
 * @date 2020/9/30
 */
@Service
public class SaleService extends BaseDtoService<Sale, Long, SaleDto> {

    private final SaleMapper saleMapper;

    private final UserService userService;

    private final ProductService productService;

    @Getter
    private final ProductSaleService productSaleService;

    public SaleService(SaleMapper saleMapper, UserService userService, ProductService productService, ProductSaleService productSaleService) {
        this.saleMapper = saleMapper;
        this.userService = userService;
        this.productService = productService;
        this.productSaleService = productSaleService;
    }

    @Override
    protected CrudMapper<Sale> getBaseMapper() {
        return saleMapper;
    }

    @Override
    public boolean beforeSave(SaleDto entity) {
        if (Objects.isNull(entity.getSaleId())) {
            entity.setSellId(userService.getCurrentUserOnlineInfo().getUserId());
            entity.setCreatedTime(LocalDateTime.now());
        }

        final AtomicReference<BigDecimal> saleTotal = new AtomicReference<>(new BigDecimal(0));
        final AtomicReference<BigDecimal> costTotal = new AtomicReference<>(new BigDecimal(0));
        entity.getProducts().forEach(item -> {
            //处理销售价、销售总价
            BigDecimal productSaleTotal = item.getSalePrice().multiply(new BigDecimal(item.getAmount()));
            item.setSaleTotal(productSaleTotal);
            saleTotal.set(saleTotal.get().add(productSaleTotal));

            //处理成本价、成本总价
            BigDecimal productCostTotal = item.getCostPrice().multiply(new BigDecimal(item.getAmount()));
            item.setCostTotal(productCostTotal);
            costTotal.set(costTotal.get().add(productCostTotal));

        });
        entity.setCostTotal(costTotal.get());
        entity.setSaleTotal(saleTotal.get());
        //销售总价-成本总价 = 利润
        entity.setProfits(entity.getSaleTotal().subtract(entity.getCostTotal()));
        return true;
    }

    @Override
    public void afterSave(SaleDto entity) {
        entity.getProducts().forEach(LambdaUtils.consumerWrapper(productSale -> {
            Product product = productService.get(productSale.getProductId());
            product.setUpdatedTime(LocalDateTime.now());
            Integer inventory = product.getInventory();
            product.setInventory(inventory - productSale.getAmount());
            productService.save(product);

            productSale.setProductName(product.toString());
            productSale.setSaleId(entity.getSaleId());
            productSale.setSaleNo(entity.getSaleNo());
            productSale.setProfits(productSale.getSaleTotal().subtract(productSale.getCostTotal()));
            productSaleService.save(productSale);
        }));
    }

    public List<ProductSaleDto> getProductSalesBySaleId(Long saleId) {
        return saleMapper.getProductSalesBySaleId(saleId);
    }

}
