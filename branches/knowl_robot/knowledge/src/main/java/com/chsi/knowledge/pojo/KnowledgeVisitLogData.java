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
@Table(name = "KNOWLEDGE_VISIT_LOG")
@DynamicUpdate(value = true)
public class KnowledgeVisitLogData extends PersistentObject {

    private static final long serialVersionUID = 3072717263208559815L;

    private String id;
    private String knowledgeId;
    private int visitCnt;
    private Calendar createTime;
    
    public KnowledgeVisitLogData(KnowledgeData data) {
        this.knowledgeId = data.getId();
        this.visitCnt = data.getVisitCnt();
        this.createTime = Calendar.getInstance();
    }
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "app_seq")
    @GenericGenerator(name = "app_seq", strategy = "com.chsi.framework.hibernate.StringRandomGenerator")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "KNOWLEDGE_ID")
    public String getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(String knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    @Column(name = "VISIT_CNT")
    public int getVisitCnt() {
        return visitCnt;
    }

    public void setVisitCnt(int visitCnt) {
        this.visitCnt = visitCnt;
    }

    @Column(name = "CREATE_TIME")
    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }

}
