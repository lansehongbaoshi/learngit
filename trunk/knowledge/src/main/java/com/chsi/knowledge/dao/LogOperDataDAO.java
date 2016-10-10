package com.chsi.knowledge.dao;

import java.util.Date;
import java.util.List;

import com.chsi.knowledge.pojo.LogOperData;

public interface LogOperDataDAO {

    void save(LogOperData logOper);

    List<LogOperData> getLogOperByKeyId(String keyId);

    List<LogOperData> getLogOpersByDate(Date startDate, Date endDate,
            int curPage, int pageSize);

    int getLogOpersCountByDate(Date startDate, Date endDate);

}
