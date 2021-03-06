package com.chsi.knowledge.dao;

import java.util.List;

import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.dic.KnowledgeType;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.pojo.TagData;

/**
 * 知识表操作DAO层
 * 
 * @author chenjian
 * 
 */
public interface KnowledgeDataDAO {

    /**
     * 某个系统的所有知识点（包括已发布、已删除等）
     * 
     * @param systemId
     * @return
     */
    List<KnowledgeData> get(String systemId);

    /**
     * 某个系统某个状态的所有知识点
     * 
     * @param systemId
     * @param knowledgeStatus
     * @return
     */
    List<KnowledgeData> get(String systemId, KnowledgeStatus knowledgeStatus);

    /**
     * 某个系统下某状态某类型的所有知识点
     * 
     * @param systemId
     * @param knowledgeStatus
     * @param type
     * @return
     */
    List<KnowledgeData> get(String systemId, KnowledgeStatus knowledgeStatus, String type);

    /**
     * 获取置顶知识（状态为已审核，类型为公开的）
     * 
     * @param systemId
     * @return 按时间降序排,可能有重复的
     */
    List<KnowledgeData> getTop(String systemId);

    void save(KnowledgeData knowledgeData);

    void update(KnowledgeData knowledgeData);

    void delete(KnowledgeData knowledgeData);

    KnowledgeData getKnowledgeById(String id);
    
    KnowledgeData getKnowledgeByCmsId(String cmsId);

    /**
     * 标签ID，知识状态统计知识数量
     * 
     * @param tagName
     * @return
     */
    int countKnowledges(String tagId, KnowledgeStatus knowledgeStatus, KnowledgeType type);

    /**
     * 根据ID 更新知识的访问次数加一
     * 
     * @param id
     */
    void updateVisitCntPlusOne(String id);

    void updateCtiVisitCntPlusOne(String id);

    List<KnowledgeData> get(String systemId, String tag, KnowledgeStatus dsh, String type, int start, int size);

    List<TagData> getTagDatasByKnowId(String id);

    long getKnowledgeCount(String systemId, String tag, KnowledgeStatus dsh, String type, String userId);

    void update(List<KnowledgeData> knows);

    List<KnowledgeData> get(String systemId, String tag, KnowledgeStatus dsh, String type, String userId, int start, int size);

    SystemData getKnowledgeByName(String systemName);

    void updateVisitCntPlus(String id, int count);

    void updateVisitCnts(KnowledgeData knowledgeData);

}
