package com.chsi.knowledge.service;

import java.util.List;

import com.chsi.knowledge.pojo.SystemData;

/**
 * 系统业务逻辑层接口
 * @author chenjian
 */
public interface SystemService {

    /**
     * 根据ID获取系统，会从缓存里面读 ，没有的话再从数据库里读取
     * @param id
     * @return
     */
    SystemData getSystemById(String id);
    
    void save(SystemData systemData);
    
    void update(SystemData systemData);
    
    /**
     * 获取所有系统列表
     * @return
     */
    List<SystemData> getSystems();
    
}
