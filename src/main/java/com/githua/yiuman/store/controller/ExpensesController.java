package com.githua.yiuman.store.controller;

import com.githua.yiuman.store.entity.Expenses;
import com.githua.yiuman.store.entity.ExpensesType;
import com.githua.yiuman.store.service.ExpensesTypeService;
import com.github.yiuman.citrus.support.crud.query.QueryParam;
import com.github.yiuman.citrus.support.crud.rest.BaseCrudController;
import com.github.yiuman.citrus.support.model.DialogView;
import com.github.yiuman.citrus.support.model.Page;
import com.github.yiuman.citrus.support.utils.Buttons;
import com.github.yiuman.citrus.support.utils.CrudUtils;
import com.github.yiuman.citrus.support.widget.DatePicker;
import com.github.yiuman.citrus.support.widget.Inputs;
import com.github.yiuman.citrus.support.widget.Selects;
import com.github.yiuman.citrus.support.widget.Textarea;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * 支出Controller
 *
 * @author yiuman
 * @date 2020/9/30
 */
@RestController
@RequestMapping("/rest/expenses")
public class ExpensesController extends BaseCrudController<Expenses, Long> {

    private final ExpensesTypeService expensesTypeService;

    public ExpensesController(ExpensesTypeService expensesTypeService) {
        this.expensesTypeService = expensesTypeService;
        setParamClass(ExpensesQuery.class);
    }

    @Override
    protected Page<Expenses> createPage() throws Exception {
        Page<Expenses> page = super.createPage();
        page.addHeader("支出日期", "expensesDate");
        page.addHeader("支出项目", "expensesName", entity -> expensesTypeService.get(entity.getTypeId()).getTypeName());
        page.addHeader("数量", "amount");
        page.addHeader("金额", "price");
        page.addHeader("合计", "total");
        page.addHeader("备注", "remark");
        page.addButton(Buttons.defaultButtonsWithMore());
        page.addActions(Buttons.defaultActions());
        page.addWidget(new DatePicker("支出日期", "expensesDate"));
        return page;
    }

    @Override
    protected DialogView createDialogView() throws Exception {
        DialogView dialogView = new DialogView();
        dialogView.addEditField(new DatePicker("支出日期", "expensesDate")).addRule("required");
        dialogView.addEditField("支出项目", "typeId", CrudUtils.getWidget(this, "getExpensesList")).addRule("required");
        dialogView.addEditField(new Inputs("数量", "amount").type("number").placeholder("默认为1"));
        dialogView.addEditField(new Inputs("金额", "price").type("number")).addRule("required");
        dialogView.addEditField(new Textarea("备注", "remark"));
        return dialogView;
    }

    @Selects(bind = "typeId", key = "typeId", label = "typeName", text = "支出项目")
    public List<ExpensesType> getExpensesList() {
        return expensesTypeService.list();
    }

    @Override
    public Long save(Expenses entity) throws Exception {
        if (Objects.isNull(entity.getAmount())) {
            entity.setAmount(1);
        }
        entity.setTotal(entity.getPrice().multiply(new BigDecimal(entity.getAmount())));
        return super.save(entity);
    }


    @Data
    static class ExpensesQuery {

        @QueryParam
        private Long typeId;

        @QueryParam
        private LocalDate expensesDate;
    }
}
