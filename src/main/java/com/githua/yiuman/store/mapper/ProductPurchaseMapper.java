package com.githua.yiuman.store.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.githua.yiuman.store.dto.ProductPurchaseDto;
import com.githua.yiuman.store.entity.ProductPurchase;
import com.github.yiuman.citrus.support.crud.mapper.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author yiuman
 * @date 2020/10/22
 */
@Mapper
@Repository
public interface ProductPurchaseMapper extends CrudMapper<ProductPurchase> {

    /**
     * DTO分页查询
     *
     * @param page         分页对象
     * @param queryWrapper 查询条件
     * @return DTO分页对象
     */
    @Select("select *" +
            "from (select pc.*," +
            "             pd.product_no," +
            "             pd.manufacturer," +
            "             pd.inventory," +
            "             pd.model," +
            "             pd.standard," +
            "             pd.location," +
            "             pr.purchase_date" +
            "      from sm_product_purchase pc" +
            "             left join sm_product pd on pc.product_id = pd.product_id " +
            "             left join sm_purchase pr on pc.purchase_id = pr.purchase_id) t ${ew.customSqlSegment} ")
    <P extends IPage<ProductPurchaseDto>> P selectDtoPage(P page, @Param("ew") Wrapper<ProductPurchaseDto> queryWrapper);
}