package com.chsi.knowledge.service;

import java.text.ParseException;
import java.util.List;

import com.chsi.knowledge.dic.SystemProperty;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.pojo.SystemOpenTimeData;

/**
 * 系统业务逻辑层接口
 * 
 * @author chenjian
 */
public interface SystemService {

    /**
     * 根据ID获取系统，会从缓存里面读 ，没有的话再从数据库里读取
     * 
     * @param id
     * @return
     */
    SystemData getSystemById(String id);

    SystemOpenTimeData getSystemOpenTimeDataById(String id);

    void save(SystemData systemData);

    void save(SystemData systemData, String[] startTime, String[] endTime) throws ParseException;

    void update(SystemData systemData);

    void update(SystemOpenTimeData systemOpenTimeData);

    void update(SystemData systemData, String[] startTime, String[] endTime) throws ParseException;

    void delete(SystemData systemData);

    /**
     * *
     * 获取所有某属性的系统列表
     * @param needTimeSet 是否设值开放起始时间
     * @param property 系统属性,null时所有系统
     * @return
     */
    List<SystemData> getSystems(boolean needTimeSet, SystemProperty property);

    /**
     * 获取当前处于工作状态且开放的系统id
     * 
     * @return
     */
    List<SystemOpenTimeData> getUnderwaySystem();

    List<SystemData> getSystemDataByKnowledgeId(String knowledgeId);

    int getKnowsCntBySystem(String systemId, String type);

    List<KnowledgeData> getKnowsBySystem(String systemId);

    void updateSystemKnowTime(List<KnowledgeData> knows);

}
