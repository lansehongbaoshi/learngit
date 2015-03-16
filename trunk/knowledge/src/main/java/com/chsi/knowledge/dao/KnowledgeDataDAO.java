package com.chsi.knowledge.dao;

import java.util.List;

import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.KnowledgeTagRelationData;
/**
 * 知识表操作DAO层
 * @author chenjian
 *
 */
public interface KnowledgeDataDAO {

    void save(KnowledgeData knowledgeData);
    
    void update(KnowledgeData knowledgeData);

    KnowledgeData getKnowledgeById(String id);
    
    /**
     * 根据系统ID，标签名称
     * @param SystemId
     * @param tagName
     * @return
     */
    Long countKnowledges(String systemId, String tagId, KnowledgeStatus knowledgeStatus);

   /**
    * 根据系统ID，标签名称，以及分页情况 取出多条知识
    * @param systemId
    * @param tagName
    * @param knowledgeStatus
    * @param start
    * @param size
    * @return
    */
    List<KnowledgeData> getKnowledges(String systemId, String tagId, KnowledgeStatus knowledgeStatus, int start, int size);
    
    void saveKnowledgeTagRelation(KnowledgeTagRelationData knowledgeTagRelationData);
    
    void updateVisitCntPlusOne(String id);

}
