package com.chsi.knowledge.service;

import java.util.List;

import com.chsi.framework.page.Page;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.TagData;
import com.chsi.knowledge.vo.KnowledgeVO;
import com.chsi.news.type.ArticleStatusType;
/**
 * 知识业务逻辑层
 * @author chenjian
 *
 */
public interface KnowledgeService {

    /**
     * 保存知识并向CMS 中保存相关数据
     * @param knowledgeData
     * @param articleTitle
     * @param articleContent
     * @param articleStatusType
     * @param ssdm
     * @param createBy
     */
    void save(KnowledgeData knowledgeData, String articleTitle, String articleContent, ArticleStatusType articleStatusType, String ssdm, String createBy);

    void update(KnowledgeData knowledgeData, String id, String articleTitle, String articleContent, ArticleStatusType articleStatusType, String updateBy);
    /**
     * 根据ID取知识，同时从CMS中读取对应数据
     * @param id
     * @return
     */
    KnowledgeVO getKnowledgeVOById(String id);
    
    KnowledgeData getKnowledgeById(String id);
    
    /**
     * 根据系统ID、标签、状态、分页数据取得知识，同时从CMS中读取对应数据。
     * @param systemId
     * @param tagName
     * @param knowledgeStatus
     * @param start
     * @param size
     * @return
     */
    Page<KnowledgeVO> getKnowledgeVOPage(String systemId, String tagId, KnowledgeStatus knowledgeStatus, int start, int pageSize); 
    
    void update(KnowledgeData knowledgeData);
    
    void updateVisitCntPlusOne(String id);
    

}
