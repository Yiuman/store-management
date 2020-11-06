package com.githua.yiuman.store.controller;

import com.githua.yiuman.store.dto.ProductPurchaseDto;
import com.githua.yiuman.store.service.ProductPurchaseService;
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
 * 产品采购明细查询
 *
 * @author yiuman
 * @date 2020/10/22
 */
@RestController
@RequestMapping("/rest/purchases_details")
public class PurchaseDetailsController extends BaseQueryController<ProductPurchaseDto, Long> {

    private final ProductPurchaseService productPurchaseService;

    public PurchaseDetailsController(ProductPurchaseService productPurchaseService) {
        this.productPurchaseService = productPurchaseService;
        setParamClass(PurchaseDetailsQuery.class);
    }

    @Override
    protected CrudService<ProductPurchaseDto, Long> getService() {
        return productPurchaseService;
    }

    @Override
    protected Page<ProductPurchaseDto> createPage() throws Exception {
        Page<ProductPurchaseDto> page = super.createPage();
        page.setHasSelect(false);
        page.addHeader("进货日期", "purchaseDate").setSortable(true);
        page.addHeader("进货单号", "purchaseNo");
        page.addHeader("货号", "productNo");
        page.addHeader("品名(+型号)", "productName");
        page.addHeader("厂家", "manufacturer");
        page.addHeader("规格", "standard");
        page.addHeader("进货单价", "price");
        page.addHeader("合计", "total");

        page.addButton(Buttons.exportButton());

        page.addWidget(new DatePicker("进货日期", "purchaseDate"));
        page.addWidget("货号", "productNo");
        page.addWidget("进货单号", "purchaseNo");
        page.addWidget("品名", "productName");

        return page;
    }

    @Data
    static class PurchaseDetailsQuery {

        @QueryParam
        private LocalDate purchaseDate;

        @QueryParam
        private String productNo;

        @QueryParam
        private String purchaseNo;

        @QueryParam(type = "like")
        private String productName;
    }
}
