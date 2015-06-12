package com.chsi.knowledge.dao;

import java.util.List;

import com.chsi.knowledge.pojo.SystemData;

/**
 * 系统表操作DAO层
 * @author chenjian
 *
 */
public interface SystemDataDAO {

    SystemData getSystemById(String id);
    
    void save(SystemData systemData);
    
    void update(SystemData systemData);
    /**
     * 获取所有系统
     * @return
     */
    List<SystemData> getSystems();
    
}
