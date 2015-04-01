package com.chsi.knowledge.service;

import com.chsi.knowledge.pojo.SystemData;

/**
 * 系统业务逻辑层接口
 * @author chenjian
 */
public interface SystemService {

    SystemData getSystemById(String id);
    
    void save(SystemData systemData);
}
