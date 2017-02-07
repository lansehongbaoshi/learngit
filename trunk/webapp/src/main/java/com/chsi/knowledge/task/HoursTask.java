package com.chsi.knowledge.task;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimerTask;

import com.chsi.knowledge.Cache;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.ServiceFactory;
import com.chsi.knowledge.vo.KnowledgeVisitVO;

public class HoursTask extends TimerTask {

    private KnowledgeService knowledgeService = ServiceFactory.getKnowledgeService();
    @Override
    public void run() {
        // TODO Auto-generated method stub
        Cache cache = Cache.getCache();
        Map<String, List<KnowledgeVisitVO>> visitKnowledgeCache = cache.getVisitKnowledgeCache();
        if(visitKnowledgeCache.size()>0){
            for(Map.Entry<String, List<KnowledgeVisitVO>> entry : visitKnowledgeCache.entrySet()){
                String id = entry.getKey();
                List<KnowledgeVisitVO> listKnowledgeVisitVO = entry.getValue();
                int total = 0;
                int webTotal = 0;
                int mTotal = 0;
                for(KnowledgeVisitVO knowledgeVisitVO : listKnowledgeVisitVO){
                    total += knowledgeVisitVO.getVisitCNT();
                    if("M".equals(knowledgeVisitVO.getVisitType())){
                        mTotal += knowledgeVisitVO.getVisitCNT();
                    }else{
                        webTotal += knowledgeVisitVO.getVisitCNT();
                    }
                }
                KnowledgeData knowledgeData = new KnowledgeData();
                knowledgeData.setId(id);
                knowledgeData.setVisitCnt(total);
                knowledgeData.setWebVisitCnt(webTotal);
                knowledgeData.setmVisitCnt(mTotal);
                knowledgeService.updateVisitCnt(knowledgeData);
//                knowledgeService.updateVisitCntPlus(id, count);
            }
            visitKnowledgeCache.clear();
        }
    }

}
