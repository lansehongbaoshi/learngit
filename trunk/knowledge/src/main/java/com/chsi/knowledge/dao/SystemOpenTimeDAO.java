package com.chsi.knowledge.dao;

import java.util.List;

import com.chsi.knowledge.pojo.SystemOpenTimeData;

public interface SystemOpenTimeDAO {

    public void save(SystemOpenTimeData systemOpenTimeData);
    
    public void delete(String systemId);
    
    public List<SystemOpenTimeData> getList(String systemId);
    
    public List<SystemOpenTimeData> getSystemId();
}
