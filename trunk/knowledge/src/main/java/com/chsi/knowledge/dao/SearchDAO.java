package com.chsi.knowledge.dao;

import java.util.List;

import com.chsi.framework.pojos.PersistentObject;

public interface SearchDAO {
    void save(PersistentObject po);
    
    List<String> getTopKeyword(int n);
    
    List<String> getTopVisitKnowl(int n);
}
