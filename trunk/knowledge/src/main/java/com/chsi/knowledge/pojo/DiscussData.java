package com.chsi.knowledge.pojo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.chsi.framework.pojos.PersistentObject;
import com.chsi.knowledge.dic.DiscussStatus;

/**
 * 评价表
 * 
 * @author chenjian
 * 
 */
@Entity
@Table(name = "DISCUSS")
@org.hibernate.annotations.Entity(dynamicUpdate=true)
public class DiscussData extends PersistentObject {

    private static final long serialVersionUID = 1L;
    private String id;
    private String knowledgeId;
    private String userId;
    private DiscussStatus discussStatus;
    private Calendar createTime;

    public void setData(PersistentObject persistentObject) {
        DiscussData discussData = (DiscussData) persistentObject;
        this.id = discussData.getId();
        this.knowledgeId = discussData.getKnowledgeId();
        this.userId = discussData.getUserId();
        this.discussStatus = discussData.getDiscussStatus();
        this.createTime = discussData.getCreateTime();
    }

    public DiscussData(){
        super();
    }
    
    public DiscussData(String id, String knowledgeId, String userId,
            DiscussStatus discussStatus, Calendar createTime) {
        super();
        this.id = id;
        this.knowledgeId = knowledgeId;
        this.userId = userId;
        this.discussStatus = discussStatus;
        this.createTime = createTime;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "app_seq")
    @GenericGenerator(name = "app_seq", strategy = "com.chsi.framework.hibernate.StringRandomGenerator")
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String arg0) {
        this.id = arg0;
    }

    @Column(name = "KNOWLEDGE_ID")
    public String getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(String knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    @Column(name = "USER_ID")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "DISCUSS")
    public DiscussStatus getDiscussStatus() {
        return discussStatus;
    }

    public void setDiscussStatus(DiscussStatus discussStatus) {
        this.discussStatus = discussStatus;
    }

    @Column(name = "CREATE_TIME")
    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }

}
