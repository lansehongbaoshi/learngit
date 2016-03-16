package com.chsi.knowledge.service.impl;

import java.util.List;

import com.chsi.framework.service.BaseDbService;
import com.chsi.knowledge.ServiceConstants;
import com.chsi.knowledge.dao.KnowTagRelationDataDAO;
import com.chsi.knowledge.dic.KnowledgeStatus;
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

    @Override
    public List<KnowTagRelationData> getKnowTagDatas(KnowledgeStatus knowledgeStatus, String knowledgeId) {
        return knowTagRelationDataDAO.getKnowTagDatas(knowledgeStatus, knowledgeId);
    }

    public KnowTagRelationData getKnowTagRelationByKnowId(String knowledgeId, String tagId) {
        return knowTagRelationDataDAO.getKnowTagRelationByKnowId(knowledgeId, tagId);
    }

    @Override
    public int del(String knowledgeId) {
        return knowTagRelationDataDAO.del(knowledgeId);
    }

    @Override
    public List<KnowTagRelationData> getKnowTagRelationByKnowId(String knowledgeId) {
        return knowTagRelationDataDAO.getKnowTagRelationByKnowId(knowledgeId);
    }

    @Override
    public List<KnowTagRelationData> getKnowTagDatas(String tagId) {
        return knowTagRelationDataDAO.getKnowTagDatas(tagId);
    }

    @Override
    public List<KnowTagRelationData> getAllKnowTagDatas(String tagId) {
        return knowTagRelationDataDAO.getAllKnowTagDatas(tagId);
    }
    
}
