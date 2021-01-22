package com.githua.yiuman.store.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.githua.yiuman.store.dto.StatisticalDto;
import com.githua.yiuman.store.mapper.StatisticalMapper;
import com.github.yiuman.citrus.support.crud.query.QueryParam;
import com.github.yiuman.citrus.support.crud.rest.BaseQueryController;
import com.github.yiuman.citrus.support.crud.view.impl.PageTableView;
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
    protected Object createView() {
        PageTableView<StatisticalDto> view = new PageTableView<>(false);
        view.addHeader(
                Header.builder()
                        .text("日期")
                        .value("businessDate")
                        .sortable(true)
                        .width(150)
                        .align(Header.Align.center)
                        .build()
        );

        view.addHeader(Header.builder().text("采购额").value("purchase").align(Header.Align.center).build());
        view.addHeader("销售额", "sales").toBuilder().align(Header.Align.center);
        view.addHeader("支出", "expenses").toBuilder().align(Header.Align.center);
        view.addHeader("销售利润", "profits").toBuilder().align(Header.Align.center);
        view.addHeader("净利润（销售利润-支出）",
                "net_profits",
                entity -> entity.getProfits().subtract(entity.getExpenses())).toBuilder().width(200).align(Header.Align.center);
        view.addWidget(new DatePicker("日期", "businessDate"));
        return view;
    }

    @Override
    protected Page<StatisticalDto> selectPage(Page<StatisticalDto> view, QueryWrapper<StatisticalDto> queryWrapper) {
        Page<StatisticalDto> statisticalDtoPage = statisticalMapper.selectPage(view, queryWrapper);
        statisticalDtoPage.setItemKey("businessDate");
        return statisticalDtoPage;
    }

    @Data
    static class StatisticalQuery {

        @QueryParam
        LocalDate businessDate;
    }
}
