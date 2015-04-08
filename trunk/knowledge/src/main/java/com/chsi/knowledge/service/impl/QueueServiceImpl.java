package com.chsi.knowledge.service.impl;

import com.chsi.framework.queue.MessageQueue;
import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.Constants;
import com.chsi.knowledge.service.QueueService;

public class QueueServiceImpl implements QueueService{

    private MessageQueue queue;
    
    public MessageQueue getQueue() {
        return queue;
    }

    public void setQueue(MessageQueue queue) {
        this.queue = queue;
    }

    @Override
    public void addVisitKnowledgeId(String knowledgeId) {
        if(!ValidatorUtil.isNull(knowledgeId))
            queue.enqueue(Constants.QUEUE_VISIT_KNOWLEDGEID_NAME,knowledgeId);
    }

    @Override
    public String getVisitKnowledgeId() {
        Object knowledgeId = queue.dequeue(Constants.QUEUE_VISIT_KNOWLEDGEID_NAME);
        if (null == knowledgeId)
            return null;
        return knowledgeId.toString();
    }

}
