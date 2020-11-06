package com.githua.yiuman.store.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.githua.yiuman.store.dto.ProductSaleDto;
import com.githua.yiuman.store.entity.ProductSale;
import com.github.yiuman.citrus.support.crud.mapper.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author yiuman
 * @date 2020/9/30
 */
@Mapper
@Repository
public interface ProductSaleMapper extends CrudMapper<ProductSale> {

    /**
     * DTO分页查询
     *
     * @param page         分页对象
     * @param queryWrapper 查询条件
     * @return DTO分页对象
     */
    @Select("select *" +
            "from (select ps.*," +
            "             pd.product_no," +
            "             pd.manufacturer," +
            "             pd.inventory," +
            "             pd.model," +
            "             pd.standard," +
            "             pd.location," +
            "             pds.sale_date" +
            "      from sm_product_sale ps" +
            "             left join sm_product pd on ps.product_id = pd.product_id" +
            "             left join sm_sale pds on pds.sale_id = ps.sale_id" +
            ") t ${ew.customSqlSegment} ")
    <P extends IPage<ProductSaleDto>> P selectDtoPage(P page, @Param("ew") Wrapper<ProductSaleDto> queryWrapper);
}