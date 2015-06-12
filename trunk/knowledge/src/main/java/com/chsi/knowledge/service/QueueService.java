package com.chsi.knowledge.service;
/**
 * 队列服务
 * @author chenjian
 *
 */
public interface QueueService {
    
    void addVisitKnowledgeId(String knowledgeId);
    
    String getVisitKnowledgeId();

}
