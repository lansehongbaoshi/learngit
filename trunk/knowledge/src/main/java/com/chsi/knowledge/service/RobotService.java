package com.chsi.knowledge.service;

import java.util.List;
import java.util.Map;

import com.chsi.framework.page.Page;
import com.chsi.framework.pojos.PersistentObject;
import com.chsi.knowledge.dic.AType;
import com.chsi.knowledge.pojo.QALogData;
import com.chsi.knowledge.pojo.QASessionData;
import com.chsi.knowledge.pojo.RobotASetData;
import com.chsi.knowledge.pojo.RobotQSetData;
import com.chsi.knowledge.pojo.WeatherCodeData;
import com.chsi.knowledge.vo.AnswerVO;
import com.chsi.knowledge.vo.PieVO;
import com.chsi.knowledge.vo.RobotQAListVO;
import com.chsi.search.client.vo.RobotQABean;

public interface RobotService {
    void save(PersistentObject pojo);

    void update(PersistentObject pojo);

    QASessionData getQASessionDataById(String id);

    /**
     * 机器人处理回答
     * 
     * @param sessionId
     * @param knowId
     * @param q
     * @return
     */
    AnswerVO answer(String sessionId, String knowId, String q, String systemId);

    /**
     * 机器人配置查询
     * 
     * @param queryParams
     * @param start
     * @param max
     * @return
     */
    public RobotQAListVO<RobotQABean> searchRobotConf(String text, int start, int max);

    public List<RobotQABean> getRobotBasicConf(String[] qs);

    /**
     * 查询问答map
     * 
     * @param qID
     *            为null时查询所有，否则查询单个
     * @return
     */
    Map<RobotQSetData, List<RobotASetData>> getRobotQASet(String qID);

    List<RobotASetData> getAByExplicitQ(String q);

    void deleteRobotQASet(String id);

    List<PieVO> totalSession();

    List<PieVO> totalSession(String startTime, String endTime);

    /**
     * 统计问题情况，包括：无答案、确定答案、不确定答案
     * 
     * @return
     */
    List<PieVO> totalQ();

    List<PieVO> totalQ(String startTime, String endTime);

    /**
     * 查询问题列表，包括：无答案、确定答案、不确定答案这三种情况
     * 
     * @param aType
     * @return
     */
    List<QALogData> listQALogDataByAType(AType aType);

    Page<QALogData> pageQALogDataByAType(AType aType, int currentPage, int pageSize, String startTime, String endTime);

    boolean addWeatherAddr(String string);

    WeatherCodeData getWeatherCode(String string);

    WeatherCodeData getWeatherCode(List<String> addrs);
}
