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
     * 根据系统ID，标签ID 取出多条知识
     * @param systemId
     * @param tagName
     * @param knowledgeStatus
     * @param start
     * @param size
     * @return
     */
     List<KnowTagRelationData> getKnowTagDatas(String tagId, KnowledgeStatus knowledgeStatus);
    
    void save(KnowTagRelationData knowledgeTagRelationData);
}
