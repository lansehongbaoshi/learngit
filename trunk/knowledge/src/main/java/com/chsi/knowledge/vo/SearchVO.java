package com.chsi.knowledge.vo;

import java.util.List;


/**
 * 快捷搜索VO 搜索下拉框展示
 * @author chenjian
 */
public class SearchVO {

    private String title;
    private String summary;
    private String knowId;
    private String keywords;
    private List<String> tagIds;
    private int visitCnt;
    
    public SearchVO(String title, String summary, String knowId, List<String> tagIds, String keywords, int visitCnt){
        this.title = title;
        this.summary = summary;
        this.knowId = knowId;
        this.tagIds = tagIds;
        this.keywords = keywords;
        this.visitCnt = visitCnt;
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

    
}
