package com.githua.yiuman.store.controller;

import com.githua.yiuman.store.dto.ProductPurchaseDto;
import com.githua.yiuman.store.dto.PurchaseDto;
import com.githua.yiuman.store.entity.ProductPurchase;
import com.githua.yiuman.store.service.PurchaseService;
import com.github.yiuman.citrus.support.crud.query.QueryParam;
import com.github.yiuman.citrus.support.crud.rest.BaseCrudController;
import com.github.yiuman.citrus.support.crud.service.CrudService;
import com.github.yiuman.citrus.support.model.Page;
import com.github.yiuman.citrus.support.utils.Buttons;
import com.github.yiuman.citrus.support.utils.LambdaUtils;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 进货Controller
 *
 * @author yiuman
 * @date 2020/9/29
 */
@RestController
@RequestMapping("/rest/purchases")
public class PurchaseController extends BaseCrudController<PurchaseDto, Long> {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
        setParamClass(PurchaseQuery.class);
        addSortBy("purchaseDate", true);
    }

    @Override
    protected CrudService<PurchaseDto, Long> getService() {
        return purchaseService;
    }

    @Override
    protected Page<PurchaseDto> createPage() throws Exception {
        Page<PurchaseDto> page = new Page<>();
        page.addHeader("进货单号", "purchaseNo");
        page.addHeader("进货日期", "purchaseDate").setSortable(true);
        page.addHeader("货品清单", "productNameModel", LambdaUtils.functionWrapper(entity -> {
            List<ProductPurchaseDto> productPurchases = purchaseService.getProductPurchasesByPurchaseId(entity.getPurchaseId());
            entity.setProducts(productPurchases);
            if (CollectionUtils.isEmpty(productPurchases)) {
                return "-";
            }

            return productPurchases.stream().map(ProductPurchase::getProductName).collect(Collectors.joining(","));
        }));
        page.addHeader("合计", "total");

        //添加默认按钮
        page.addButton(Buttons.defaultButtonsWithMore());
        //添加默认行内操作
        page.addActions(Buttons.defaultActions());
        return page;
    }

    @Data
    static class PurchaseQuery {

        @QueryParam
        private String purchaseNo;

        @QueryParam
        private LocalDate purchaseDate;
    }

}
