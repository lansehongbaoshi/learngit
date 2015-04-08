package com.chsi.knowledge.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.chsi.framework.pojos.PersistentObject;

/**
 * 标签表信息
 * @author chenjian
 */
@Entity
@Table(name = "TAG")
@DynamicUpdate(value = true)
public class TagData extends PersistentObject {

    private static final long serialVersionUID = -5725482424563596939L;
    private String id;
    private SystemData systemData;
    private String name;
    private String description;
    private int sort;

    public void setData(PersistentObject persistentObject) {
        TagData tagData = (TagData) persistentObject;
        this.id = tagData.getId();
        this.systemData = tagData.getSystemData();
        this.name = tagData.getName();
        this.description = tagData.getDescription();
        this.sort = tagData.getSort();
    }

    public TagData(){
        super();
    }
    
    public TagData(String id, SystemData systemData, String name, String description, int sort) {
        super();
        this.id = id;
        this.systemData = systemData;
        this.name = name;
        this.description = description;
        this.sort = sort;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "app_seq")
    @GenericGenerator(name = "app_seq", strategy = "com.chsi.framework.hibernate.StringRandomGenerator")
    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String arg0) {
        this.id = arg0;
    }

    @OneToOne
    @JoinColumn(name = "SYSTEM_ID")
    public SystemData getSystemData() {
        return systemData;
    }

    public void setSystemData(SystemData systemData) {
        this.systemData = systemData;
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
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
    
    

}
