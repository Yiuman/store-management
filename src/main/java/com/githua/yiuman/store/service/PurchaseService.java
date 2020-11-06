package com.githua.yiuman.store.service;

import com.githua.yiuman.store.dto.ProductPurchaseDto;
import com.githua.yiuman.store.dto.PurchaseDto;
import com.githua.yiuman.store.entity.Product;
import com.githua.yiuman.store.entity.Purchase;
import com.githua.yiuman.store.mapper.PurchaseMapper;
import com.github.yiuman.citrus.support.crud.service.BaseDtoService;
import com.github.yiuman.citrus.support.utils.LambdaUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 采购service
 *
 * @author yiuman
 * @date 2020/9/30
 */
@Service
public class PurchaseService extends BaseDtoService<Purchase, Long, PurchaseDto> {

    private final PurchaseMapper purchaseMapper;

    private final ProductService productService;

    private final ProductPurchaseService productPurchaseService;

    public PurchaseService(PurchaseMapper purchaseMapper, ProductService productService, ProductPurchaseService productPurchaseService) {
        this.purchaseMapper = purchaseMapper;
        this.productService = productService;
        this.productPurchaseService = productPurchaseService;
    }

    @Override
    public boolean beforeSave(PurchaseDto entity) throws Exception {
        //算出总数
        final AtomicReference<BigDecimal> total = new AtomicReference<>(new BigDecimal(0));
        entity.getProducts().forEach(item -> {
            BigDecimal productTotal = item.getPrice().multiply(new BigDecimal(item.getAmount()));
            item.setTotal(productTotal);
            total.set(total.get().add(productTotal));
        });
        entity.setTotal(total.get());

        if (StringUtils.isEmpty(entity.getPurchaseNo())) {
            entity.setPurchaseNo(UUID.randomUUID().toString());
        }

        return true;
    }

    /**
     * 进货后，自动增加产品库存、更新产品进货价
     *
     * @param entity 进货实体
     * @throws Exception 数据库操作异常
     */
    @Override
    public void afterSave(PurchaseDto entity) throws Exception {
        entity.getProducts().forEach(LambdaUtils.consumerWrapper(productPurchase -> {
            Product product = productService.get(productPurchase.getProductId());
            product.setUpdatedTime(LocalDateTime.now());
            product.setBuyPrice(productPurchase.getPrice());
            Integer inventory = product.getInventory();
            product.setInventory(inventory + productPurchase.getAmount());
            productService.save(product);
            productPurchase.setProductName(product.toString());
            productPurchase.setPurchaseId(entity.getPurchaseId());
            productPurchase.setPurchaseNo(entity.getPurchaseNo());
            productPurchaseService.save(productPurchase);
        }));
    }

    public List<ProductPurchaseDto> getProductPurchasesByPurchaseId(Long purchaseId) throws Exception {
        return purchaseMapper.getProductPurchasesByPurchaseId(purchaseId);
    }
}
