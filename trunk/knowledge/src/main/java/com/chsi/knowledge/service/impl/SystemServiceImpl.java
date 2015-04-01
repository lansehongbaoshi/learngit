package com.chsi.knowledge.service.impl;


import com.chsi.framework.service.BaseDbService;
import com.chsi.knowledge.ServiceConstants;
import com.chsi.knowledge.dao.SystemDataDAO;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.service.SystemService;

public class SystemServiceImpl extends BaseDbService implements SystemService{

    private SystemDataDAO systemDataDAO;

    @Override
    protected void doCreate() {
        systemDataDAO = getDAO(ServiceConstants.SYSTEMDATA_DAO, SystemDataDAO.class);
    }

    @Override
    protected void doRemove() {
        
    }
    
    @Override
    public SystemData getSystemById(String id) {
        return systemDataDAO.getSystemById(id);
    }

    @Override
    public void save(SystemData systemData) {
        systemDataDAO.save(systemData);
    }

}
