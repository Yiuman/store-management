package com.githua.yiuman.store.controller;

import com.githua.yiuman.store.entity.ExpensesType;
import com.github.yiuman.citrus.support.crud.rest.BaseCrudController;
import com.github.yiuman.citrus.support.model.DialogView;
import com.github.yiuman.citrus.support.model.Page;
import com.github.yiuman.citrus.support.utils.Buttons;
import com.github.yiuman.citrus.support.widget.Inputs;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * 支出类型Controller
 *
 * @author yiuman
 * @date 2020/9/29
 */
@RestController
@RequestMapping("/rest/expenses_type")
public class ExpensesTypeController extends BaseCrudController<ExpensesType, Long> {

    public ExpensesTypeController() {
    }

    @Override
    protected Page<ExpensesType> createPage() throws Exception {
        Page<ExpensesType> page = super.createPage();
        page.addHeader("支出类型", "typeName");
        page.addHeader("支出类型代码", "typeCode");
        page.addButton(Buttons.defaultButtonsWithMore());
        page.addActions(Buttons.defaultActions());
        return page;
    }

    @Override
    protected DialogView createDialogView() throws Exception {
        DialogView dialogView = new DialogView();
        dialogView.addEditField("支出类型", "typeName").addRule("required");
        dialogView.addEditField(new Inputs("支出类型代码", "typeCode").placeholder("默认自动生成"));
        return dialogView;
    }

    @Override
    public Long save(ExpensesType entity) throws Exception {
        if (Objects.isNull(entity.getTypeId())) {
            if (StringUtils.isEmpty(entity.getTypeCode())) {
                entity.setTypeCode(UUID.randomUUID().toString().replace("-", ""));
            }

            entity.setCreatedTime(LocalDateTime.now());
        }
        return super.save(entity);
    }
}
