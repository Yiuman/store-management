package com.githua.yiuman.store.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.githua.yiuman.store.dto.StatisticalDto;
import com.githua.yiuman.store.entity.Expenses;
import com.githua.yiuman.store.entity.Purchase;
import com.githua.yiuman.store.entity.Sale;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * @author yiuman
 * @date 2020/10/27
 */
@Mapper
@Repository
public interface StatisticalMapper {

    @Select("select count(1) from sm_product")
    Long countProducts();

    /**
     * 统计销售额
     *
     * @return 销售额总数
     */
    @Select("select sum(sale_total) from sm_sale ${ew.customSqlSegment}")
    BigDecimal sumSales(@Param(Constants.WRAPPER) Wrapper<Sale> wrapper);

    /**
     * 统计销售单总利润
     *
     * @return 销售单总利润
     */
    @Select("select sum(profits) from sm_sale ${ew.customSqlSegment}")
    BigDecimal sumSalesProfits(@Param(Constants.WRAPPER) Wrapper<Sale> wrapper);


    /**
     * 统计支出总额
     *
     * @return 支出总额
     */
    @Select("select sum(total) from sm_expenses ${ew.customSqlSegment}")
    BigDecimal sumExpenses(@Param(Constants.WRAPPER) Wrapper<Expenses> wrapper);

    @Select("select sum(total) from sm_purchase ${ew.customSqlSegment}")
    BigDecimal sumPurchases(@Param(Constants.WRAPPER) Wrapper<Purchase> wrapper);


    /**
     * 根据 entity 条件，查询全部记录（并翻页）
     *
     * @param page         分页查询条件（可以为 RowBounds.DEFAULT）
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    @Select("select business_date," +
            " ifnull(purchase,0) as purchase," +
            " ifnull(expenses,0) as expenses," +
            " ifnull(sales,0) as sales," +
            " ifnull(profits,0) as profits" +
            " from (select *\n" +
            "from (select se.expenses_date as business_date\n" +
            "      from sm_expenses se\n" +
            "      union\n" +
            "      select sp.purchase_date as business_date\n" +
            "      from sm_purchase sp\n" +
            "      union\n" +
            "      select ss.sale_date as business_date\n" +
            "      from sm_sale ss) date_\n" +
            "       left join (select purchase_date, sum(total) as purchase from sm_purchase group by purchase_date) sp\n" +
            "                 on date_.business_date = sp.purchase_date\n" +
            "       left join (select expenses_date, sum(total) as expenses from sm_expenses group by expenses_date) se\n" +
            "                 on date_.business_date = se.expenses_date\n" +
            "       left join (select sale_date, sum(sale_total) as sales, sum(profits) as profits\n" +
            "                  from sm_sale\n" +
            "                  group by sale_date) ss\n" +
            "                 on date_.business_date = ss.sale_date) temp ${ew.customSqlSegment} ")
    <E extends IPage<StatisticalDto>> E selectPage(E page, @Param(Constants.WRAPPER) Wrapper<StatisticalDto> queryWrapper);
}