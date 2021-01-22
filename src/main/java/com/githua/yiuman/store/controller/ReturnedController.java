package com.githua.yiuman.store.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.githua.yiuman.store.dto.ProductSaleDto;
import com.githua.yiuman.store.dto.SaleDto;
import com.githua.yiuman.store.entity.ProductSale;
import com.githua.yiuman.store.entity.Returned;
import com.githua.yiuman.store.entity.Sale;
import com.githua.yiuman.store.service.ProductService;
import com.githua.yiuman.store.service.SaleService;
import com.github.yiuman.citrus.support.crud.rest.BaseCrudController;
import com.github.yiuman.citrus.support.crud.view.impl.DialogView;
import com.github.yiuman.citrus.support.crud.view.impl.PageTableView;
import com.github.yiuman.citrus.support.model.Header;
import com.github.yiuman.citrus.support.utils.Buttons;
import com.github.yiuman.citrus.support.widget.DatePicker;
import com.github.yiuman.citrus.support.widget.Inputs;
import com.github.yiuman.citrus.support.widget.Textarea;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 退货管理
 *
 * @author yiuman
 * @date 2020/10/28
 */
@RestController
@RequestMapping("/rest/returned")
public class ReturnedController extends BaseCrudController<Returned, Long> {

    private final ProductService productService;

    private final SaleService saleService;

    public ReturnedController(ProductService productService, SaleService saleService) {
        this.productService = productService;
        this.saleService = saleService;
    }

    @Override
    protected Object createView() {
        PageTableView<Returned> view = new PageTableView<>();
        view.addHeader(
                Header.builder()
                        .text("退货日期")
                        .value("returnedDate")
                        .width(120)
                        .align(Header.Align.center)
                        .build()
        );
        view.addHeader("销售单号", "saleNo_", entity ->
                StringUtils.isEmpty(entity.getSaleNo()) ? "-" : entity.getSaleNo())
                .setAlign(Header.Align.center);
        view.addHeader("退货数量", "amount");
        view.addHeader("退货单价", "price");
        view.addHeader("退还金额", "total");
        view.addHeader("原因", "returnReason");

        view.addButton(Buttons.defaultButtonsWithMore());
        view.addAction(Buttons.defaultActions());
        view.addWidget(new DatePicker("退货日期", "returnedDate"));
        view.addWidget("销售单号", "saleNo");
        return view;
    }

    @Override
    protected Object createEditableView() throws Exception {
        DialogView view = new DialogView();
        view.addEditField(new DatePicker("退货日期", "returnedDate"));
        view.addEditField("销售单号", "saleNo");
        view.addEditField("商品", "productId", productService.getProductSelections()).addRule("required");
        view.addEditField(new Inputs("退货数量", "amount").type("number")).addRule("required");
        view.addEditField(new Inputs("退货单价", "price").type("number")).addRule("required");
        view.addEditField(new Textarea("原因", "returnReason")).addRule("required");
        return view;
    }

    @Override
    public Long save(Returned entity) throws Exception {
        if (Objects.isNull(entity.getCreatedTime())) {
            entity.setCreatedTime(LocalDateTime.now());
        }

        entity.setTotal(entity.getPrice().multiply(BigDecimal.valueOf(entity.getAmount())));
        if (!StringUtils.isEmpty(entity.getSaleNo())) {
            //关联销售单
            Sale sale = saleService.get(Wrappers.<SaleDto>lambdaQuery().eq(Sale::getSaleNo, entity.getSaleNo()));
            if (Objects.nonNull(sale)) {
                entity.setSaleId(sale.getSaleId());
                entity.setSaleDate(sale.getSaleDate());
                //关联销售单商品记录
                ProductSaleDto productSaleDto = saleService.getProductSaleService().get(Wrappers.<ProductSaleDto>lambdaQuery()
                        .eq(ProductSale::getSaleId, sale.getSaleId())
                        .eq(ProductSale::getProductId, entity.getProductId()));
                if (Objects.nonNull(productSaleDto)) {
                    entity.setProductSaleId(productSaleDto.getId());
                }
            }

        }
        return super.save(entity);
    }
}
