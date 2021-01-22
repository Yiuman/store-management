package com.githua.yiuman.store.controller;

import com.githua.yiuman.store.dto.ProductSaleDto;
import com.githua.yiuman.store.dto.SaleDto;
import com.githua.yiuman.store.entity.ProductSale;
import com.githua.yiuman.store.service.SaleService;
import com.github.yiuman.citrus.support.crud.query.QueryParam;
import com.github.yiuman.citrus.support.crud.rest.BaseCrudController;
import com.github.yiuman.citrus.support.crud.service.CrudService;
import com.github.yiuman.citrus.support.crud.view.impl.PageTableView;
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
    protected Object createView()  {
        PageTableView<SaleDto> view = new PageTableView<>();
        view.addHeader("销售单号", "saleNo");
        view.addHeader("销售日期", "saleDate").setSortable(true);
        view.addHeader("货品清单", "productNameModel", LambdaUtils.functionWrapper(entity -> {
            List<ProductSaleDto> productSales = saleService.getProductSalesBySaleId(entity.getSaleId());
            entity.setProducts(productSales);
            if (CollectionUtils.isEmpty(productSales)) {
                return "-";
            }
            return productSales.stream().map(ProductSale::getProductName).collect(Collectors.joining(","));
        }));
        view.addHeader("成本总价", "costTotal");
        view.addHeader("销售总价", "saleTotal");
        view.addHeader("利润", "profits");
        view.addButton(Buttons.defaultButtonsWithMore());
        view.addAction(Buttons.defaultActions());

        view.addWidget("销售单号", "saleNo");
        view.addWidget(new DatePicker("销售日期", "saleDate"));
        return view;
    }

    @Data
    static class SaleQuery {

        @QueryParam
        private String saleNo;

        @QueryParam
        private LocalDate saleDate;
    }
}
