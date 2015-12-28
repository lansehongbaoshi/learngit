package com.chsi.knowledge.service.impl;

import java.util.List;

import com.chsi.framework.pojos.PersistentObject;
import com.chsi.framework.service.BaseDbService;
import com.chsi.knowledge.ServiceConstants;
import com.chsi.knowledge.dao.SearchDAO;
import com.chsi.knowledge.service.SearchService;

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
    
}
