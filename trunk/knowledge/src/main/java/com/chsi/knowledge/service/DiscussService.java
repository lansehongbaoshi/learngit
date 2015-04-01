package com.chsi.knowledge.service;

import com.chsi.knowledge.pojo.DiscussData;

/**
 * 评价业务逻辑层接口
 * @author chenjian
 */
public interface DiscussService {
    
    void saveOrUpdate(DiscussData discussData);
    /**
     * 根据知识ID取出评价
     * @param knowledgeId
     * @return
     */
    /*  DiscussVO getDiscussVOByKnowledgeId(String knowledgeId);*/
}
