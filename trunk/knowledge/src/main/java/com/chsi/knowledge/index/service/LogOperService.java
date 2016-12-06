package com.chsi.knowledge.index.service;

import java.util.List;

import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.LogOperData;
import com.chsi.knowledge.vo.LogOperListVO;
import com.chsi.knowledge.vo.LogOperVO;

public interface LogOperService {

    void save(LogOperData logOper);

    List<LogOperData> getLogOperByKeyId(String keyId);

    LogOperListVO<LogOperVO> searchLogOper(String startDate, String endDate, int curPage) throws Exception;

}
