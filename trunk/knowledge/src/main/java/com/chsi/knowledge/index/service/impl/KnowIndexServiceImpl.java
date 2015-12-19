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
import com.chsi.search.type.QueueNameType;

public class KnowIndexServiceImpl extends BaseDbService implements KnowIndexService {

    private KnowTagRelationDataDAO knowTagRelationDAO;

    protected void doCreate() {
        knowTagRelationDAO = getDAO(ServiceConstants.KNOWTAGRELATIONDATA_DAO, KnowTagRelationDataDAO.class);
    }

    protected void doRemove() {
        
    }
    
    public void deleteKnowIndex(String knowledgeId) {
        if (StringUtils.isNotBlank(knowledgeId)) {
            SearchProtos.SearchIndexInfo.Builder builder = SearchProtos.SearchIndexInfo.newBuilder();
            builder.setSearchIndexId(knowledgeId);
            SearchProtos.SearchIndexInfo indexInfo = builder.build();
            SearchServiceClient searchClient = SearchServiceClientFactory.getSearchServiceClient();
            MessageQueue knowQueue = searchClient.getQueue();
            knowQueue.enqueue(QueueNameType.KNOW_INDEX_DELETER.toString(), indexInfo.toByteArray());   //名称须与SOLR配置的queueName 相同
        }
    }

    public void updateKnowIndex(String knowledgeId) {
        SearchServiceClient searchClient = SearchServiceClientFactory.getSearchServiceClient();
        searchClient.updateKnow(setKnowIndexData(knowledgeId));
    }

    private KnowIndexData setKnowIndexData(String knowledgeId){
        List<KnowTagRelationData> relation = knowTagRelationDAO.getKnowTagDatas(KnowledgeStatus.YSH, knowledgeId);
        CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
        Article article = cmsServiceClient.getArticle(relation.get(0).getKnowledgeData().getCmsId());
        KnowledgeData know = relation.get(0).getKnowledgeData();
        List<String> tags=new LinkedList<String>();
        List<String> tagIds=new LinkedList<String>();
        for(KnowTagRelationData k : relation){
            tags.add(k.getTagData().getName());
            tagIds.add(k.getTagData().getId());
        }
        KnowIndexData index=new KnowIndexData();
        index.setId(know.getId());
        index.setKeywords(know.getKeywords());
        index.setTitle(article.getTitle());
        index.setContent(article.getContent());
        index.setSystemId(relation.get(0).getTagData().getSystemData().getId());
        index.setTagIds(tagIds);
        index.setTags(tags);
        index.setSort(know.getSort());
        return index;
    }

    @Override
    public KnowListVO<KnowledgeVO> searchKnow(String keywords, String systemId, int start, int pageSize) {
        SearchServiceClient searchClient = SearchServiceClientFactory.getSearchServiceClient();
        if (start < 0) {
            start = 0;
        }
        Page<KnowledgeVO> page = searchClient.searchKnow(keywords, systemId, start, pageSize);
        Pagination pagination = new Pagination(page.getTotalCount(), page.getPageCount(), page.getCurPage());
        KnowListVO<KnowledgeVO> knowListVO = new KnowListVO<KnowledgeVO>(page.getList(), pagination);
        return knowListVO;
    }

    @Override
    public KnowListVO<KnowledgeVO> searchKnow(String keywords, int start, int pageSize) {
        SearchServiceClient searchClient = SearchServiceClientFactory.getSearchServiceClient();
        if (start < 0) {
            start = 0;
        }
        Page<KnowledgeVO> page = searchClient.searchKnow(keywords, start, pageSize);
        Pagination pagination = new Pagination(page.getTotalCount(), page.getPageCount(), page.getCurPage());
        KnowListVO<KnowledgeVO> knowListVO = new KnowListVO<KnowledgeVO>(page.getList(), pagination);
        return knowListVO;
    }


}
