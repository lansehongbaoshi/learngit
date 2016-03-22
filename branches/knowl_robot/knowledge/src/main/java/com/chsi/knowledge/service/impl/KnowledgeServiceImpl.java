package com.chsi.knowledge.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.service.TagService;
import com.chsi.knowledge.util.ManageCacheUtil;
import com.chsi.knowledge.util.Navigation;
import com.chsi.knowledge.util.NavigationUtil;
import com.chsi.knowledge.util.Pagination;
import com.chsi.knowledge.vo.ViewKnowVO;
import com.chsi.knowledge.vo.ViewKnowVO.ConKnow;
import com.chsi.knowledge.vo.ViewKnowsVO;
import com.chsi.knowledge.vo.ViewKnowsVO.Know;
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
    public void save(KnowledgeData knowledgeData, String articleTitle, String articleContent, String ssdm, String createBy) {
        Article article = Article.getInstance4Create(articleTitle, articleContent, getStatus(knowledgeData.getKnowledgeStatus()), ssdm, createBy);
        CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
        String articleId = cmsServiceClient.saveOrUpdateArticle(article);
        knowledgeData.setCmsId(articleId);
        knowledgeDataDAO.save(knowledgeData);
    }

    @Override
    public void update(KnowledgeData knowledgeData, String articleTitle, String articleContent, String updateBy) {
        Article article = Article.getInstance4Update(knowledgeData.getCmsId(), articleTitle, articleContent, getStatus(knowledgeData.getKnowledgeStatus()), updateBy);
        CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
        cmsServiceClient.saveOrUpdateArticle(article);
        knowledgeDataDAO.update(knowledgeData);
        ManageCacheUtil.removeKnowledgeDataById(knowledgeData.getId());
    }

    private ArticleStatusType getStatus(KnowledgeStatus knowledgeStatus) {
        if (knowledgeStatus == KnowledgeStatus.WSH) {
            return ArticleStatusType.WAITTING;
        } else if (knowledgeStatus == KnowledgeStatus.YSH) {
            return ArticleStatusType.PUBLISHED;
        } else if (knowledgeStatus == KnowledgeStatus.YSC) {
            return ArticleStatusType.NUPUBLISHED;
        }
        return ArticleStatusType.NUPUBLISHED;
    }

    @Override
    public ViewKnowVO getKnowVOById(String id, String tagId) {
        KnowTagRelationData ktRelation = null;
        List<KnowTagRelationData> list = ManageCacheUtil.getKnowTag(tagId);
        if (null != list) {
            for (KnowTagRelationData knowTag : list) {
                if (id.equals(knowTag.getKnowledgeData().getId())) {
                    ktRelation = knowTag;
                }
            }
        }

        if (null == ktRelation) {
            ktRelation = knowTagRelationDAO.getKnowTagRelationByKnowId(id, tagId);
        }

        if (null == ktRelation) {
            return null;
        }
        KnowledgeData knowledgeData = ktRelation.getKnowledgeData();
        CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
        Article article = cmsServiceClient.getArticle(knowledgeData.getCmsId());
        if (null == article) {
            return null;
        }
        Calendar lastOperTime = knowledgeData.getUpdateTime()==null ? knowledgeData.getCreateTime():knowledgeData.getUpdateTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String updateTime = format.format(lastOperTime.getTime());
        ConKnow conKnow = new ConKnow(knowledgeData.getId(), article.getTitle(), article.getContent(), knowledgeData.getKeywords(), knowledgeData.getVisitCnt(), updateTime);
        List<Navigation> navigation = NavigationUtil.getNavigation(ktRelation.getTagData().getSystemData(), ktRelation.getTagData(), article.getTitle(), id);
        ViewKnowVO knowPageVO = new ViewKnowVO(conKnow, navigation);
        return knowPageVO;
    }

    public KnowledgeData getKnowledgeWithArticleById(String id) {
        KnowledgeData knowledgeData = getKnowledgeById(id);
        if (knowledgeData != null) {
            CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
            Article article = cmsServiceClient.getArticle(knowledgeData.getCmsId());
            knowledgeData.setArticle(article);
            SystemService systemService = ServiceFactory.getSystemService();
            SystemData systemData = systemService.getSystemDataByKnowledgeId(id);
            knowledgeData.setSystemData(systemData);
        }
        return knowledgeData;
    }

    @Override
    public ViewKnowsVO getViewKnowsVO(SystemData system, String tagId, int start, int pageSize) {
        int count;
        ViewKnowsVO viewKnowsVO = null;
        List<Navigation> navigation = null;
        TagService service = ServiceFactory.getTagService();
        List<ViewTagVO> tagVOList = service.getTagVOsBySystemIdAndStatus(system.getId(), KnowledgeStatus.YSH);
        List<KnowTagRelationData> list = ManageCacheUtil.getKnowTag(tagId);
        if (null == list) {
            count = knowledgeDataDAO.countKnowledges(tagId, KnowledgeStatus.YSH);
        } else {
            count = list.size();
        }
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
            list = knowTagRelationDAO.getKnowTagDatas(tagId, KnowledgeStatus.YSH);
            ManageCacheUtil.addKnowTag(tagId, list);
        }
        int size = (pageSize + start) >= list.size() ? list.size() : (pageSize + start);
        for (int i = start; i < size; i++) {
            knowledgeDataList.add(list.get(i).getKnowledgeData());
        }

        // 分页情况数据
        Pagination pagination = new Pagination(count, pageSize, start / pageSize + 1);
        // 数据列表
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
    public List<KnowledgeData> get(String systemId) {
        return knowledgeDataDAO.get(systemId);
    }

    @Override
    public void delete(KnowledgeData knowledgeData) {
        knowledgeDataDAO.delete(knowledgeData);
        ManageCacheUtil.removeKnowledgeDataById(knowledgeData.getId());
    }

    @Override
    public void update(KnowledgeData knowledgeData) {
        knowledgeDataDAO.update(knowledgeData);
        ManageCacheUtil.removeKnowledgeDataById(knowledgeData.getId());
    }

    @Override
    public List<KnowTagRelationData> getKnowTagDatas(String tagId, KnowledgeStatus knowledgeStatus) {
        return knowTagRelationDAO.getKnowTagDatas(tagId, knowledgeStatus);
    }

    @Override
    public List<KnowledgeData> get(String systemId, KnowledgeStatus knowledgeStatus) {
        List<KnowledgeData> list = knowledgeDataDAO.get(systemId, knowledgeStatus);
        CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
        for(KnowledgeData knowledgeData:list) {
            Article article = cmsServiceClient.getArticle(knowledgeData.getCmsId());
            knowledgeData.setArticle(article);
        }
        return list;
    }

}