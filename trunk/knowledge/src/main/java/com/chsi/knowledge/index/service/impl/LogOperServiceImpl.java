package com.chsi.knowledge.index.service.impl;

import com.chsi.framework.service.BaseDbService;
import com.chsi.knowledge.ServiceConstants;
import com.chsi.knowledge.dao.KnowTagRelationDataDAO;
import com.chsi.knowledge.dao.KnowledgeDataDAO;
import com.chsi.knowledge.dao.LogOperDataDAO;
import com.chsi.knowledge.index.service.LogOperService;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.LogOperData;

public class LogOperServiceImpl extends BaseDbService implements LogOperService {
    
    private LogOperDataDAO logOperDAO;
    private KnowledgeDataDAO knowledgeDataDAO;

    @Override
    protected void doCreate() {
        // TODO Auto-generated method stub
        logOperDAO = getDAO(ServiceConstants.LogOperData_DAO, LogOperDataDAO.class);
        knowledgeDataDAO = getDAO(ServiceConstants.KNOWLEDGEDATA_DAO, KnowledgeDataDAO.class);
    }

    @Override
    protected void doRemove() {
        // TODO Auto-generated method stub

    }

    @Override
    public void save(LogOperData logOper) {
        // TODO Auto-generated method stub
        logOperDAO.save(logOper);
    }

    

}
