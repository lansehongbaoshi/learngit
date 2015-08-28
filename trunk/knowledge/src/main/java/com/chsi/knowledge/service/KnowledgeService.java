package com.chsi.knowledge.service;

import java.util.List;

import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.vo.ViewKnowVO;
import com.chsi.knowledge.vo.ViewKnowsVO;
/**
 * 知识业务逻辑层
 * @author chenjian
 */
public interface KnowledgeService {
    /*后台service开始*/
    /**
     * 取出所有的知识
     * @return
     */
    List<KnowledgeData> get(String systemId);
    /**
     * 保存知识并向CMS 中保存相关数据
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
     * @param knowledgeData
     * @param articleId
     * @param articleTitle
     * @param articleContent
     * @param articleStatusType
     * @param updateBy
     */
    void update(KnowledgeData knowledgeData, String articleTitle, String articleContent, String updateBy);
    
    
    
    /*后台service结束*/
    
    
    
    /*前台service开始*/
    /**
     * 根据ID取知识，同时从CMS中读取对应数据
     * @param id
     * @return
     */
    ViewKnowVO getKnowVOById(String id, String tagId);
    
    KnowledgeData getKnowledgeCmsById(String id);
    
    /**
     * 根据系统ID、标签、状态、分页数据取得知识，同时从CMS中读取对应数据。
     * @param systemId
     * @param tagName
     * @param knowledgeStatus
     * @param start
     * @param size
     * @return
     */
    ViewKnowsVO getViewKnowsVO(SystemData system, String tagId, int start, int pageSize); 
    
    void updateVisitCntPlusOne(String id);
    
    /*前台service结束*/
    
    
    KnowledgeData getKnowledgeById(String id);
}
