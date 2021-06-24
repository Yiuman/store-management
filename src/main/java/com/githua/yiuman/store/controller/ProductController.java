package com.githua.yiuman.store.controller;

import com.githua.yiuman.store.entity.Product;
import com.githua.yiuman.store.service.ProductService;
import com.github.yiuman.citrus.support.crud.query.annotations.Like;
import com.github.yiuman.citrus.support.crud.rest.BaseCrudController;
import com.github.yiuman.citrus.support.crud.service.CrudService;
import com.github.yiuman.citrus.support.crud.view.impl.DialogView;
import com.github.yiuman.citrus.support.crud.view.impl.PageTableView;
import com.github.yiuman.citrus.support.http.ResponseEntity;
import com.github.yiuman.citrus.support.utils.Buttons;
import com.github.yiuman.citrus.support.widget.Inputs;
import com.github.yiuman.citrus.support.widget.Textarea;
import lombok.Data;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 商品Controller
 *
 * @author yiuman
 * @date 2020/9/29
 */
@RestController
@RequestMapping("/rest/products")
public class ProductController extends BaseCrudController<Product, Long> {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
        setParamClass(ProductQuery.class);
    }

    @Override
    protected CrudService<Product, Long> getService() {
        return productService;
    }

    @Override
    protected Object createView() {
        PageTableView<Product> view = new PageTableView<>();
        view.addHeader("货号", "productNo");
        view.addHeader("品名(+型号)", "productNameModel", product -> {
            String model = product.getModel();
            model = ObjectUtils.isEmpty(model) ? "" : String.format("（%s）", model);
            return product.getProductName() + model;
        });
        view.addHeader("厂家", "manufacturer");
        view.addHeader("数量", "inventory");
        view.addHeader("存放位置", "location");
        //添加默认按钮
        view.addButton(Buttons.defaultButtonsWithMore());
        //添加默认行内操作
        view.addAction(Buttons.defaultActions());

        view.addWidget("货号", "productNo");
        view.addWidget("品名", "productName");
        view.addWidget("厂家", "manufacturer");
        return view;
    }

    @Override
    protected Object createEditableView() {
        DialogView dialogView = new DialogView();
        dialogView.setWidth(800);
        dialogView.addEditField(new Inputs("品名", "productName").placeholder("必填")).addRule("required");
        dialogView.addEditField("型号", "model");
        dialogView.addEditField(new Inputs("货号", "productNo").placeholder("若不填入则自动生成"));
        dialogView.addEditField("厂家", "manufacturer");
        dialogView.addEditField(new Inputs("单价", "buyPrice").type("number").placeholder("默认为0，进货时自动补充"));
        dialogView.addEditField(new Inputs("当前售价", "salePrice").type("number").placeholder("默认为0，出单时自动补充"));
        dialogView.addEditField(new Inputs("库存", "inventory").type("number").placeholder("默认0,进货/出单自动处理"));
        dialogView.addEditField(new Textarea("规格", "standard"));
        dialogView.addEditField(new Textarea("存放位置", "location"));
        return dialogView;
    }

    @Override
    public Long save(Product entity) throws Exception {
        if (Objects.isNull(entity.getProductId()) && ObjectUtils.isEmpty(entity.getProductNo())) {
            entity.setProductNo(UUID.randomUUID().toString());
            entity.setCreatedTime(LocalDateTime.now());
        } else {
            entity.setUpdatedTime(LocalDateTime.now());
        }
        return super.save(entity);
    }

    @GetMapping("/selections")
    public ResponseEntity<List<Product>> getProductSelections() {
        return ResponseEntity.ok(productService.list());
    }

    @Data
    static class ProductQuery {

        @Like
        private String productNo;

        @Like
        private String productName;

        @Like
        private String manufacturer;
    }
}
