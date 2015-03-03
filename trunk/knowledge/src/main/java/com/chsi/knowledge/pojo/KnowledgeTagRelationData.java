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
 * 知识与标签关联表
 * @author chenjian
 */
@Entity
@Table(name = "KNOWLEDGE_TAG_RELATION")
@DynamicUpdate(value = true)
public class KnowledgeTagRelationData extends PersistentObject {

    private static final long serialVersionUID = 1L;
    private String id;
    private KnowledgeData knowledgeData;
    private TagData tagData;

    public void setData(PersistentObject persistentObject) {
        KnowledgeTagRelationData relation = (KnowledgeTagRelationData) persistentObject;
        this.id = relation.getId();
        this.knowledgeData = relation.getKnowledgeData();
        this.tagData = relation.getTagData();
    }
    
    public KnowledgeTagRelationData(){
        super();
    }
    
    public KnowledgeTagRelationData(String id, KnowledgeData knowledgeData, TagData tagData) {
        super();
        this.id = id;
        this.knowledgeData = knowledgeData;
        this.tagData = tagData;
    }
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "app_seq")
    @GenericGenerator(name = "app_seq", strategy = "com.chsi.framework.hibernate.StringRandomGenerator")
    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String s) {
        this.id=s;
    }
    
    @OneToOne
    @JoinColumn(name = "KNOWLEDGE_ID")
    public KnowledgeData getKnowledgeData() {
        return knowledgeData;
    }

    public void setKnowledgeData(KnowledgeData knowledgeData) {
        this.knowledgeData = knowledgeData;
    }

    @OneToOne
    @JoinColumn(name = "TAG_ID")
    public TagData getTagData() {
        return tagData;
    }

    public void setTagData(TagData tagData) {
        this.tagData = tagData;
    }
    
}
