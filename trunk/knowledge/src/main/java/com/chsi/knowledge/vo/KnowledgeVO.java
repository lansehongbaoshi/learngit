package com.chsi.knowledge.vo;

import java.io.Serializable;
import java.util.List;

import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.SystemData;


/**
 * 用于前台展示的知识vo
 * @author zhangzh
 */
public class KnowledgeVO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 5269656089513977038L;
    private String systemId;
    private String system;
    private String systems;
    private String tags;
    private String title;
    private String content;
    private String knowId;
    private String keywords;
    private String type;
    private String updateTime;
    private List<String> tagIds;
    private int visitCnt;
    private int sort;
    
    public KnowledgeVO(KnowledgeData knowl) {
        
    }
    
    public KnowledgeVO(List<SystemData> systemDatas, String knowId, String title, String content) {
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
        this.setContent(content);
        this.knowId = knowId;
    }
    
    public KnowledgeVO(List<SystemData> systemDatas, String tags, String title, String content, String knowId,
            List<String> tagIds, String keywords, int visitCnt, int sort, String type){
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
        this.setContent(content);
        this.knowId = knowId;
        this.tagIds = tagIds;
        this.keywords = keywords;
        this.visitCnt = visitCnt;
        this.sort = sort;
        this.type = type;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    
}
