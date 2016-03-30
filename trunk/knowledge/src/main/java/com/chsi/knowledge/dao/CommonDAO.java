package com.chsi.knowledge.dao;

import java.util.Calendar;
import java.util.List;

import com.chsi.framework.pojos.PersistentObject;
import com.chsi.knowledge.pojo.SearchLogData;
import com.chsi.knowledge.pojo.SystemOpenTimeData;
import com.chsi.knowledge.vo.CntVO;

public interface CommonDAO {
    void save(PersistentObject po);
    
    void del(PersistentObject po);
    
    List<Object[]> getTopKeyword(int n, Calendar startTime, Calendar endTime);
    
    List<CntVO> getTopVisitKnowl(SystemOpenTimeData sotd, Calendar startTime, Calendar endTime);
    
    /**
     * 查询SYSTEM_ID,KEYWORD,USER_IP都相同的数据
     * @return
     */
    List<Object[]> getDuplicatedDatas(Calendar startTime, Calendar endTime);
    
    List<SearchLogData> getTheDuplicatedData(String systemId, String keyword, String userIP);
    
    /**
     * 查询某个时间段之间的搜索日志
     * @param startTime
     * @param endTime
     * @return
     */
    List<SearchLogData> getSearchLogData(Calendar startTime, Calendar endTime);
    
    
    List<Long> totalVisitLog(String kowId, String startTime, String endTime);
}
