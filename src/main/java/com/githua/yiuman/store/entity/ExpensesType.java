package com.githua.yiuman.store.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 支出类型
 *
 * @author yiuman
 * @date 2020/9/29
 */
@Data
@TableName("sm_expenses_type")
public class ExpensesType {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long typeId;

    /**
     * 类型名
     */
    private String typeName;

    /**
     * 类型代码
     */
    private String typeCode;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
}
