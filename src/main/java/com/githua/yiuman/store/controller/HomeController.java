package com.githua.yiuman.store.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.githua.yiuman.store.dto.ChartInfo;
import com.githua.yiuman.store.entity.Expenses;
import com.githua.yiuman.store.entity.Purchase;
import com.githua.yiuman.store.entity.Sale;
import com.githua.yiuman.store.mapper.StatisticalMapper;
import com.github.yiuman.citrus.security.authorize.Authorize;
import com.github.yiuman.citrus.support.http.ResponseEntity;
import com.github.yiuman.citrus.system.hook.HasLoginHook;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 商品管理桌面主页
 *
 * @author yiuman
 * @date 2020/10/27
 */
@RestController
@RequestMapping("/rest/store/home")
public class HomeController {

    private final StatisticalMapper statisticalMapper;

    public HomeController(StatisticalMapper statisticalMapper) {
        this.statisticalMapper = statisticalMapper;
    }

    private final static Map<Integer, String> WEEK_ORDINAL_MAP = new HashMap<>(7);

    static {
        WEEK_ORDINAL_MAP.put(0, "周一");
        WEEK_ORDINAL_MAP.put(1, "周二");
        WEEK_ORDINAL_MAP.put(2, "周三");
        WEEK_ORDINAL_MAP.put(3, "周四");
        WEEK_ORDINAL_MAP.put(4, "周五");
        WEEK_ORDINAL_MAP.put(5, "周六");
        WEEK_ORDINAL_MAP.put(6, "周日");
    }

    @Data
    @AllArgsConstructor
    static class HomePanelVo {

        private String icon;

        private String color;

        private String title;

        private String text;
    }

    /**
     * 获取[商品数，总销售额，支出，利润]指标数量
     *
     * @return 主页PanelVo
     */
    @GetMapping("/panels")
    @Authorize(HasLoginHook.class)
    public ResponseEntity<List<HomePanelVo>> getPanels() {
        BigDecimal expenses = Optional
                .ofNullable(statisticalMapper.sumExpenses(Wrappers.emptyWrapper()))
                .orElse(BigDecimal.ZERO);
        BigDecimal profits = Optional.ofNullable(statisticalMapper.sumSalesProfits(Wrappers.emptyWrapper())).orElse(BigDecimal.ZERO);
        String CNY_UNIT = "%s（元）";
        return ResponseEntity.ok(Arrays.asList(
                new HomePanelVo("mdi-dolly", "blue", "商品", String.format("%s（件）", statisticalMapper.countProducts())),
                new HomePanelVo("mdi-cart-arrow-up", "cyan", "总销售额", String.format(CNY_UNIT, statisticalMapper.sumSales(Wrappers.emptyWrapper()))),
                new HomePanelVo("mdi-cash-refund", "red", "支出", String.format(CNY_UNIT, expenses)),
                new HomePanelVo("mdi-currency-cny", "amber", "利润", String.format(CNY_UNIT, profits.subtract(expenses)))
        ));
    }

    /**
     * 获取一周业务量分析，指标[采购额,销售额,支出,利润]
     *
     * @return 线性视图对象
     */
    @GetMapping("/week_analysis")
    @Authorize(HasLoginHook.class)
    public ResponseEntity<ChartInfo> getWeekAnalysis() {
        final List<Map<String, Object>> datas = new ArrayList<>();
        long n = 6L;
        while (n >= 0L) {
            //此处负N 再N--
            LocalDate localDate = LocalDate.now().plusDays(-n--);
            Map<String, Object> data = new LinkedHashMap<>(5);
            data.put("维度", String.format("%s（%s）",
                    WEEK_ORDINAL_MAP.get(localDate.getDayOfWeek().ordinal()),
                    DateTimeFormatter.ISO_DATE.format(localDate)));

            data.put("采购额", Optional
                    .ofNullable(statisticalMapper
                            .sumPurchases(Wrappers.<Purchase>lambdaQuery()
                                    .eq(Purchase::getPurchaseDate, localDate)))
                    .orElse(BigDecimal.ZERO));
            data.put("销售额", Optional
                    .ofNullable(statisticalMapper.sumSales(Wrappers.<Sale>lambdaQuery().eq(Sale::getSaleDate, localDate)))
                    .orElse(BigDecimal.ZERO));

            BigDecimal expenses = Optional
                    .ofNullable(statisticalMapper.sumExpenses(Wrappers.<Expenses>query().eq("expenses_date", localDate)))
                    .orElse(BigDecimal.ZERO);
            BigDecimal profits = Optional
                    .ofNullable(statisticalMapper.sumSalesProfits(Wrappers.<Sale>lambdaQuery().eq(Sale::getSaleDate, localDate)))
                    .orElse(BigDecimal.ZERO);
            data.put("支出", expenses);
            data.put("利润", profits.subtract(expenses));
            datas.add(data);
        }
        return ResponseEntity.ok(new ChartInfo(datas));
    }


}
