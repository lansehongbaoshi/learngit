package com.chsi.knowledge.service;

import java.util.List;

import com.chsi.framework.pojos.PersistentObject;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.search.client.vo.KnowledgeVO;

public interface SearchService {
    void save(PersistentObject po);
    
    /**
     * 获取搜索频次最多的前n个关键词
     * @param n
     * @return
     */
    List<KnowledgeVO> getTopSearchKnow(int n);
    
    List<KnowledgeData> getTopKnowl(int n);
}
