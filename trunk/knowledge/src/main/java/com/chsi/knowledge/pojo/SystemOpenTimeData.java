package com.chsi.knowledge.pojo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.chsi.framework.pojos.PersistentObject;

@Entity
@Table(name = "SYSTEM_OPEN_TIME")
@DynamicUpdate(value = true)
public class SystemOpenTimeData extends PersistentObject {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String systemId;
    private Calendar startTime;
    private Calendar endTime;
    private String tagIds;
    
    public SystemOpenTimeData() {
        
    }
    
    public SystemOpenTimeData(String systemId) {
        this.systemId = systemId;
    }
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "app_seq")
    @GenericGenerator(name = "app_seq", strategy = "com.chsi.framework.hibernate.StringRandomGenerator")
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    @Column(name = "SYSTEM_ID", nullable = false)
    public String getSystemId() {
        return systemId;
    }
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
    
    @Column(name = "START_TIME", nullable = false)
    public Calendar getStartTime() {
        return startTime;
    }
    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }
    
    @Column(name = "END_TIME", nullable = false)
    public Calendar getEndTime() {
        return endTime;
    }
    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }
    
    
    @Column(name = "TAG_IDS")
    public String getTagIds() {
        return tagIds;
    }
    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }
    
    

}
