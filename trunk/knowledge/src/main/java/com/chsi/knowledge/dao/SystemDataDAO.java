package com.chsi.knowledge.dao;

import java.util.List;

import com.chsi.knowledge.pojo.SystemData;

/**
 * 系统表操作DAO层
 * 
 * @author chenjian
 * 
 */
public interface SystemDataDAO {

    SystemData getSystemById(String id);

    void save(SystemData systemData);

    void update(SystemData systemData);

    void delete(SystemData systemData);
    
    /**
     * 获取所有系统
     * 
     * @return
     */
    List<SystemData> getSystems();

    /**
     * 获取某属性的所有系统
     * 
     * @return
     */
    List<SystemData> getSystems(int property);

    List<String> getSystemIdByKnowledgeId(String knowledgeId);

    SystemData getKnowledgeByName(String systemName);
}
