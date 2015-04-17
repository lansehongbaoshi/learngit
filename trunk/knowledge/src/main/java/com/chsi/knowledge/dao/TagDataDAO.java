package com.chsi.knowledge.dao;

import java.util.List;

import com.chsi.knowledge.pojo.TagData;
/**
 * 标签表操作DAO层
 * @author chenjian
 */
public interface TagDataDAO {
    
    /**
     * 根据系统ID 从标签表中 取出一个系统的所有标签
     * @param systemId
     * @return
     */
    List<TagData>  getTagDataBySystemId(String systemId);
    
    void saveOrUpdate(TagData tagData);
    
    TagData getTagData(String id);
    
    TagData getTagData(String systemId, String name);
}
