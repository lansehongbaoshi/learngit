package com.chsi.knowledge.index.service;

import java.util.List;

import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.LogOperData;

public interface LogOperService {

    void save(LogOperData logOper);

    List<LogOperData> getLogOperByKeyId(String keyId);

}
