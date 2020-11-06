package com.githua.yiuman.store.controller;

import com.githua.yiuman.store.dto.ProductSaleDto;
import com.githua.yiuman.store.service.ProductSaleService;
import com.github.yiuman.citrus.support.crud.query.QueryParam;
import com.github.yiuman.citrus.support.crud.rest.BaseQueryController;
import com.github.yiuman.citrus.support.crud.service.CrudService;
import com.github.yiuman.citrus.support.model.Page;
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
    protected Page<ProductSaleDto> createPage() throws Exception {
        Page<ProductSaleDto> page = super.createPage();
        page.setHasSelect(false);
        page.addHeader("销售日期", "saleDate").setSortable(true);
        page.addHeader("销售单号", "saleNo");
        page.addHeader("货号", "productNo");
        page.addHeader("品名(+型号)", "productName");
        page.addHeader("厂家", "manufacturer");
        page.addHeader("规格", "standard");
        page.addHeader("成本单价", "costPrice");
        page.addHeader("成本总价", "costTotal");
        page.addHeader("销售单价", "salePrice");
        page.addHeader("销售总价", "saleTotal");

        page.addButton(Buttons.exportButton());

        page.addWidget(new DatePicker("销售日期", "saleDate"));
        page.addWidget("货号", "productNo");
        page.addWidget("销售单号", "saleNo");
        page.addWidget("品名", "productName");
        return page;
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
