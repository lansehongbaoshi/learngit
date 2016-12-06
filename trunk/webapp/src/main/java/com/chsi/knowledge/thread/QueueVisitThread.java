package com.chsi.knowledge.thread;

import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.QueueService;
import com.chsi.knowledge.service.ServiceFactory;

/**
 * 队列线程，用于更新每个知识的访问次数
 * 
 * @author chenjian
 */
public class QueueVisitThread extends BaseThread {

    private QueueService queueService;
    private KnowledgeService knowledgeService;

    public QueueVisitThread() {
        super();
        queueService = ServiceFactory.getQueueService();
        knowledgeService = ServiceFactory.getKnowledgeService();
    }

    @Override
    public void doRun() {
        String knowledgeId = null;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                knowledgeId = queueService.getVisitKnowledgeId();
                if (null != knowledgeId) {
                    knowledgeService.updateVisitCntPlusOne(knowledgeId);
                }
                sleep(100);
            } catch (Exception e) {
                log.error(e);
            }
        }

    }

}
