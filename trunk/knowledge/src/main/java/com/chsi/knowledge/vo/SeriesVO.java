package com.chsi.knowledge.vo;

import java.util.List;

public class SeriesVO {
    private String name;
    private String type;
    private String stack;
    private List<Long> data;
    
    public SeriesVO(String name, List<Long> data) {
        this.name = name;
        this.setData(data);
        this.type = "line";
        this.stack = "";
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    public List<Long> getData() {
        return data;
    }

    public void setData(List<Long> data) {
        this.data = data;
    }
}
