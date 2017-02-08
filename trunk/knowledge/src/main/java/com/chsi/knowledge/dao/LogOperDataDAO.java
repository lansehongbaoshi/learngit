package com.chsi.knowledge.dao;

import java.util.Date;
import java.util.List;

import com.chsi.knowledge.pojo.LogOperData;

public interface LogOperDataDAO {

    void save(LogOperData logOper);

    List<LogOperData> getLogOperByKeyId(String keyId);

    List<LogOperData> getLogOpersByDate(String operator, String operation, Date startDate, Date endDate, int curPage, int pageSize);

    int getLogOpersCountByDate(String operator, String operation, Date startDate, Date endDate);

    List<String> getLogOperAllUserId();

    List<String> getLogOperAllOperations();

}
