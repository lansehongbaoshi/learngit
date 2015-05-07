package com.chsi.knowledge.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.chsi.cms.client.CmsServiceClient;
import com.chsi.cms.client.CmsServiceClientFactory;
import com.chsi.framework.service.BaseDbService;
import com.chsi.knowledge.ServiceConstants;
import com.chsi.knowledge.dao.KnowTagRelationDataDAO;
import com.chsi.knowledge.dao.KnowledgeDataDAO;
import com.chsi.knowledge.dao.TagDataDAO;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.pojo.KnowTagRelationData;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.pojo.TagData;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.ServiceFactory;
import com.chsi.knowledge.service.TagService;
import com.chsi.knowledge.util.ManageCacheUtil;
import com.chsi.knowledge.util.Navigation;
import com.chsi.knowledge.util.NavigationUtil;
import com.chsi.knowledge.util.Pagination;
import com.chsi.knowledge.vo.KnowListVO.Know;
import com.chsi.knowledge.vo.ViewKnowVO;
import com.chsi.knowledge.vo.ViewKnowVO.ConKnow;
import com.chsi.knowledge.vo.ViewKnowsVO;
import com.chsi.knowledge.vo.ViewTagVO;
import com.chsi.news.type.ArticleStatusType;
import com.chsi.news.vo.Article;

public class KnowledgeServiceImpl extends BaseDbService implements KnowledgeService {

    private KnowledgeDataDAO knowledgeDataDAO;
    private TagDataDAO tagDataDAO;
    private KnowTagRelationDataDAO knowTagRelationDAO;

    @Override
    protected void doCreate() {
        knowledgeDataDAO = getDAO(ServiceConstants.KNOWLEDGEDATA_DAO, KnowledgeDataDAO.class);
        tagDataDAO = getDAO(ServiceConstants.TAGDATA_DAO, TagDataDAO.class);
        knowTagRelationDAO = getDAO(ServiceConstants.KNOWTAGRELATIONDATA_DAO, KnowTagRelationDataDAO.class);
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
    public ViewKnowVO getKnowledgeVOById(String id, String tagId) {
        KnowledgeData knowledgeData = null;
        KnowTagRelationData ktRelation = null;
        List<KnowTagRelationData> list = ManageCacheUtil.getKnowTag(tagId);
        if (null != list) {
            for (KnowTagRelationData knowTag : list) {
                if (id.equals(knowTag.getKnowledgeData().getId())){
                    knowledgeData = knowTag.getKnowledgeData();
                    ktRelation = knowTag;
                }
            }
        }

        if (null == knowledgeData){
            knowledgeData = knowledgeDataDAO.getKnowledgeById(id);
            ktRelation = knowTagRelationDAO.getKnowTagRelationByKnowId(id, tagId);
        }
           
        if (null == knowledgeData)
            return null;
        CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
        Article article = cmsServiceClient.getArticle(knowledgeData.getCmsId());
        if (null == article)
            return null;
        ConKnow conKnow = new ConKnow(knowledgeData.getId(),
                                      article.getTitle(), article.getContent(),
                                      knowledgeData.getKeywords(), knowledgeData.getVisitCnt(),
                                      knowledgeData.getUpdateTime());
        List<Navigation> navigation = NavigationUtil.getNavigation(
                                             ktRelation.getTagData().getSystemData(),
                                             ktRelation.getTagData(), article.getTitle(), id);
        ViewKnowVO knowPageVO = new ViewKnowVO(conKnow, navigation);
        return knowPageVO;
    }

    @Override
    public ViewKnowsVO getViewKnowsVO(SystemData system, String tagId, KnowledgeStatus knowledgeStatus, int start, int pageSize) {
        int count;
        ViewKnowsVO viewKnowsVO = null;
        List<Navigation> navigation = null;
        TagService service = ServiceFactory.getTagService();
        List<ViewTagVO> tagVOList = service.getTagVOsBySystemIdAndStatus(system.getId(), knowledgeStatus);
        List<KnowTagRelationData> list = ManageCacheUtil.getKnowTag(tagId);
        if (null == list)
            count = knowledgeDataDAO.countKnowledges(tagId, knowledgeStatus);
        else
            count = list.size();
        
        if (count == 0) {
             navigation = NavigationUtil.getNavigation(system, null, null, null);
             viewKnowsVO = new ViewKnowsVO(null, tagVOList, navigation, null);
             return viewKnowsVO;
        }
        if (start >= count || start < 0) {
            start = 0;
        }
        CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
        
        List<KnowledgeData> knowledgeDataList = new ArrayList<KnowledgeData>();
        if (null == list) {
            list = knowTagRelationDAO.getKnowTagDatas(tagId, knowledgeStatus); 
            ManageCacheUtil.addKnowTag(tagId, list);
        } 
        int size = (pageSize + start) >= list.size() ? list.size() : (pageSize + start);
        for (int i = start; i < size; i++) {
            knowledgeDataList.add(list.get(i).getKnowledgeData());
        }
        
        //分页情况数据
        Pagination pagination = new Pagination(count, pageSize, start / pageSize + 1);
        //数据列表
        List<Know> knows = new ArrayList<Know>();
        Know know = null;
        Article article = null;
        for (KnowledgeData knowledgeData : knowledgeDataList) {
            article = cmsServiceClient.getArticle(knowledgeData.getCmsId());
            know = new Know(article.getTitle());
            know.addParam("tagId", tagId);
            know.addParam("id", knowledgeData.getId());
            knows.add(know);
        }
        
        List<TagData> tagList = ManageCacheUtil.getTagList(system.getId());
        TagData tagData = null;
        if (null != tagId) {
            if (null != tagList && tagList.size() > 0) {
                for (TagData temp : tagList) {
                    if (tagId.equals(temp.getId()))
                        tagData = temp;
                }
            } else {
                tagData = tagDataDAO.getTagData(tagId);
            }
        }
        navigation = NavigationUtil.getNavigation(tagData.getSystemData(), tagData, null, null);  
        viewKnowsVO = new ViewKnowsVO(knows, tagVOList, navigation, pagination);
        return viewKnowsVO;
    }

    @Override
    public void updateVisitCntPlusOne(String id) {
        knowledgeDataDAO.updateVisitCntPlusOne(id);
    }

    @Override
    public KnowledgeData getKnowledgeById(String id) {
        return knowledgeDataDAO.getKnowledgeById(id);
    }

    @Override
    public List<KnowledgeData> get() {
        return knowledgeDataDAO.get();
    }

}
