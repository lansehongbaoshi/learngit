package com.chsi.knowledge.dao;

import java.util.List;

import com.chsi.knowledge.pojo.LogOperData;

public interface LogOperDataDAO {

    void save(LogOperData logOper);

    List<LogOperData> getLogOperByKeyId(String keyId);

}
