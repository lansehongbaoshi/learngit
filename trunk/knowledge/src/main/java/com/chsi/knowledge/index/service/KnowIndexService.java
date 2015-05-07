package com.chsi.knowledge.index.service;

import com.chsi.knowledge.vo.KnowListVO;


public interface KnowIndexService {

   void deleteKnowIndex(String knowledgeId);
   
   void updateKnowIndex(String knowledgeId);
   
   KnowListVO searchKnow(String keywords, String systemId, int start, int pageSize);
}
