package com.chsi.knowledge.dao;

import java.util.List;

import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.pojo.KnowTagRelationData;

public interface KnowTagRelationDataDAO {
    
    /**
     * 根据知识的ID 取出 标签与知识的关联关系
     * @param knowledgeId
     * @return
     */
    KnowTagRelationData getKnowTagRelationByKnowId(String knowledgeId, String tagId);
    
    /**
     * 
     * @param knowledgeId
     * @return
     */
    List<KnowTagRelationData> getKnowTagRelationByKnowId(String knowledgeId);
    
    /**
     * 根据标签ID 取出多条知识
     * @param systemId
     * @param tagName
     * @param knowledgeStatus
     * @param start
     * @param size
     * @return
     */
     List<KnowTagRelationData> getKnowTagDatas(String tagId, KnowledgeStatus knowledgeStatus);
     
     /**
      * 取出该条知识属于的所有 关联关系
      * @param knowledgeStatus
      * @param knowledgeId
      * @return
      */
     List<KnowTagRelationData> getKnowTagDatas(KnowledgeStatus knowledgeStatus,String knowledgeId);
    
     void save(KnowTagRelationData knowledgeTagRelationData);
     
     int del(String knowledgeId);
}
