package com.chsi.knowledge.dao;

import java.util.List;
import java.util.Map;

import com.chsi.framework.page.Page;
import com.chsi.framework.pojos.PersistentObject;
import com.chsi.knowledge.dic.AType;
import com.chsi.knowledge.pojo.QALogData;
import com.chsi.knowledge.pojo.QASessionData;
import com.chsi.knowledge.pojo.RobotASetData;
import com.chsi.knowledge.pojo.RobotQSetData;
import com.chsi.knowledge.vo.PieVO;

public interface RobotDAO {
    void save(PersistentObject pojo);
    
    void update(PersistentObject pojo);
    
    void del(PersistentObject pojo);
    
    QASessionData getQASessionDataById(String id);
    
    RobotQSetData getRobotQSetData(String id);
    
    RobotASetData getRobotASetData(String id);
    
    /**
     * 根据问题模糊查询配置的答案
     * @param q
     * @return
     */
    List<RobotASetData> getAByQ(String q);
    
    /**
     * 根据问题精确查询配置的答案
     * @param q
     * @return
     */
    List<RobotASetData> getAByExplicitQ(String q);
    
    List<RobotQSetData> allQ();
    List<RobotQSetData> pageQ(int start,int max);
    
    List<RobotASetData> getAByQSet(RobotQSetData robotQSetData);
    
    /**
     * 统计会话情况，包括：正常会话（有问答）、空会话（只开启窗口，无问答）
     * @return
     */
    List<PieVO> totalSession();
    List<PieVO> totalSession(String startTime, String endTime);
    
    /**
     * 统计问题情况，包括：无答案、确定答案、不确定答案
     * @return
     */
    List<PieVO> totalQ();
    List<PieVO> totalQ(String startTime, String endTime);
    
    /**
     * 查询问题列表
     * @param aType
     * @return
     */
    List<QALogData> listQALogDataByAType(AType aType);
    
    /**
     * 分页查询问题列表
     * @param aType
     * @return
     */
    Page<QALogData> pageQALogDataByAType(AType aType, int currentPage, int pageSize, String startTime, String endTime);
    /**
     * 根据问题查找RobotQSetDate
     * @param ques
     * @return
     */
    public RobotQSetData getRobotQSetByQ(String ques);
    
    public Map<RobotQSetData, List<RobotASetData>> getSolrByRQ(RobotQSetData robotQSetData);
}
