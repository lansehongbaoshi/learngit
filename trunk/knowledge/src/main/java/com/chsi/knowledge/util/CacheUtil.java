package com.chsi.knowledge.util;

import java.util.Map;

import com.chsi.knowledge.Cache;

public class CacheUtil {
    
    public static Map<String, Integer> addVisitKnowledgeCache(String knowledgeId){
        Cache cache = Cache.getCache();
        Map<String, Integer> visitKnowledgeCache = cache.getVisitKnowledgeCache();
        if(visitKnowledgeCache.containsKey(knowledgeId)){
            Integer count = visitKnowledgeCache.get(knowledgeId);
            if(count == null){
                count = 0;
            }
            visitKnowledgeCache.put(knowledgeId, count+1);
        }else{
            visitKnowledgeCache.put(knowledgeId, 1);
        }
        return visitKnowledgeCache;
    }
}
