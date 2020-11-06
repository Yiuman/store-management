package com.githua.yiuman.store.mapper;

import com.githua.yiuman.store.dto.ProductPurchaseDto;
import com.githua.yiuman.store.entity.Product;
import com.githua.yiuman.store.entity.Purchase;
import com.github.yiuman.citrus.support.crud.mapper.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yiuman
 * @date 2020/9/29
 */
@Mapper
@Repository
public interface PurchaseMapper extends CrudMapper<Purchase> {

    @Select("select sp.* from sm_product sp ,sm_product_purchase spp where sp.product_id = spp.product_id and spp.purchase_id = #{purchaseId}")
    List<Product> getProductsByPurchaseId(Long purchaseId) throws Exception;

    @Select("select spp.* from sm_product_purchase spp where spp.purchase_id = #{purchaseId}")
    List<ProductPurchaseDto> getProductPurchasesByPurchaseId(Long purchaseId);
}