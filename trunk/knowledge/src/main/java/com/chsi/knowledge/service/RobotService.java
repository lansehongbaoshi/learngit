package com.chsi.knowledge.service;

import java.util.List;
import java.util.Map;

import com.chsi.framework.pojos.PersistentObject;
import com.chsi.knowledge.dic.AType;
import com.chsi.knowledge.pojo.QALogData;
import com.chsi.knowledge.pojo.QASessionData;
import com.chsi.knowledge.pojo.RobotASetData;
import com.chsi.knowledge.pojo.RobotQSetData;
import com.chsi.knowledge.vo.AnswerVO;
import com.chsi.knowledge.vo.PieVO;

public interface RobotService {
    void save(PersistentObject pojo);
    
    void update(PersistentObject pojo);
    
    QASessionData getQASessionDataById(String id);
    
    /**
     * 机器人处理回答
     * @param sessionId
     * @param knowId
     * @param q
     * @return
     */
    AnswerVO answer(String sessionId, String knowId, String q);
    
    /**
     * 查询问答map
     * @param qID 为null时查询所有，否则查询单个
     * @return
     */
    Map<RobotQSetData, List<RobotASetData>> getRobotQASet(String qID);
    
    List<RobotASetData> getAByExplicitQ(String q);
    
    void deleteRobotQASet(String id);
    
    List<PieVO> totalSession();
    
    /**
     * 统计问题情况，包括：无答案、确定答案、不确定答案
     * @return
     */
    List<PieVO> totalQ();
    
    /**
     * 查询问题列表，包括：无答案、确定答案、不确定答案这三种情况
     * @param aType 
     * @return
     */
    List<QALogData> listQALogDataByAType(AType aType);
}
