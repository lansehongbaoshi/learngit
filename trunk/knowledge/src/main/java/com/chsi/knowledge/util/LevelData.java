package com.chsi.knowledge.util;

import java.util.Map;
/**
 * 前台页面层级展示类
 * @author chenjian
 */
public class LevelData {

    private int level;
    private String name;
    private String description;
    private Map<String, String> param;
    
    public LevelData(int level, String name, String description, Map<String, String> param) {
        this.level = level;
        this.name = name;
        this.description = description;
        this.param = param;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public Map<String, String> getParam() {
        return param;
    }

    public void setParam(Map<String, String> param) {
        this.param = param;
    }

}
