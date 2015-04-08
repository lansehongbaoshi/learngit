package com.chsi.knowledge.service.impl;

import com.chsi.framework.service.BaseDbService;
import com.chsi.knowledge.ServiceConstants;
import com.chsi.knowledge.dao.KnowTagRelationDataDAO;
import com.chsi.knowledge.pojo.KnowTagRelationData;
import com.chsi.knowledge.service.KnowTagRelationService;

public class KnowTagRelationServiceImpl extends BaseDbService implements KnowTagRelationService{

    private KnowTagRelationDataDAO knowTagRelationDataDAO;
    
    @Override
    protected void doCreate() {
        knowTagRelationDataDAO = getDAO(ServiceConstants.KNOWTAGRELATIONDATA_DAO, KnowTagRelationDataDAO.class);
    }

    @Override
    protected void doRemove() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void save(KnowTagRelationData knowTagRelationData) {
        knowTagRelationDataDAO.save(knowTagRelationData);
    }
    
}
