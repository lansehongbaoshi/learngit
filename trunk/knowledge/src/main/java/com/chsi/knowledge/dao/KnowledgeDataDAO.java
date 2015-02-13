package com.chsi.knowledge.dao;

import java.util.List;

import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.pojo.KnowledgeData;
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
    Long countKnowledges(String systemId, String tagName, KnowledgeStatus knowledgeStatus);

   /**
    * 根据系统ID，标签名称，以及分页情况 取出多条知识
    * @param systemId
    * @param tagName
    * @param knowledgeStatus
    * @param start
    * @param size
    * @return
    */
    List<KnowledgeData> getKnowledges(String systemId, String tagName, KnowledgeStatus knowledgeStatus, int start, int size);
    
    void updateVisitCntPlusOne(String id);

}
