package com.chsi.knowledge.service;

import com.chsi.knowledge.pojo.SearchLogData;

/**
 * 队列服务
 * 
 * @author chenjian
 * 
 */
public interface QueueService {

    void addVisitKnowledgeId(String knowledgeId);

    String getVisitKnowledgeId();

    void addCtiVisitKnowledgeId(String knowledgeId);

    String getCtiVisitKnowledgeId();

    void addSearchLog(SearchLogData data);

    SearchLogData getSearchLog();
}
