package com.chsi.knowledge.service;

import java.io.File;

public interface RobotSolrIndexService {

    /**
     * 调用Solr，直接删除索引
     * 
     * @param knowledgeId
     */
    public void deleteKnowIndexBySolr();

    public void updateAllRobotIndex();

    public void ImportDialogue(File file);

}
