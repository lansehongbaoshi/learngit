package com.chsi.knowledge.pojo;

import java.util.Calendar;

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
import com.chsi.knowledge.dic.KnowledgeStatus;

/**
 * 知识表
 * 
 * @author chenjian
 * 
 */
@Entity
@Table(name = "KNOWLEDGE")
@DynamicUpdate(value = true)
public class KnowledgeData extends PersistentObject {

    private static final long serialVersionUID = -4645122472610059168L;
    private String id;
    private TagData tagData;
    private String keywords;
    private String cmsId;
    private int visitCnt;
    private int sort;
    private KnowledgeStatus knowledgeStatus;
    private String creater;
    private Calendar createTime;
    private String updater;
    private Calendar updateTime;

    public void setData(PersistentObject persistentObject) {
        KnowledgeData knowledgeData = (KnowledgeData) persistentObject;
        this.id = knowledgeData.getId();
        this.tagData = knowledgeData.getTagData();
        this.keywords = knowledgeData.getKeywords();
        this.cmsId = knowledgeData.getCmsId();
        this.visitCnt = knowledgeData.getVisitCnt();
        this.sort = knowledgeData.getSort();
        this.knowledgeStatus = knowledgeData.getKnowledgeStatus();
        this.creater = knowledgeData.getCreater();
        this.createTime = knowledgeData.getCreateTime();
        this.updater = knowledgeData.getUpdater();
        this.updateTime = knowledgeData.getUpdateTime();
    }

    public KnowledgeData(){
        super();
    }
    
    public KnowledgeData(String id, TagData tagData, String keywords,
            String cmsId, int visitCnt, int sort,
            KnowledgeStatus knowledgeStatus, String creater,
            Calendar createTime, String updater, Calendar updateTime) {
        super();
        this.id = id;
        this.tagData = tagData;
        this.keywords = keywords;
        this.cmsId = cmsId;
        this.visitCnt = visitCnt;
        this.sort = sort;
        this.knowledgeStatus = knowledgeStatus;
        this.creater = creater;
        this.createTime = createTime;
        this.updater = updater;
        this.updateTime = updateTime;
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
    @JoinColumn(name = "TAG_ID")
    public TagData getTagData() {
        return tagData;
    }

    public void setTagData(TagData tagData) {
        this.tagData = tagData;
    }

    @Column(name = "KEYWORDS")
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Column(name = "CMS_ID")
    public String getCmsId() {
        return cmsId;
    }

    public void setCmsId(String cmsId) {
        this.cmsId = cmsId;
    }

    @Column(name = "VISIT_CNT")
    public int getVisitCnt() {
        return visitCnt;
    }

    public void setVisitCnt(int visitCnt) {
        this.visitCnt = visitCnt;
    }

    @Column(name = "SORT")
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Column(name = "STATUS")
    public KnowledgeStatus getKnowledgeStatus() {
        return knowledgeStatus;
    }

    public void setKnowledgeStatus(KnowledgeStatus knowledgeStatus) {
        this.knowledgeStatus = knowledgeStatus;
    }

    @Column(name = "CREATER")
    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    @Column(name = "CREATE_TIME")
    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }

    @Column(name = "UPDATER")
    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    @Column(name = "UPDATE_TIME")
    public Calendar getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Calendar updateTime) {
        this.updateTime = updateTime;
    }

}
