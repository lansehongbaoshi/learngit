package com.chsi.knowledge.pojo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

import com.chsi.framework.pojos.PersistentObject;

/**
 * 系统信息表
 * @author chenjian
 * 
 */
@Entity
@Table(name = "SYSTEM")
@DynamicUpdate(value = true)
public class SystemData extends PersistentObject {
    private static final long serialVersionUID = -4617519619519431521L;
    private String id;
    private String name;
    private String description;
    private List<SystemOpenTimeData> list;
    
    private int tagCnt;
    private Integer sort;

    public void setData(PersistentObject persistentObject) {
        SystemData systemData = (SystemData) persistentObject;
        this.id = systemData.getId();
        this.name = systemData.getName();
        this.description = systemData.getDescription();
        this.sort = systemData.getSort();
    }
    
    public SystemData(){
        super();
    }

    public SystemData(String id, String name, String description, int sort) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.sort = sort;
    }
    
    public SystemData(String id, String name, String description) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Id
    @Column(name = "ID")
    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String arg0) {
        this.id = arg0;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "SORT")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Transient
    public int getTagCnt() {
        return tagCnt;
    }

    public void setTagCnt(int tagCnt) {
        this.tagCnt = tagCnt;
    }

    @Transient
    public List<SystemOpenTimeData> getList() {
        return list;
    }

    public void setList(List<SystemOpenTimeData> list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj!=null&&obj.getClass().equals(this.getClass())) {
            SystemData data = (SystemData)obj;
            return this.getId().equals(data.getId());
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }
}
