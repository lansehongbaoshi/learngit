package com.chsi.knowledge.service.impl;

import com.chsi.framework.pojos.PersistentObject;
import com.chsi.framework.service.BaseDbService;
import com.chsi.knowledge.ServiceConstants;
import com.chsi.knowledge.dao.RobotDAO;
import com.chsi.knowledge.service.RobotService;

public class RobotServiceImpl extends BaseDbService implements RobotService {
    RobotDAO robotDAO;
    
    @Override
    protected void doCreate() {
        robotDAO = getDAO(ServiceConstants.ROBOT_DAO, RobotDAO.class);
    }

    @Override
    protected void doRemove() {
        
    }

    @Override
    public void save(PersistentObject pojo) {
        robotDAO.save(pojo);
    }
}
