package com.chsi.knowledge.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chsi.knowledge.Cache;
import com.chsi.knowledge.vo.KnowledgeVisitVO;

public class CacheUtil {
    
    public static Map<String, List<KnowledgeVisitVO>> addVisitKnowledgeCache(String knowledgeId,String visitType){
        Cache cache = Cache.getCache();
        Map<String, List<KnowledgeVisitVO>> visitKnowledgeCache = cache.getVisitKnowledgeCache();
        if(visitKnowledgeCache.containsKey(knowledgeId)){
            List<KnowledgeVisitVO> listKnowledgeVisitVO = visitKnowledgeCache.get(knowledgeId);
            if(listKnowledgeVisitVO == null){
                KnowledgeVisitVO knowledgeVisitVO;
                if("M".equals(visitType)){
                    //移动端
                    knowledgeVisitVO = new KnowledgeVisitVO(knowledgeId,visitType,1);
                }else{
                    //网页端
                    knowledgeVisitVO = new KnowledgeVisitVO(knowledgeId,visitType,1);
                }
                List<KnowledgeVisitVO> list = new ArrayList<KnowledgeVisitVO>();
                list.add(knowledgeVisitVO);
                visitKnowledgeCache.put(knowledgeId, list);
                
            }else{
                boolean isExist = false;
                for(KnowledgeVisitVO knowledgeVisitVO : listKnowledgeVisitVO){
                    if(knowledgeVisitVO.getVisitType().equals(visitType)){
                        knowledgeVisitVO.setVisitCNT(knowledgeVisitVO.getVisitCNT()+1);
                        isExist = true;
                    }
                }
                if(!isExist){
                    KnowledgeVisitVO knowledgeVisitVO;
                    if("M".equals(visitType)){
                        //移动端
                        knowledgeVisitVO = new KnowledgeVisitVO(knowledgeId,visitType,1);
                    }else{
                        //网页端
                        knowledgeVisitVO = new KnowledgeVisitVO(knowledgeId,visitType,1);
                    }
                    List<KnowledgeVisitVO> list = new ArrayList<KnowledgeVisitVO>();
                    list.add(knowledgeVisitVO);
                    visitKnowledgeCache.put(knowledgeId, list);
                }
            }
        }else{
            KnowledgeVisitVO knowledgeVisitVO;
            if("M".equals(visitType)){
                //移动端
                knowledgeVisitVO = new KnowledgeVisitVO(knowledgeId,visitType,1);
            }else{
                //网页端
                knowledgeVisitVO = new KnowledgeVisitVO(knowledgeId,visitType,1);
            }
            List<KnowledgeVisitVO> list = new ArrayList<KnowledgeVisitVO>();
            list.add(knowledgeVisitVO);
            visitKnowledgeCache.put(knowledgeId, list);
        }
        return visitKnowledgeCache;
    }
}
