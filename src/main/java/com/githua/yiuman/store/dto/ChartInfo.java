package com.githua.yiuman.store.dto;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 图标数据类
 *
 * @author yiuman
 * @date 2019-08-07
 */
public class ChartInfo implements Chart {

    private String key = UUID.randomUUID().toString();

    private String group = "";

    private String type;

    private String text;

    private String subtext;

    private Set<String> indicators;

    private Set<String> dimension;

    private Collection<Collection<?>> data;

    private String slot;

    private final List<Map<String, Object>> dataMap;

    private Map<String, Object> extendData;

    private boolean showToolBox;

    public ChartInfo(List<Map<String, Object>> dataMap) {
        this.dataMap = dataMap;
        this.data = data();
    }

    public ChartInfo(String text, List<Map<String, Object>> dataMap) {
        this.text = text;
        this.dataMap = dataMap;
        this.data = data();
    }

    public ChartInfo(String text, String subtext, List<Map<String, Object>> dataMap) {
        this.text = text;
        this.subtext = subtext;
        this.dataMap = dataMap;
        this.data = data();
    }

    public ChartInfo(String group, String text, String subtext, List<Map<String, Object>> dataMap) {
        this.group = group;
        this.text = text;
        this.subtext = subtext;
        this.dataMap = dataMap;
        this.data = data();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubtext() {
        return subtext;
    }

    public void setSubtext(String subtext) {
        this.subtext = subtext;
    }

    public Set<String> getIndicators() {
        return indicators;
    }

    public void setIndicators(Set<String> indicators) {
        this.indicators = indicators;
    }

    public Set<String> getDimension() {
        return dimension;
    }

    public void setDimension(Set<String> dimension) {
        this.dimension = dimension;
    }

    public Collection<Collection<?>> getData() {
        return CollectionUtils.isEmpty(data) ? data() : data;
    }

    public void setData(Collection<Collection<?>> data) {
        this.data = data;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    @Override
    public String group() {
        return this.group;
    }

    @Override
    public String text() {
        return StringUtils.isEmpty(text) ? "" : text;
    }

    @Override
    public String subtext() {
        return StringUtils.isEmpty(subtext) ? "" : subtext;
    }

    public Map<String, Object> getExtendData() {
        return extendData;
    }

    public void addExtendData(String key, Object value) {
        if (CollectionUtils.isEmpty(extendData)) {
            extendData = new HashMap<>();
        }

        extendData.put(key, value);
    }

    @Override
    public Set<String> indicators() {
        if (CollectionUtils.isEmpty(dataMap)) {
            return new LinkedHashSet<>();
        }
        //默认除了数据集的第一列，都是指标
        if (CollectionUtils.isEmpty(indicators)) {
            indicators = Optional.of(dataMap).get()
                    .get(0)
                    .keySet()
                    .stream()
                    .skip(1)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }
        return indicators;
    }

    @Override
    public Set<String> dimension() {
        //默认第一列的数据为维度
        if (CollectionUtils.isEmpty(dimension)) {
            String theFirstColumn = Optional.of(dataMap).get()
                    .get(0).keySet().stream()
                    .filter(key -> !indicators().contains(key))
                    .findFirst().orElse("");
            dimension = Optional.of(dataMap).get().stream()
                    .map(item
                            -> String.valueOf(item.get(theFirstColumn))).collect(Collectors.toCollection(LinkedHashSet::new));
        }

        return dimension;

    }

    @Override
    public boolean isShowToolBox() {
        return showToolBox;
    }

    public void setShowToolBox(boolean showToolBox) {
        this.showToolBox = showToolBox;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Collection<?>> data() {
        Collection<Collection<?>> realDatas = new ArrayList<>(indicators().size());
        indicators().forEach(key -> {
            Collection<Object> dataList = new ArrayList<>(dimension().size());
            dataMap.forEach(map -> dataList.add(map.get(key)));
            realDatas.add(dataList);
        });

        return realDatas;
    }

}
