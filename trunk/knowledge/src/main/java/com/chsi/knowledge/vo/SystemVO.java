package com.chsi.knowledge.vo;

import java.io.Serializable;
import java.util.List;

import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.pojo.SystemOpenTimeData;
public class SystemVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2180556470935241724L;
 
    private String id;
    private String name;
    private String description;
    private List<SystemOpenTimeData> list;
    
    private int tagCnt;
    private Integer sort;
    private int knowPrivateCnt;
    private int knowPublicCnt;
    
    public SystemVO(){
        super();
    }
    
    public SystemVO(SystemData systemData){
        this.id = systemData.getId();
        this.name = systemData.getName();
        this.description = systemData.getDescription();
        this.list = systemData.getList();
        this.tagCnt = systemData.getTagCnt();
        this.sort = systemData.getSort();
    }
    
    

    public SystemVO(String id, String name, String description,
            List<SystemOpenTimeData> list, int tagCnt, Integer sort) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.list = list;
        this.tagCnt = tagCnt;
        this.sort = sort;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
    public List<SystemOpenTimeData> getList() {
        return list;
    }
    public void setList(List<SystemOpenTimeData> list) {
        this.list = list;
    }
    public int getTagCnt() {
        return tagCnt;
    }
    public void setTagCnt(int tagCnt) {
        this.tagCnt = tagCnt;
    }
    public Integer getSort() {
        return sort;
    }
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    public int getKnowPrivateCnt() {
        return knowPrivateCnt;
    }
    public void setKnowPrivateCnt(int knowPrivateCnt) {
        this.knowPrivateCnt = knowPrivateCnt;
    }
    public int getKnowPublicCnt() {
        return knowPublicCnt;
    }
    public void setKnowPublicCnt(int knowPublicCnt) {
        this.knowPublicCnt = knowPublicCnt;
    }
}
