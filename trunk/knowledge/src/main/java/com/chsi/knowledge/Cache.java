package com.chsi.knowledge;

import java.util.HashMap;
import java.util.Map;

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
    Map<String, Integer> visitKnowledgeCache = new HashMap<String, Integer>();
    public Map<String, Integer> getVisitKnowledgeCache() {
        return visitKnowledgeCache;
    }

    public void setVisitKnowledgeCache(Map<String, Integer> visitKnowledgeCache) {
        this.visitKnowledgeCache = visitKnowledgeCache;
    }
    
}
