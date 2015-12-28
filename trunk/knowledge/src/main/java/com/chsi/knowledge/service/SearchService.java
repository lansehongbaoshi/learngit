package com.chsi.knowledge.service;

import java.util.List;

import com.chsi.framework.pojos.PersistentObject;

public interface SearchService {
    void save(PersistentObject po);
    
    /**
     * 获取搜索频次最多的前n个关键词
     * @param n
     * @return
     */
    List<String> getTopKeyword(int n);
}
