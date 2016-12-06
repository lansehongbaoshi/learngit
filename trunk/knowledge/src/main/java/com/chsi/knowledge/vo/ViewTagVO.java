package com.chsi.knowledge.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 前台标签VO
 * 
 * @author chsi-pc
 * 
 */
public class ViewTagVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7169012927977286197L;
    private String name;
    private String description;
    private int count;
    private Map<String, String> param;

    public ViewTagVO(String tagId, String systemId, String name, String description, int count) {
        param = new HashMap<String, String>();
        param.put("tagId", tagId);
        param.put("systemId", systemId);
        this.name = name;
        this.description = description;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Map<String, String> getParam() {
        return param;
    }

    public void setParam(Map<String, String> param) {
        this.param = param;
    }

}
