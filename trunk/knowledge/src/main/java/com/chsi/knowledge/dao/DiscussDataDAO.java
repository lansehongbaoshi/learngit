package com.chsi.knowledge.dao;

import java.util.List;

import com.chsi.knowledge.pojo.DiscussData;
/**
 * 评价表操作DAO 接口
 * @author chenjian
 *
 */
public interface DiscussDataDAO {
    
    void saveOrUpdate(DiscussData discussData);
    /**
     *  根据知识ID取出评价表
     * @param knowledgeId
     * @return
     */
    List<DiscussData> getDiscusssByKnowledgeId(String knowledgeId);
}
