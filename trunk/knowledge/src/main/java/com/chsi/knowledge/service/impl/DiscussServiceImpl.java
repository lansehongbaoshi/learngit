package com.chsi.knowledge.service.impl;

import com.chsi.framework.service.BaseDbService;
import com.chsi.knowledge.ServiceConstants;
import com.chsi.knowledge.dao.DiscussDataDAO;
import com.chsi.knowledge.pojo.DiscussData;
import com.chsi.knowledge.service.DiscussService;

public class DiscussServiceImpl extends BaseDbService implements DiscussService {

    private DiscussDataDAO discussDataDAO;
    @Override
    protected void doCreate() {
        discussDataDAO=getDAO(ServiceConstants.DISCUSSDATA_DAO, DiscussDataDAO.class);
    }

    @Override
    protected void doRemove() {
       
    }

    @Override
    public void saveOrUpdate(DiscussData discussData) {
        discussDataDAO.saveOrUpdate(discussData);
    }

   /* @Override
    public DiscussVO getDiscussVOByKnowledgeId(String knowledgeId) {
        List<DiscussData> list = discussDataDAO.getDiscusssByKnowledgeId(knowledgeId);
        KnowledgeService knowledgeService = ServiceFactory.getKnowledgeService();
        KnowledgeVO knowledgeVO = knowledgeService.getKnowledgeVOById(knowledgeId);
        DiscussVO discussVO = new DiscussVO();
        discussVO.setDiscussDataList(list);
        discussVO.setKnowledgeVO(knowledgeVO);
        return discussVO;
    }*/
   
}