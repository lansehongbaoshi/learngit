package com.chsi.knowledge.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chsi.cms.client.CmsServiceClient;
import com.chsi.cms.client.CmsServiceClientFactory;
import com.chsi.framework.service.BaseDbService;
import com.chsi.knowledge.ServiceConstants;
import com.chsi.knowledge.dao.KnowTagRelationDataDAO;
import com.chsi.knowledge.dao.KnowledgeDataDAO;
import com.chsi.knowledge.dao.TagDataDAO;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.dic.KnowledgeType;
import com.chsi.knowledge.pojo.KnowTagRelationData;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.pojo.TagData;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.ServiceFactory;
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.service.TagService;
import com.chsi.knowledge.util.ManageCacheUtil;
import com.chsi.knowledge.util.MemCachedUtil;
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
        if (knowledgeStatus == KnowledgeStatus.DSH) {
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
        KnowledgeData knowledgeData = ManageCacheUtil.getKnowledgeDataById(id);
        Calendar lastOperTime = knowledgeData.getUpdateTime() == null ? knowledgeData.getCreateTime() : knowledgeData.getUpdateTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String updateTime = format.format(lastOperTime.getTime());
        ConKnow conKnow = new ConKnow(knowledgeData.getId(), knowledgeData.getTitle(), knowledgeData.getContent(), knowledgeData.getKeywords(), knowledgeData.getVisitCnt(), updateTime, knowledgeData.getSystemNames());
        List<Navigation> navigation = NavigationUtil.getNavigation(ktRelation.getTagData().getSystemData(), ktRelation.getTagData(), knowledgeData.getTitle(), id);
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
            List<SystemData> systemDatas = systemService.getSystemDataByKnowledgeId(id);
            knowledgeData.setSystemDatas(systemDatas);
        }
        return knowledgeData;
    }

    @Override
    public ViewKnowsVO getViewKnowsVO(SystemData system, String tagId, int start, int pageSize) {
        int count;
        ViewKnowsVO viewKnowsVO = null;
        List<Navigation> navigation = null;
        TagService service = ServiceFactory.getTagService();
        List<ViewTagVO> tagVOList = service.getTagVOsBySystemIdAndStatus(system.getId(), KnowledgeStatus.YSH, KnowledgeType.PUBLIC);
        List<KnowTagRelationData> list = ManageCacheUtil.getKnowTag(tagId);
        if (null == list) {
            count = knowledgeDataDAO.countKnowledges(tagId, KnowledgeStatus.YSH, KnowledgeType.PUBLIC);
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

        List<KnowledgeData> knowledgeDataList = new ArrayList<KnowledgeData>();
        int size = (pageSize + start) >= list.size() ? list.size() : (pageSize + start);
        for (int i = start; i < size; i++) {
            knowledgeDataList.add(list.get(i).getKnowledgeData());
        }

        // 分页情况数据
        Pagination pagination = new Pagination(count, pageSize, start / pageSize + 1);
        // 数据列表
        List<Know> knows = new ArrayList<Know>();
        Know know = null;
        for (KnowledgeData knowledgeData : knowledgeDataList) {
            KnowledgeData KnowledgeData = ManageCacheUtil.getKnowledgeDataById(knowledgeData.getId());
            know = new Know(KnowledgeData.getArticle().getTitle());
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
    
    public void updateVisitCntPlus(String id,int count) {
        knowledgeDataDAO.updateVisitCntPlus(id,count);
    }

    @Override
    public void updateCtiVisitCntPlusOne(String id) {
        knowledgeDataDAO.updateCtiVisitCntPlusOne(id);
    }

    @Override
    public KnowledgeData getKnowledgeById(String id) {
        return knowledgeDataDAO.getKnowledgeById(id);
    }
    
    @Override
    public KnowledgeData getKnowledgeByCmsId(String cmsId) {
        return knowledgeDataDAO.getKnowledgeByCmsId(cmsId);
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
    public List<KnowTagRelationData> getKnowTagDatas(String tagId, KnowledgeStatus knowledgeStatus, KnowledgeType type) {
        return knowTagRelationDAO.getKnowTagDatas(tagId, knowledgeStatus, type);
    }

    @Override
    public List<KnowledgeData> get(String systemId, KnowledgeStatus knowledgeStatus) {
        List<KnowledgeData> list = knowledgeDataDAO.get(systemId, knowledgeStatus);
        CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
        SystemService systemService = ServiceFactory.getSystemService();
        for (KnowledgeData knowledgeData : list) {
            Article article = cmsServiceClient.getArticle(knowledgeData.getCmsId());
            knowledgeData.setArticle(article);
            List<SystemData> systemDatas = systemService.getSystemDataByKnowledgeId(knowledgeData.getId());
            knowledgeData.setSystemDatas(systemDatas);
        }
        return list;
    }

    @Override
    public List<KnowledgeData> get(String systemId, KnowledgeStatus knowledgeStatus, KnowledgeType type) {
        List<KnowledgeData> list = knowledgeDataDAO.get(systemId, knowledgeStatus, type.toString());
        CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
        SystemService systemService = ServiceFactory.getSystemService();
        for (KnowledgeData knowledgeData : list) {
            Article article = cmsServiceClient.getArticle(knowledgeData.getCmsId());
            knowledgeData.setArticle(article);
            List<SystemData> systemDatas = systemService.getSystemDataByKnowledgeId(knowledgeData.getId());
            knowledgeData.setSystemDatas(systemDatas);
        }
        return list;
    }

    @Override
    public Set<KnowledgeData> getTop(String systemId) {
        Set<KnowledgeData> set = new LinkedHashSet<KnowledgeData>();
        List<KnowledgeData> list = knowledgeDataDAO.getTop(systemId);
        for (KnowledgeData data : list) {
            KnowledgeData newData = ManageCacheUtil.getKnowledgeDataById(data.getId());
            set.add(newData);
        }
        return set;
    }

    @Override
    public List<KnowledgeData> getKnowledgeByStatus(String systemId, String tag, KnowledgeStatus dsh, String type, int start, int size) {
        // TODO Auto-generated method stub
        List<KnowledgeData> list = knowledgeDataDAO.get(systemId, tag, dsh, type, start, size);
        return list;
    }

    @Override
    public List<TagData> getTagDatasByKnowId(KnowledgeData know) {
        // TODO Auto-generated method stub
        List<TagData> list = knowledgeDataDAO.getTagDatasByKnowId(know.getId());
        return list;
    }

    @Override
    public long getKnowledgeCount(String systemId, String tag, KnowledgeStatus dsh, String type, String userId) {
        // TODO Auto-generated method stub
        long count = knowledgeDataDAO.getKnowledgeCount(systemId, tag, dsh, type, userId);
        return count;
    }

    @Override
    public List<KnowledgeData> getKnowledgeByStatusAndUserId(String systemId, String tag, KnowledgeStatus dsh, String type, String userId, int start, int size) {
        // TODO Auto-generated method stub
        List<KnowledgeData> list = knowledgeDataDAO.get(systemId, tag, dsh, type, userId, start, size);
        return list;
    }

    public boolean judgeKnowledgeInTopCount(String knowId, int rank) {

        KnowledgeData knowledge = ManageCacheUtil.getKnowledgeDataById(knowId);
        // 首先判断该知识是不是置顶的知识
        if (knowledge.getTopTime() != null) {
            String key = "com.chsi.knowledge.util.ManageCacheUtil.getCatalogTopKnowl";
            MemCachedUtil.removeByKey(key);
            return true;
        }

        Map<SystemData, List<KnowledgeData>> map = ManageCacheUtil.getCatalogTopKnowl(10);
        List<SystemData> systems = new ArrayList<SystemData>();
        List<KnowTagRelationData> knowTagRelations = knowTagRelationDAO.getKnowTagRelationByKnowId(knowledge.getId());
        if (knowTagRelations != null) {
            for (KnowTagRelationData knowTagRelation : knowTagRelations) {
                SystemData system = knowTagRelation.getTagData().getSystemData();
                if (!systems.contains(system)) {
                    systems.add(system);
                }
            }
        }

        if (systems.size() > 0) {
            for (SystemData system : systems) {
                List<KnowledgeData> knows = map.get(system);
                if (knows==null || knows.size() < 10) {
                    String key = "com.chsi.knowledge.util.ManageCacheUtil.getCatalogTopKnowl";
                    MemCachedUtil.removeByKey(key);
                    return true;
                } else {
                    for (KnowledgeData know : knows) {
                        if (know.getVisitCnt() <= knowledge.getVisitCnt()) {
                            String key = "com.chsi.knowledge.util.ManageCacheUtil.getCatalogTopKnowl";
                            MemCachedUtil.removeByKey(key);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void updateVisitCnt(KnowledgeData knowledgeData) {
        // TODO Auto-generated method stub
        knowledgeDataDAO.updateVisitCnts(knowledgeData);
    }

}
