package com.chsi.knowledge.service;

import java.util.List;

import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.pojo.TagData;
import com.chsi.knowledge.vo.TagListPageVO;
/**
 * 标签业务逻辑层
 * @author chenjian
 *
 */
public interface TagService {
    //获取所有TAG临时用，后面删
    List<TagData> get();
    /**
     * 根据系统ID，知识状态，获取标签page页面数据
     * @param systemId
     * @param knowledgeStatus
     * @return
     */
    TagListPageVO getTagVOsBySystemIdAndStatus(String systemId,KnowledgeStatus knowledgeStatus);
    
    void saveOrUpdate(TagData tagData);
    
}
