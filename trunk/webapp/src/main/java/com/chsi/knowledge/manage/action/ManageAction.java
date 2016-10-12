package com.chsi.knowledge.manage.action;

import java.util.List;

import com.chsi.cms.client.CmsServiceClient;
import com.chsi.cms.client.CmsServiceClientFactory;
import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.action.base.BasicAction;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.index.service.KnowIndexService;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.pojo.TagData;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.service.TagService;
import com.chsi.knowledge.util.ManageCacheUtil;
import com.chsi.knowledge.vo.ViewKnowVO;
import com.chsi.news.vo.Article;

/**
 * 知识管理ACTION
 * @author zhangzh
 */
public class ManageAction extends BasicAction{
    /**
     * 
     */
    private static final long serialVersionUID = -952471596418924283L;
    private String id;
    private String tagId;
    private String title;
    private String content;
    
    private String systemId;
    
    private ViewKnowVO viewKnowVO;

    private KnowledgeService knowledgeService;
    private KnowIndexService knowIndexService;
    private TagService tagService;
    private SystemService systemService;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public ViewKnowVO getViewKnowVO() {
        return viewKnowVO;
    }

    public void setViewKnowVO(ViewKnowVO viewKnowVO) {
        this.viewKnowVO = viewKnowVO;
    }

    public KnowledgeService getKnowledgeService() {
        return knowledgeService;
    }

    public void setKnowledgeService(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    public KnowIndexService getKnowIndexService() {
        return knowIndexService;
    }

    public void setKnowIndexService(KnowIndexService knowIndexService) {
        this.knowIndexService = knowIndexService;
    }

    public TagService getTagService() {
        return tagService;
    }

    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    //把某个系统的所有知识点刷到搜索引擎的索引
    public String refreshIndex() throws Exception {
        if(!ValidatorUtil.isNull(systemId)) {
            List<KnowledgeData> list = knowledgeService.get(systemId, KnowledgeStatus.YSH);
            if (null != list) {
                log.info(systemId+"开始刷索引，共"+list.size());
                for (KnowledgeData temp : list) {
                    try{
                        knowIndexService.updateKnowIndex(temp.getId());
                        log.info(String.format("{method:'refreshIndex',knowId:'%s',result:'success'}", temp.getId()));
                    } catch(Exception ex) {
                        ex.printStackTrace();
                        log.error(String.format("{method:'refreshIndex',knowId:'%s',result:'fail'}", temp.getId()));
                    }
                }
                log.info(systemId+"结束刷索引");
            }
        } else {
            List<SystemData> systems = systemService.getSystems(false);
            for(SystemData system:systems) {
                List<KnowledgeData> list = knowledgeService.get(system.getId(), KnowledgeStatus.YSH);
                if (null != list) {
                    log.info(systemId+"开始刷索引，共"+list.size());
                    for (KnowledgeData temp : list) {
                        try{
                            knowIndexService.updateKnowIndex(temp.getId());
                            log.info(String.format("{method:'refreshIndex',knowId:'%s',result:'success'}", temp.getId()));
                        } catch(Exception ex) {
                            ex.printStackTrace();
                            log.error(String.format("{method:'refreshIndex',knowId:'%s',result:'fail'}", temp.getId()));
                        }
                    }
                    log.info(systemId+"结束刷索引");
                }
            }
        }
        return INPUT;
    }
    //把某个系统的所有知识点在搜索引擎的索引删掉
    public String deleteIndex() throws Exception {
        if(!ValidatorUtil.isNull(systemId)) {
            List<KnowledgeData> list = knowledgeService.get(systemId);
            if (null != list) {
                log.info(systemId+"开始删索引，共"+list.size());
                for (KnowledgeData temp : list) {
                    try{
                        knowIndexService.deleteKnowIndex(temp.getId());
                        log.info(String.format("{method:'deleteIndex',knowId:'%s',result:'success'}", temp.getId()));
                    } catch(Exception ex) {
                        ex.printStackTrace();
                        log.error(String.format("{method:'deleteIndex',knowId:'%s',result:'fail'}", temp.getId()));
                    }
                }
                log.info(systemId+"结束删索引");
            }
        }
        return INPUT;
    }
    
    //更新新闻系统和知识库系统里审核状态
    public String updateStatus() throws Exception {
        if(ValidatorUtil.isNull(systemId)) {
            return ERROR;
        }
        List<KnowledgeData> list = knowledgeService.get(systemId);
        if (null != list) {
            for (KnowledgeData temp : list) {
                CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
                Article article = cmsServiceClient.getArticle(temp.getCmsId());
                temp.setKnowledgeStatus(KnowledgeStatus.YSH);
                temp.setUpdater(temp.getCreater());
                temp.setUpdateTime(temp.getCreateTime());
                knowledgeService.update(temp, article.getTitle(), article.getContent(), article.getCreateby());
            }
        }
        return INPUT;
    }
    //从新闻系统里删除某个系统的所有知识，并清缓存
    public String delete() throws Exception{
        if(ValidatorUtil.isNull(systemId)) {
            return ERROR;
        }
        ManageCacheUtil.removeSystem(systemId);
        ManageCacheUtil.removeTagList(systemId);
        List<TagData> t = tagService.get(systemId);
        for (TagData tag : t) {
            ManageCacheUtil.removeKnowTag(tag.getId());
        }
        List<KnowledgeData> list = knowledgeService.get(systemId);
        CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
        for (KnowledgeData k : list){
            cmsServiceClient.deleteArticle(k.getCmsId());
        }
        return INPUT;
    }

    public SystemService getSystemService() {
        return systemService;
    }

    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }
}
