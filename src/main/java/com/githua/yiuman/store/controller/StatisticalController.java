package com.githua.yiuman.store.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.githua.yiuman.store.dto.StatisticalDto;
import com.githua.yiuman.store.mapper.StatisticalMapper;
import com.github.yiuman.citrus.support.crud.query.QueryParam;
import com.github.yiuman.citrus.support.crud.rest.BaseQueryController;
import com.github.yiuman.citrus.support.model.Header;
import com.github.yiuman.citrus.support.model.Page;
import com.github.yiuman.citrus.support.widget.DatePicker;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * 统计分析
 *
 * @author yiuman
 * @date 2020/11/2
 */
@RestController
@RequestMapping("/rest/stats")
public class StatisticalController extends BaseQueryController<StatisticalDto, String> {

    private final StatisticalMapper statisticalMapper;

    public StatisticalController(StatisticalMapper statisticalMapper) {
        this.statisticalMapper = statisticalMapper;
        setParamClass(StatisticalQuery.class);
        addSortBy("business_date", true);
    }

    @Override
    protected Page<StatisticalDto> createPage() throws Exception {
        Page<StatisticalDto> page = super.createPage();
        page.setItemKey("businessDate");
        page.setHasSelect(false);
        page.addHeader("日期", "businessDate").sortable(true).align(Header.Align.center).width(150);
        page.addHeader("采购额", "purchase").align(Header.Align.center);
        page.addHeader("销售额", "sales").align(Header.Align.center);
        page.addHeader("支出", "expenses").align(Header.Align.center);
        page.addHeader("销售利润", "profits").align(Header.Align.center);
        page.addHeader("净利润（销售利润-支出）",
                "net_profits",
                entity -> entity.getProfits().subtract(entity.getExpenses()))
        .width(200).align(Header.Align.center);
        page.addWidget(new DatePicker("日期","businessDate"));
        return page;
    }

    @Override
    protected Page<StatisticalDto> selectPage(Page<StatisticalDto> page, QueryWrapper<StatisticalDto> queryWrapper) {
        return statisticalMapper.selectPage(page, queryWrapper);
    }

    @Data
    static class StatisticalQuery{

        @QueryParam
        LocalDate businessDate;
    }
}
