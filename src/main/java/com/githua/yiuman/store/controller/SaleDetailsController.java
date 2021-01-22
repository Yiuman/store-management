package com.githua.yiuman.store.controller;

import com.githua.yiuman.store.dto.ProductSaleDto;
import com.githua.yiuman.store.service.ProductSaleService;
import com.github.yiuman.citrus.support.crud.query.QueryParam;
import com.github.yiuman.citrus.support.crud.rest.BaseQueryController;
import com.github.yiuman.citrus.support.crud.service.CrudService;
import com.github.yiuman.citrus.support.crud.view.impl.PageTableView;
import com.github.yiuman.citrus.support.utils.Buttons;
import com.github.yiuman.citrus.support.widget.DatePicker;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * 产品销售明细查询
 *
 * @author yiuman
 * @date 2020/10/22
 */
@RestController
@RequestMapping("/rest/sale_details")
public class SaleDetailsController extends BaseQueryController<ProductSaleDto, Long> {

    private final ProductSaleService productSaleService;

    public SaleDetailsController(ProductSaleService productSaleService) {
        this.productSaleService = productSaleService;
        setParamClass(SaleDetailsQuery.class);
    }

    @Override
    protected CrudService<ProductSaleDto, Long> getService() {
        return productSaleService;
    }

    @Override
    protected Object createView() {
        PageTableView<ProductSaleDto> view = new PageTableView<>(false);
        view.addHeader("销售日期", "saleDate").setSortable(true);
        view.addHeader("销售单号", "saleNo");
        view.addHeader("货号", "productNo");
        view.addHeader("品名(+型号)", "productName");
        view.addHeader("厂家", "manufacturer");
        view.addHeader("规格", "standard");
        view.addHeader("成本单价", "costPrice");
        view.addHeader("成本总价", "costTotal");
        view.addHeader("销售单价", "salePrice");
        view.addHeader("销售总价", "saleTotal");

        view.addButton(Buttons.exportButton());

        view.addWidget(new DatePicker("销售日期", "saleDate"));
        view.addWidget("货号", "productNo");
        view.addWidget("销售单号", "saleNo");
        view.addWidget("品名", "productName");
        return view;
    }

    @Data
    static class SaleDetailsQuery {

        @QueryParam
        private LocalDate saleDate;

        @QueryParam
        private String productNo;

        @QueryParam
        private String saleNo;

        @QueryParam(type = "like")
        private String productName;
    }

}
