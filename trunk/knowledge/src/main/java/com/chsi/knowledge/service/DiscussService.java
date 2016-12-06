package com.chsi.knowledge.service;

import net.sf.json.JSONObject;

import com.chsi.knowledge.pojo.DiscussData;
import com.chsi.knowledge.vo.DiscussCountVO;
import com.chsi.knowledge.vo.DiscussInfoVO;
import com.chsi.knowledge.vo.KnowListVO;

/**
 * 评价业务逻辑层接口
 * 
 * @author chenjian
 */
public interface DiscussService {

    void saveOrUpdate(DiscussData discussData);

    /**
     * 根据知识ID取出评价
     * 
     * @param knowledgeId
     * @return
     */
    /* DiscussVO getDiscussVOByKnowledgeId(String knowledgeId); */

    DiscussCountVO getDiscussCountVOByKId(String KId);

    KnowListVO<DiscussInfoVO> getDiscussInfoVOList(String KId, int start, int pageSize);

    JSONObject getDiscussCount(String kId);
}
