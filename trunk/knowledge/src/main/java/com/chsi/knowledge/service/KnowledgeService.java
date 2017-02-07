package com.chsi.knowledge.service;

import java.util.List;
import java.util.Set;

import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.dic.KnowledgeType;
import com.chsi.knowledge.pojo.KnowTagRelationData;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.pojo.TagData;
import com.chsi.knowledge.vo.ViewKnowVO;
import com.chsi.knowledge.vo.ViewKnowsVO;

/**
 * 知识业务逻辑层
 * 
 * @author chenjian
 */
public interface KnowledgeService {
    /* 后台service开始 */
    /**
     * 取出所有的知识
     * 
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
     * 某个系统置顶的知识
     * 
     * @param systemId
     * @return
     */
    Set<KnowledgeData> getTop(String systemId);

    /**
     * 保存知识并向CMS 中保存相关数据
     * 
     * @param knowledgeData
     * @param articleTitle
     * @param articleContent
     * @param articleStatusType
     * @param ssdm
     * @param createBy
     */
    void save(KnowledgeData knowledgeData, String articleTitle, String articleContent, String ssdm, String createBy);

    /**
     * 更新知识，同时更新CMS中的数据
     * 
     * @param knowledgeData
     * @param articleId
     * @param articleTitle
     * @param articleContent
     * @param articleStatusType
     * @param updateBy
     */
    void update(KnowledgeData knowledgeData, String articleTitle, String articleContent, String updateBy);

    void delete(KnowledgeData knowledgeData);

    void update(KnowledgeData knowledgeData);

    /* 后台service结束 */

    /* 前台service开始 */
    /**
     * 根据ID取知识，同时从CMS中读取对应数据
     * 
     * @param id
     * @return
     */
    ViewKnowVO getKnowVOById(String id, String tagId);

    /**
     * 查询知识点内容（）
     * 
     * @param id
     * @return
     */
    KnowledgeData getKnowledgeWithArticleById(String id);

    /**
     * 根据系统ID、标签、状态、分页数据取得知识，同时从CMS中读取对应数据。
     * 
     * @param systemId
     * @param tagName
     * @param knowledgeStatus
     * @param start
     * @param size
     * @return
     */
    ViewKnowsVO getViewKnowsVO(SystemData system, String tagId, int start, int pageSize);

    void updateVisitCntPlusOne(String id);

    void updateCtiVisitCntPlusOne(String id);
    
    public void updateVisitCntPlus(String id,int count);

    /* 前台service结束 */

    KnowledgeData getKnowledgeById(String id);
    
    KnowledgeData getKnowledgeByCmsId(String cmsId);

    /**
     * 根据标签ID 取出多条知识
     * 
     * @param tagId
     * @param knowledgeStatus
     * @return
     */
    List<KnowTagRelationData> getKnowTagDatas(String tagId, KnowledgeStatus knowledgeStatus, KnowledgeType type);

    /**
     * 
     * @param systemId
     * @param knowledgeStatus
     * @param type
     * @return
     */
    List<KnowledgeData> get(String systemId, KnowledgeStatus knowledgeStatus, KnowledgeType type);

    List<KnowledgeData> getKnowledgeByStatus(String systemId, String tag, KnowledgeStatus dsh, String type, int start, int size);

    List<TagData> getTagDatasByKnowId(KnowledgeData know);

    long getKnowledgeCount(String systemId, String tag, KnowledgeStatus dsh, String type, String userId);

    List<KnowledgeData> getKnowledgeByStatusAndUserId(String systemId, String tag, KnowledgeStatus dsh, String type, String userId, int start, int size);

    /**
     * 判断该知识是否在前rank名在各个系统中
     * 
     * @param knowledge
     * @param rank
     * @return
     */
    public boolean judgeKnowledgeInTopCount(String knowId, int rank);

    void updateVisitCnt(KnowledgeData knowledgeData);
}
