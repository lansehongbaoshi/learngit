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
    
    /**
     * 某个系统的所有知识点
     * @param systemId
     * @return
     */
    List<KnowledgeData> get(String systemId);
    
    /**
     * 某个系统某个状态的所有知识点
     * @param systemId
     * @param knowledgeStatus
     * @return
     */
    List<KnowledgeData> get(String systemId, KnowledgeStatus knowledgeStatus);

    void save(KnowledgeData knowledgeData);
    
    void update(KnowledgeData knowledgeData);
    
    void delete(KnowledgeData knowledgeData);

    KnowledgeData getKnowledgeById(String id);
    
    /**
     * 标签ID，知识状态统计知识数量
     * @param tagName
     * @return
     */
    int countKnowledges(String tagId, KnowledgeStatus knowledgeStatus);

    /**
     * 根据ID 更新知识的访问次数加一
     * @param id
     */
    void updateVisitCntPlusOne(String id);

}
