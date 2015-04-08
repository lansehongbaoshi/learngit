package com.chsi.knowledge.thread;

import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.QueueService;
import com.chsi.knowledge.service.ServiceFactory;

public class QueueVisitThread  extends BaseThread{

    private QueueService queueService;
    private KnowledgeService knowledgeService;
    
    public QueueVisitThread(){
        queueService = ServiceFactory.getQueueService();
        knowledgeService = ServiceFactory.getKnowledgeService();
    }
    
    @Override
    public void doRun() {
        String knowledgeId = null;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                knowledgeId = queueService.getVisitKnowledgeId();
                if (null != knowledgeId)
                    knowledgeService.updateVisitCntPlusOne(knowledgeId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }

}
