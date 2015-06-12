package com.chsi.knowledge.service.impl;


import java.util.List;

import com.chsi.framework.service.BaseDbService;
import com.chsi.knowledge.ServiceConstants;
import com.chsi.knowledge.dao.SystemDataDAO;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.util.ManageCacheUtil;

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
        SystemData systemData = ManageCacheUtil.getSystem(id);
        if (null == systemData) {
            systemData = systemDataDAO.getSystemById(id);
            if (null != systemData) {
                ManageCacheUtil.addSystem(id, systemData);
            }
        }
        return systemData;
    }

    @Override
    public void save(SystemData systemData) {
        systemDataDAO.save(systemData);
    }
    
    @Override
    public void update(SystemData systemData) {
        systemDataDAO.update(systemData);
        ManageCacheUtil.removeSystem(systemData.getId());
    }

    @Override
    public List<SystemData> getSystems() {
        return systemDataDAO.getSystems();
    }

}
