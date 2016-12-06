package com.chsi.knowledge.dao;

import java.util.List;

import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.dic.KnowledgeType;
import com.chsi.knowledge.pojo.KnowTagRelationData;
import com.chsi.knowledge.pojo.KnowledgeData;

public interface KnowTagRelationDataDAO {

    /**
     * 根据知识的ID 取出 标签与知识的关联关系
     * 
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
     * 
     * @param systemId
     * @param tagName
     * @param knowledgeStatus
     * @param start
     * @param size
     * @return
     */
    List<KnowTagRelationData> getKnowTagDatas(String tagId, KnowledgeStatus knowledgeStatus, KnowledgeType type);

    /**
     * 非删除状态的知识
     * 
     * @param tagId
     * @return
     */
    List<KnowTagRelationData> getKnowTagDatas(String tagId);

    /**
     * 不考虑状态，所有关联的知识
     * 
     * @param tagId
     * @return
     */
    List<KnowTagRelationData> getAllKnowTagDatas(String tagId);

    /**
     * 取出该条知识属于的所有 关联关系
     * 
     * @param knowledgeStatus
     * @param knowledgeId
     * @return
     */
    List<KnowTagRelationData> getKnowTagDatas(KnowledgeStatus knowledgeStatus, String knowledgeId);

    void save(KnowTagRelationData knowledgeTagRelationData);

    int del(String knowledgeId);

    long getKnowsCntBySystemId(String systemId, String type);

    List<KnowledgeData> getKnowsBySystemId(String systemId);

    List<KnowTagRelationData> getKnowsByTagId(String tagId);
}
