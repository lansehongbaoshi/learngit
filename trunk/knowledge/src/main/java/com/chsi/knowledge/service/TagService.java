package com.chsi.knowledge.service;

import java.util.List;

import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.dic.KnowledgeType;
import com.chsi.knowledge.dic.TagProperty;
import com.chsi.knowledge.pojo.TagData;
import com.chsi.knowledge.vo.ViewTagVO;

/**
 * 标签业务逻辑层
 * 
 * @author chenjian
 * 
 */
public interface TagService {
    /**
     * 根据systemId获取系统下所有标签
     * 
     * @param systemId
     * @return
     */
    List<TagData> get(String systemId);

    /**
     * 根据系统ID，知识状态，获取标签数据
     * 
     * @param systemId
     * @param knowledgeStatus
     * @return
     */
    List<ViewTagVO> getTagVOsBySystemIdAndStatus(String systemId, KnowledgeStatus knowledgeStatus, KnowledgeType type);

    void saveOrUpdate(TagData tagData);

    void delete(TagData tagData);

    TagData getTagData(String systemId, String name);

    List<TagData> getTagData(String systemId, TagProperty property);

    TagData getTagData(String id);
}
