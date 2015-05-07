package com.chsi.knowledge.index.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.chsi.cms.client.CmsServiceClient;
import com.chsi.cms.client.CmsServiceClientFactory;
import com.chsi.framework.page.Page;
import com.chsi.framework.queue.MessageQueue;
import com.chsi.framework.service.BaseDbService;
import com.chsi.knowledge.ServiceConstants;
import com.chsi.knowledge.dao.KnowTagRelationDataDAO;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.index.service.KnowIndexService;
import com.chsi.knowledge.pojo.KnowTagRelationData;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.util.Pagination;
import com.chsi.knowledge.vo.KnowListVO;
import com.chsi.news.vo.Article;
import com.chsi.search.client.SearchServiceClient;
import com.chsi.search.client.SearchServiceClientFactory;
import com.chsi.search.client.vo.KnowledgeVO;
import com.chsi.search.common.indexdata.KnowIndexData;
import com.chsi.search.proto.SearchProtos;

public class KnowIndexServiceImpl extends BaseDbService implements KnowIndexService {

    private KnowTagRelationDataDAO knowTagRelationDAO;
    @Override
    protected void doCreate() {
        knowTagRelationDAO = getDAO(ServiceConstants.KNOWTAGRELATIONDATA_DAO, KnowTagRelationDataDAO.class);
        
    }

    @Override
    protected void doRemove() {
        
    }
    
    @Override
    public void deleteKnowIndex(String knowledgeId) {
        if (StringUtils.isNotBlank(knowledgeId)) {
            SearchProtos.SearchIndexInfo.Builder builder = SearchProtos.SearchIndexInfo.newBuilder();
            builder.setSearchIndexId(knowledgeId);
            SearchProtos.SearchIndexInfo indexInfo = builder.build();
            SearchServiceClient searchClient = SearchServiceClientFactory.getSearchServiceClient();
            MessageQueue knowQueue = searchClient.getQueue();
            knowQueue.enqueue("knowindexdeleter", indexInfo.toByteArray());   //名称须与SOLR配置的queueName 相同
        }
    }

    @Override
    public void updateKnowIndex(String knowledgeId) {
        SearchServiceClient searchClient = SearchServiceClientFactory.getSearchServiceClient();
        searchClient.updateKnow(setKnowIndexData(knowledgeId));
    }

    private KnowIndexData setKnowIndexData(String knowledgeId){
        List<KnowTagRelationData> relation=knowTagRelationDAO.getKnowTagDatas(KnowledgeStatus.WSH, knowledgeId);
        CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
        Article article = cmsServiceClient.getArticle(relation.get(0).getKnowledgeData().getCmsId());
        KnowledgeData know=relation.get(0).getKnowledgeData();
        List<String> tags=new LinkedList<String>();
        for(KnowTagRelationData k : relation){
            tags.add(k.getTagData().getName());
        }
        KnowIndexData index=new KnowIndexData();
        index.setId(know.getId());
        index.setKeywords(know.getKeywords());
        index.setTitle(article.getTitle());
        index.setContent(article.getContent());
        index.setSort(know.getSort());
        index.setVisitCnt(know.getVisitCnt());
        index.setSystemId(relation.get(0).getTagData().getSystemData().getId());
        index.setCreateTime(know.getCreateTime().getTime());
        index.setUpdateTime(know.getUpdateTime().getTime());
        index.setTags(tags);
        return index;
    }

    @Override
    public KnowListVO searchKnow(String keywords, String systemId, int start,
            int pageSize) {
        SearchServiceClient searchClient = SearchServiceClientFactory.getSearchServiceClient();
        Page<KnowledgeVO> page = searchClient.searchKnow(keywords, systemId, start, pageSize);
        List<KnowListVO.Know> knowList=new LinkedList<KnowListVO.Know>();
        for(KnowledgeVO vo : page.getList()){
            KnowListVO.Know tempKnow=new KnowListVO.Know(vo.getTitle());
            tempKnow.addParam("id", vo.getKnowledgeId());
            knowList.add(tempKnow);
        }
        
        Pagination pagination = new Pagination(page.getTotalCount(),page.getPageCount(),page.getCurPage());
        KnowListVO knowListVO=new KnowListVO(knowList,pagination);
        
        return knowListVO;
    }


}
