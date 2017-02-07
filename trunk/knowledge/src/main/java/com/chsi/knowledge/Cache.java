package com.chsi.knowledge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chsi.knowledge.vo.KnowledgeVisitVO;

public class Cache {
    /*
     * 构造方法私有化实现单一模式
     */
    private Cache(){}
    
    public static Cache cache ;
    public static Cache getCache(){
        if(cache == null){
            cache = new Cache();
        }
        return cache;
    }
    
    /*增加访问知识记录访问的知识的id和次数*/
    public static Map<String, List<KnowledgeVisitVO>> visitKnowledgeCache = new HashMap<String, List<KnowledgeVisitVO>>();
    
    public static Map<String, List<KnowledgeVisitVO>> getVisitKnowledgeCache() {
        if(visitKnowledgeCache==null){
            visitKnowledgeCache = new HashMap<String, List<KnowledgeVisitVO>>();
        }
        return visitKnowledgeCache;
    }

    public void setVisitKnowledgeCache(Map<String, List<KnowledgeVisitVO>> visitKnowledgeCache) {
        this.visitKnowledgeCache = visitKnowledgeCache;
    }
    
}
