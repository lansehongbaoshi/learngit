package com.chsi.knowledge.index.service;

import com.chsi.knowledge.vo.KnowListVO;
import com.chsi.search.client.vo.KnowledgeVO;

/**
 * 搜索引擎索引操作接口
 * @author chenjian
 *
 */
public interface KnowIndexService {

   void deleteKnowIndex(String knowledgeId);
   
   void updateKnowIndex(String knowledgeId);
   
   /**
    * 限定系统搜索
    * @param keywords
    * @param systemId
    * @param start
    * @param pageSize
    * @return
    */
   KnowListVO<KnowledgeVO> searchKnow(String keywords, String systemId, int start, int pageSize);
   
   /**
    * 不限系统搜索
    * @param keywords
    * @param start
    * @param pageSize
    * @return
    */
   KnowListVO<KnowledgeVO> searchKnow(String keywords, int start, int pageSize);
}
