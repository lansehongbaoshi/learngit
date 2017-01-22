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
    public static Map<String, Integer> visitKnowledgeCache = new HashMap<String, Integer>();
    
    public static Map<String, Integer> getVisitKnowledgeCache() {
        if(visitKnowledgeCache==null){
            visitKnowledgeCache = new HashMap<String, Integer>();
        }
        return visitKnowledgeCache;
    }

    public void setVisitKnowledgeCache(Map<String, Integer> visitKnowledgeCache) {
        this.visitKnowledgeCache = visitKnowledgeCache;
    }
    
}
