package com.githua.yiuman.store.dto;

import java.util.Collection;
import java.util.Set;

/**
 * @author yiuman
 * @date 2019-08-07
 */
public interface Chart {

    /**
     * 主键，标记位
     */
    String group();

    /**
     * 标题
     */
    String text();

    /**
     * 子标题
     */
    String subtext();

    /**
     * 指标
     *
     * @return 指标集合
     */
    Set<String> indicators();

    /**
     * X维度
     *
     * @return 维度集合
     */
    Set<String> dimension();

    boolean isShowToolBox();


    /**
     * 数据及
     *
     * @param <E> 数据对象
     * @return 数据集合  外层对应指标，里层对应维度
     */
    <E> Collection<Collection<E>> data();

}