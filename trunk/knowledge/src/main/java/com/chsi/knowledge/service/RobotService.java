package com.chsi.knowledge.service;

import java.util.List;
import java.util.Map;

import com.chsi.framework.pojos.PersistentObject;
import com.chsi.knowledge.pojo.RobotASetData;
import com.chsi.knowledge.pojo.RobotQSetData;
import com.chsi.knowledge.vo.AnswerVO;

public interface RobotService {
    void save(PersistentObject pojo);
    
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
    
    void deleteRobotQASet(String id);
}