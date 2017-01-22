package com.chsi.knowledge.task;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TimerTask;

import com.chsi.knowledge.Cache;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.ServiceFactory;

public class HoursTask extends TimerTask {

    private KnowledgeService knowledgeService = ServiceFactory.getKnowledgeService();
    @Override
    public void run() {
        // TODO Auto-generated method stub
        Cache cache = Cache.getCache();
        Map<String, Integer> visitKnowledgeCache = cache.getVisitKnowledgeCache();
        if(visitKnowledgeCache.size()>0){
            for(Map.Entry<String, Integer> entry : visitKnowledgeCache.entrySet()){
                String id = entry.getKey();
                int count = entry.getValue();
                System.out.println("id:"+id+";count:"+count);
                knowledgeService.updateVisitCntPlus(id, count);
            }
            visitKnowledgeCache.clear();
        }
    }

}
