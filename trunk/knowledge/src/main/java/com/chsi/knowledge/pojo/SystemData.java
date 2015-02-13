package com.chsi.knowledge.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.chsi.framework.pojos.PersistentObject;

/**
 * 系统信息表
 * 
 * @author chenjian
 * 
 */
@Entity
@Table(name = "SYSTEM")
@org.hibernate.annotations.Entity(dynamicUpdate=true)
public class SystemData extends PersistentObject {

    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String description;

    public void setData(PersistentObject persistentObject) {
        SystemData systemData = (SystemData) persistentObject;
        this.id = systemData.getId();
        this.name = systemData.getName();
        this.description = systemData.getDescription();
    }
    
    public SystemData(){
        super();
    }

    public SystemData(String id, String name, String description) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
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

}
