package com.githua.yiuman.store.controller;

import com.githua.yiuman.store.dto.ProductPurchaseDto;
import com.githua.yiuman.store.service.ProductPurchaseService;
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
    protected Object createView()  {
        PageTableView<ProductPurchaseDto> view = new PageTableView<>(false);
        view.addHeader("进货日期", "purchaseDate").setSortable(true);
        view.addHeader("进货单号", "purchaseNo");
        view.addHeader("货号", "productNo");
        view.addHeader("品名(+型号)", "productName");
        view.addHeader("厂家", "manufacturer");
        view.addHeader("规格", "standard");
        view.addHeader("进货单价", "price");
        view.addHeader("合计", "total");

        view.addButton(Buttons.exportButton());

        view.addWidget(new DatePicker("进货日期", "purchaseDate"));
        view.addWidget("货号", "productNo");
        view.addWidget("进货单号", "purchaseNo");
        view.addWidget("品名", "productName");

        return view;
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
