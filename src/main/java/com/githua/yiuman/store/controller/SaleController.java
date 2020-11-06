package com.githua.yiuman.store.controller;

import com.githua.yiuman.store.dto.ProductSaleDto;
import com.githua.yiuman.store.dto.SaleDto;
import com.githua.yiuman.store.entity.ProductSale;
import com.githua.yiuman.store.service.SaleService;
import com.github.yiuman.citrus.support.crud.query.QueryParam;
import com.github.yiuman.citrus.support.crud.rest.BaseCrudController;
import com.github.yiuman.citrus.support.crud.service.CrudService;
import com.github.yiuman.citrus.support.model.Page;
import com.github.yiuman.citrus.support.utils.Buttons;
import com.github.yiuman.citrus.support.utils.LambdaUtils;
import com.github.yiuman.citrus.support.widget.DatePicker;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 销售管理控制器
 *
 * @author yiuman
 * @date 2020/10/20
 */
@RestController
@RequestMapping("/rest/sales")
public class SaleController extends BaseCrudController<SaleDto, Long> {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
        setParamClass(SaleQuery.class);
        addSortBy("saleDate", true);
    }

    @Override
    protected CrudService<SaleDto, Long> getService() {
        return saleService;
    }

    @Override
    protected Page<SaleDto> createPage() throws Exception {
        Page<SaleDto> page = new Page<>();
        page.addHeader("销售单号", "saleNo");
        page.addHeader("销售日期", "saleDate").setSortable(true);
        page.addHeader("货品清单", "productNameModel", LambdaUtils.functionWrapper(entity -> {
            List<ProductSaleDto> productSales = saleService.getProductSalesBySaleId(entity.getSaleId());
            entity.setProducts(productSales);
            if (CollectionUtils.isEmpty(productSales)) {
                return "-";
            }
            return productSales.stream().map(ProductSale::getProductName).collect(Collectors.joining(","));
        }));
        page.addHeader("成本总价", "costTotal");
        page.addHeader("销售总价", "saleTotal");
        page.addHeader("利润", "profits");
        page.addButton(Buttons.defaultButtonsWithMore());
        page.addActions(Buttons.defaultActions());

        page.addWidget("销售单号", "saleNo");
        page.addWidget(new DatePicker("销售日期", "saleDate"));
        return page;
    }

    @Data
    static class SaleQuery {

        @QueryParam
        private String saleNo;

        @QueryParam
        private LocalDate saleDate;
    }
}
