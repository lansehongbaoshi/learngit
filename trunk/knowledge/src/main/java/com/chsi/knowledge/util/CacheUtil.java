package com.chsi.knowledge.util;

import java.util.Map;

import com.chsi.knowledge.Cache;

public class CacheUtil {
    
    public static Map<String, Integer> addVisitKnowledgeCache(String knowledgeId){
        Cache cache = Cache.getCache();
        System.out.println("获取自定义缓存："+cache);
        Map<String, Integer> visitKnowledgeCache = cache.getVisitKnowledgeCache();
        System.out.println("放置访问数量的map缓存："+visitKnowledgeCache+";数量："+visitKnowledgeCache.size());
        if(visitKnowledgeCache.containsKey(knowledgeId)){
            Integer count = visitKnowledgeCache.get(visitKnowledgeCache);
            visitKnowledgeCache.put(knowledgeId, count+1);
        }else{
            visitKnowledgeCache.put(knowledgeId, 1);
        }
        return visitKnowledgeCache;
    }
}
