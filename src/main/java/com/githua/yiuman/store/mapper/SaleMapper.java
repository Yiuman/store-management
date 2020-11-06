package com.githua.yiuman.store.mapper;

import com.githua.yiuman.store.dto.ProductSaleDto;
import com.githua.yiuman.store.entity.Sale;
import com.github.yiuman.citrus.support.crud.mapper.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yiuman
 * @date 2020/10/20
 */
@Mapper
@Repository
public interface SaleMapper extends CrudMapper<Sale> {

    /**
     * 根据销售单ID查询销售商品信息
     *
     * @param saleId 销售单ID
     * @return 销售商品信息
     */
    @Select("select * from sm_product_sale sps left join sm_product sp on sps.product_id = sp.product_id" +
            " where sps.sale_id = #{sale_id}")
    List<ProductSaleDto> getProductSalesBySaleId(Long saleId);
}