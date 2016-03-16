package com.chsi.knowledge.vo;

import java.util.List;


/**
 * 快捷搜索VO 搜索下拉框展示
 * @author chenjian
 */
public class SearchVO {

    private String systemId;
    private String system;
    private String tags;
    private String title;
    private String summary;
    private String knowId;
    private String keywords;
    private List<String> tagIds;
    private int visitCnt;
    private int sort;
    
    public SearchVO(String systemId, String system, String tags, String title, String summary, String knowId, List<String> tagIds, String keywords, int visitCnt, int sort){
        this.systemId = systemId;
        this.system = system;
        this.tags = tags;
        this.title = title;
        this.summary = summary;
        this.knowId = knowId;
        this.tagIds = tagIds;
        this.keywords = keywords;
        this.visitCnt = visitCnt;
        this.sort = sort;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getKnowId() {
        return knowId;
    }

    public void setKnowId(String knowId) {
        this.knowId = knowId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public List<String> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<String> tagIds) {
        this.tagIds = tagIds;
    }

    public int getVisitCnt() {
        return visitCnt;
    }

    public void setVisitCnt(int visitCnt) {
        this.visitCnt = visitCnt;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    
}
