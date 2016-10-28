package com.chsi.knowledge.index.service;

import java.util.Map;

import com.chsi.knowledge.vo.KnowListVO;
import com.chsi.search.client.vo.KnowledgeVO;

/**
 * 搜索引擎索引操作接口
 * @author chenjian
 *
 */
public interface KnowIndexService {

    /**
     * 通过加入队列来删除索引
     * @param knowledgeId
     */
   void deleteKnowIndex(String knowledgeId);
   
   /**
    * 调用Solr，直接删除索引
    * @param knowledgeId
    */
   void deleteKnowIndexBySolr(String knowledgeId);
   
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
   @Deprecated
   KnowListVO<KnowledgeVO> searchKnow(String keywords, int start, int pageSize);
   
   /**
    * 通用搜索，很灵活 自由配置参数
    * @param queryParams 接口里会另外配置qf=title^25 content^10 key_words^6 tags^5",
    *                     defType=edismax,
    *                     bf=recip(rord(visit_cnt),1,1000,1000)^50 recip(rord(sort),1,100,100)^1
    * @param start
    * @param pageSize
    * @return
    */
   KnowListVO<KnowledgeVO> searchKnow(Map<String, String> queryParams, int start, int pageSize);
   
   /**
    * 
    * @param queryParams
    * @param start
    * @param pageSize
    */
   KnowListVO<KnowledgeVO> customSearch(Map<String, String> queryParams, int start, int pageSize);

   /**
    * 查询相同或者类似的Title
    * @param title
    * @return
    */
   com.chsi.search.client.vo.RepeatVO<KnowledgeVO> getRepeatKnows(String knowId,String title);

   
   /**
    * 刷新系统所有知识索引
    */
   void updateAllKnowledgeIndex();

}
