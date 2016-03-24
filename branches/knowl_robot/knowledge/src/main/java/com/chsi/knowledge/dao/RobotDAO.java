package com.chsi.knowledge.dao;

import java.util.List;

import com.chsi.framework.pojos.PersistentObject;
import com.chsi.knowledge.pojo.RobotASetData;
import com.chsi.knowledge.pojo.RobotQSetData;

public interface RobotDAO {
    void save(PersistentObject pojo);
    
    void del(PersistentObject pojo);
    
    RobotQSetData getRobotQSetData(String id);
    
    RobotASetData getRobotASetData(String id);
    
    /**
     * 根据问题查询配置的答案
     * @param q
     * @return
     */
    List<RobotASetData> getAByQ(String q);
    
    List<RobotQSetData> allQ();
    
    List<RobotASetData> getAByQSet(RobotQSetData robotQSetData);
}
