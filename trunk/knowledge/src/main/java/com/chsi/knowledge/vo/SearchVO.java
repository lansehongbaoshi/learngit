package com.chsi.knowledge.vo;

import java.util.List;

import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.SystemData;


/**
 * 快捷搜索VO 搜索下拉框展示
 * @author chenjian
 */
public class SearchVO {

    private String systemId;
    private String system;
    private String systems;
    private String tags;
    private String title;
    private String summary;
    private String contentTxt;//纯文本的内容，不带html标记的
    private String knowId;
    private String keywords;
    private String type;
    private List<String> tagIds;
    private int visitCnt;
    private int sort;
    private long topTime;
    private boolean hasImage;
    
    public SearchVO(KnowledgeData knowl) {
        
    }
    
    public SearchVO(List<SystemData> systemDatas, String knowId, String title, String summary) {
        if(systemDatas!=null) {
            this.systemId = systemDatas.get(0).getId();
            this.system = systemDatas.get(0).getName();
            if(systemDatas.size()>1) {
                this.system+="...";
            }
            this.systems = "";
            for(SystemData systemData:systemDatas) {
                systems+=systemData.getName()+"&nbsp;";
            }
        }
        
        this.title = title;
        this.summary = summary;
        this.knowId = knowId;
    }
    
    public SearchVO(List<SystemData> systemDatas, String tags, String title, String summary, String contentTxt, String knowId,
            List<String> tagIds, String keywords, int visitCnt, int sort, String type, long topTime, boolean hasImage){
        if(systemDatas!=null) {
            this.systemId = systemDatas.get(0).getId();
            this.system = systemDatas.get(0).getName();
            if(systemDatas.size()>1) {
                this.system+="...";
            }
            this.systems = "";
            for(SystemData systemData:systemDatas) {
                systems+=systemData.getName()+"&nbsp;";
            }
        }
        this.tags = tags;
        this.title = title;
        this.summary = summary;
        this.setContentTxt(contentTxt);
        this.knowId = knowId;
        this.tagIds = tagIds;
        this.keywords = keywords;
        this.visitCnt = visitCnt;
        this.sort = sort;
        this.type = type;
        this.topTime = topTime;
        this.hasImage = hasImage;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSystems() {
        return systems;
    }

    public void setSystems(String systems) {
        this.systems = systems;
    }

    public long getTopTime() {
        return topTime;
    }

    public void setTopTime(long topTime) {
        this.topTime = topTime;
    }

    public String getContentTxt() {
        return contentTxt;
    }

    public void setContentTxt(String contentTxt) {
        this.contentTxt = contentTxt;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    
}
