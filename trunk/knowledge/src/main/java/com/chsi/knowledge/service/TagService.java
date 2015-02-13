package com.chsi.knowledge.service;

import java.util.List;

import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.pojo.TagData;
import com.chsi.knowledge.vo.TagVO;
/**
 * 标签业务逻辑层
 * @author chenjian
 *
 */
public interface TagService {
    /**
     * 根据系统ID，知识状态，获取标签VO
     * @param systemId
     * @param knowledgeStatus
     * @return
     */
    List<TagVO> getTagVOsBySystemIdAndStatus(String systemId,KnowledgeStatus knowledgeStatus);
    
    void saveOrUpdate(TagData tagData);
    
    TagData getTagDataBySystemIdAndName(String systemId,String name);

}
