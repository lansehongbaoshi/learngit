package com.chsi.knowledge.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.chsi.knowledge.pojo.DiscussData;
import com.chsi.knowledge.vo.DiscussCountVO;
import com.chsi.knowledge.vo.DiscussInfoVO;
import com.chsi.knowledge.vo.KnowListVO;
import com.chsi.knowledge.vo.SystemStatisticsVO;

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
    List<SystemStatisticsVO> getSystemStatistics();
    List<DiscussCountVO> getBadKnowledgeRank();
    List<DiscussCountVO> getGoodKnowledgeRank();
    /**
     * 获取一个系统差评排名的前十名
     * @param systemId
     * @return
     */
    List<DiscussCountVO> getKnowledgeInSystemTopBad(String systemId);
    /**
     * 获取一个系统好评排名的前十名
     * @param systemId
     * @return
     */
    List<DiscussCountVO> getKnowledgeInSystemTopGood(String systemId);

}
