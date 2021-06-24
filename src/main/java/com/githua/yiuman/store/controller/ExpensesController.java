package com.githua.yiuman.store.controller;

import com.githua.yiuman.store.entity.Expenses;
import com.githua.yiuman.store.entity.ExpensesType;
import com.githua.yiuman.store.service.ExpensesTypeService;
import com.github.yiuman.citrus.support.crud.query.annotations.Equals;
import com.github.yiuman.citrus.support.crud.rest.BaseCrudController;
import com.github.yiuman.citrus.support.crud.view.impl.DialogView;
import com.github.yiuman.citrus.support.crud.view.impl.PageTableView;
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
    protected Object createView() {
        PageTableView<Expenses> view = new PageTableView<>();
        view.addHeader("支出日期", "expensesDate");
        view.addHeader("支出项目", "expensesName", entity -> expensesTypeService.get(entity.getTypeId()).getTypeName());
        view.addHeader("数量", "amount");
        view.addHeader("金额", "price");
        view.addHeader("合计", "total");
        view.addHeader("备注", "remark");
        view.addButton(Buttons.defaultButtonsWithMore());
        view.addAction(Buttons.defaultActions());
        view.addWidget(new DatePicker("支出日期", "expensesDate"));
        return view;
    }

    @Override
    protected Object createEditableView() throws Exception {
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

        @Equals
        private Long typeId;

        @Equals
        private LocalDate expensesDate;
    }
}
