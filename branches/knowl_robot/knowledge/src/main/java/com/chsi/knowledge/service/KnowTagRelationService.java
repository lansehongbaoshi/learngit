package com.chsi.knowledge.service;

import java.util.List;

import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.pojo.KnowTagRelationData;

public interface KnowTagRelationService {
    KnowTagRelationData getKnowTagRelationByKnowId(String knowledgeId, String tagId);
    
    List<KnowTagRelationData> getKnowTagRelationByKnowId(String knowledgeId);
    
    List<KnowTagRelationData> getKnowTagDatas(KnowledgeStatus knowledgeStatus, String knowledgeId);
    
    void save(KnowTagRelationData knowTagRelationData);
    
    /**
     * 删除某个知识的所有标签
     * @param knowledgeId
     * @return
     */
    int del(String knowledgeId);

    /**
     * 非删除状态的知识
     * @param tagId
     * @return
     */
    List<KnowTagRelationData> getKnowTagDatas(String tagId);
    
    List<KnowTagRelationData> getAllKnowTagDatas(String tagId);
}
