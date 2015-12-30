package com.chsi.knowledge.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.chsi.framework.pojos.PersistentObject;
import com.chsi.framework.service.BaseDbService;
import com.chsi.knowledge.ServiceConstants;
import com.chsi.knowledge.dao.SearchDAO;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.SearchService;
import com.chsi.knowledge.service.ServiceFactory;

public class SearchServiceImpl extends BaseDbService implements SearchService {
    private SearchDAO searchDAO;

    protected void doCreate() {
        searchDAO = getDAO(ServiceConstants.SEARCH_DAO, SearchDAO.class);
    }

    protected void doRemove() {
        
    }

    public void save(PersistentObject po) {
        searchDAO.save(po);
    }

    public List<String> getTopKeyword(int n) {
        return searchDAO.getTopKeyword(n);
    }

    public List<KnowledgeData> getTopKnowl(int n) {
        List<String> knowIds = searchDAO.getTopVisitKnowl(n);
        KnowledgeService knowledgeService = ServiceFactory.getKnowledgeService();
        List<KnowledgeData> result = new ArrayList<KnowledgeData>();
        for(String id:knowIds) {
            KnowledgeData data = knowledgeService.getKnowledgeWithArticleById(id);
            if(data!=null) {
                result.add(data);
            }
        }
        return result;
    }
    
}
