package com.chsi.knowledge.dao;

import java.util.List;

import com.chsi.framework.pojos.PersistentObject;
import com.chsi.knowledge.pojo.RobotASetData;
import com.chsi.knowledge.pojo.RobotQSetData;
import com.chsi.knowledge.vo.PieVO;

public interface RobotDAO {
    void save(PersistentObject pojo);
    
    void del(PersistentObject pojo);
    
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
    
    List<RobotASetData> getAByQSet(RobotQSetData robotQSetData);
    
    /**
     * 统计会话情况，包括：正常会话（有问答）、空会话（只开启窗口，无问答）
     * @return
     */
    List<PieVO> totalSession();
    
    /**
     * 统计问题情况，包括：没答案、确定答案、不确定答案
     * @return
     */
    List<PieVO> totalQ();
}
