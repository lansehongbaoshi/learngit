package com.chsi.knowledge.service;

import java.text.ParseException;
import java.util.List;

import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.pojo.SystemOpenTimeData;

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
    SystemOpenTimeData getSystemOpenTimeDataById(String id);
    
    void save(SystemData systemData);
    void save(SystemData systemData,String[] startTime, String[] endTime) throws ParseException;
    
    void update(SystemData systemData);
    void update(SystemOpenTimeData systemOpenTimeData);
    void update(SystemData systemData, String[] startTime, String[] endTime) throws ParseException;
    
    void delete(SystemData systemData);
    
    /**
     * 获取所有系统列表
     * @return
     */
    List<SystemData> getSystems();
    
    /**
     * 获取当前处于开放状态的系统id
     * @return
     */
    List<SystemOpenTimeData> getSystemId();
    
}
