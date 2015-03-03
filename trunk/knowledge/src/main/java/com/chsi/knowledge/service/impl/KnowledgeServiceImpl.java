package com.chsi.knowledge.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.chsi.cms.client.CmsServiceClient;
import com.chsi.cms.client.CmsServiceClientFactory;
import com.chsi.framework.page.Page;
import com.chsi.framework.page.PageUtil;
import com.chsi.framework.service.BaseDbService;
import com.chsi.knowledge.ServiceConstants;
import com.chsi.knowledge.dao.KnowledgeDataDAO;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.vo.KnowledgeVO;
import com.chsi.news.type.ArticleStatusType;
import com.chsi.news.vo.Article;

public class KnowledgeServiceImpl extends BaseDbService implements KnowledgeService {

    private KnowledgeDataDAO knowledgeDataDAO;

    @Override
    protected void doCreate() {
        knowledgeDataDAO = getDAO(ServiceConstants.KNOWLEDGEDATA_DAO, KnowledgeDataDAO.class);
    }

    @Override
    protected void doRemove() {
    }

    @Override
    public void save(KnowledgeData knowledgeData,String articleTitle, String articleContent, ArticleStatusType articleStatusType,String ssdm,String createBy) {
          Article article = Article.getInstance4Create(articleTitle, articleContent, articleStatusType, ssdm, createBy);
          CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
          String articleId=cmsServiceClient.saveOrUpdateArticle(article);
          knowledgeData.setCmsId(articleId);
          knowledgeDataDAO.save(knowledgeData); 
    }

    @Override
    public void update(KnowledgeData knowledgeData, String id, String articleTitle, String articleContent, ArticleStatusType articleStatusType, String updateBy) {
        Article article = Article.getInstance4Update(id, articleTitle, articleContent, articleStatusType, updateBy);
        CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
        cmsServiceClient.saveOrUpdateArticle(article);
        knowledgeDataDAO.update(knowledgeData);
    }

    @Override
    public KnowledgeVO getKnowledgeVOById(String id) {
        KnowledgeData knowledgeData = knowledgeDataDAO.getKnowledgeById(id);
        if (null == knowledgeData)  return null;
        CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
        Article article = cmsServiceClient.getArticle(knowledgeData.getCmsId());
        if (null == article)   return null;
        KnowledgeVO KnowledgeVO = new KnowledgeVO(knowledgeData, article);
        return KnowledgeVO;
    }

    @Override
    public Page<KnowledgeVO> getKnowledgeVOPage(String systemId, String tagId, KnowledgeStatus knowledgeStatus, int start, int pageSize) {
        Long count = knowledgeDataDAO.countKnowledges(systemId, tagId, knowledgeStatus);
        if (count == 0) {
            return Page.EMPTY_PAGE;
        }
        if (start >= count) {
            start = 0;
        }
        CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
        List<KnowledgeData> KnowledgeDataList = knowledgeDataDAO.getKnowledges(systemId, tagId, knowledgeStatus, start, pageSize);
        List<KnowledgeVO> KnowledgeVOList = new ArrayList<KnowledgeVO>();
        KnowledgeVO knowledgeVO = null;
        Article article = null;
        for (KnowledgeData knowledgeData : KnowledgeDataList) {
            article = cmsServiceClient.getArticle(knowledgeData.getCmsId());
            knowledgeVO = new KnowledgeVO(knowledgeData, article);
            KnowledgeVOList.add(knowledgeVO);
        }
        return PageUtil.getPage(KnowledgeVOList.iterator(), start, pageSize, count);
    }

    @Override
    public void update(KnowledgeData knowledgeData) {
        knowledgeDataDAO.update(knowledgeData);
    }

    @Override
    public void updateVisitCntPlusOne(String id) {
        knowledgeDataDAO.updateVisitCntPlusOne(id);
    }

}
