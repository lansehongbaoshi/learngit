package com.chsi.knowledge.dao;

import com.chsi.knowledge.pojo.KnowTagRelationData;

public interface KnowTagRelationDataDAO {
    
    /**
     * 根据知识的ID 取出 标签与知识的关联关系
     * @param knowledgeId
     * @return
     */
    KnowTagRelationData getKnowTagRelationByKnowId(String knowledgeId);
    
    void save(KnowTagRelationData knowledgeTagRelationData);
}
