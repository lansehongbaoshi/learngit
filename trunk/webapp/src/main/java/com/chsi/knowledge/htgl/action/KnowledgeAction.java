package com.chsi.knowledge.htgl.action;

import java.util.Calendar;

import com.chsi.cms.client.CmsServiceClient;
import com.chsi.cms.client.CmsServiceClientFactory;
import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.action.base.BasicAction;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.index.service.KnowIndexService;
import com.chsi.knowledge.pojo.KnowTagRelationData;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.pojo.TagData;
import com.chsi.knowledge.service.KnowTagRelationService;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.service.TagService;
import com.chsi.knowledge.vo.LoginUserVO;
import com.chsi.knowledge.vo.ViewKnowVO;

/**
 * 知识后台管理ACTION
 * @author zhangzh
 */
public class KnowledgeAction extends BasicAction{
    private String id;
    private String tagId;
    private String keywords;
    private String tagName;
    private String title;
    private String content;
    private String sort;
    
    private String systemId;
    
    private ViewKnowVO viewKnowVO;
    private KnowledgeData knowledgeData;

    private KnowledgeService knowledgeService;
    private KnowIndexService knowIndexService;
    private TagService tagService;
    private SystemService systemService;
    private KnowTagRelationService knowTagRelationService;
    
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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
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
    
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
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

    public KnowledgeData getKnowledgeData() {
        return knowledgeData;
    }

    public void setKnowledgeData(KnowledgeData knowledgeData) {
        this.knowledgeData = knowledgeData;
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

    public SystemService getSystemService() {
        return systemService;
    }

    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    public KnowTagRelationService getKnowTagRelationService() {
        return knowTagRelationService;
    }

    public void setKnowTagRelationService(KnowTagRelationService knowTagRelationService) {
        this.knowTagRelationService = knowTagRelationService;
    }

    private static final long serialVersionUID = 464316546L;

    
    public String searchindex() throws Exception{
        return SUCCESS;
    }
    
    public String modifyindex() throws Exception{
        if(!ValidatorUtil.isNull(id)) {
            knowledgeData = knowledgeService.getKnowledgeCmsById(id);
            if (null != knowledgeData)  {
                return SUCCESS;
            }
            
        }
        return ERROR;
    }
    
    public String getKnow() throws Exception{
        
        return null;
    }
    
    //更新某个知识点，包括更新系统内的knowledge表、新闻系统里的知识点以及搜索引擎的索引
    public String updateKnowledge() throws Exception {
        if(!ValidatorUtil.isNull(id)&&!ValidatorUtil.isNull(title)&&!ValidatorUtil.isNull(content)) {
            KnowledgeData data = knowledgeService.getKnowledgeById(id);
            LoginUserVO loginUserVO = getLoginUserVO();
            data.setUpdateTime(Calendar.getInstance());
            data.setUpdater(getLoginedUserId());
            knowledgeService.update(data, title, content, loginUserVO.getAcc().getId());
            knowIndexService.updateKnowIndex(data.getId());
        }
        return SUCCESS;
    }
    
    public void addKnowledge() throws Exception {
        if(!ValidatorUtil.isNull(systemId)&&!ValidatorUtil.isNull(keywords)&&!ValidatorUtil.isNull(title)&&!ValidatorUtil.isNull(content)&&!ValidatorUtil.isNull(tagName)&&!ValidatorUtil.isNull(sort)) {
            SystemData system = systemService.getSystemById(systemId);
            if(system!=null) {
                TagData tagData = tagService.getTagData(systemId, tagName);
                if(tagData==null) {
                    tagData = new TagData(null, system, tagName, tagName, 0);
                    tagService.saveOrUpdate(tagData);
                }
                knowledgeData = new KnowledgeData(null, keywords, null, 0, Integer.parseInt(sort), 
                        KnowledgeStatus.YSH, getLoginedUserId(), Calendar.getInstance(),
                        null, null);
                LoginUserVO loginUserVO = getLoginUserVO();
                //保存知识
                knowledgeService.save(knowledgeData, title, content, loginUserVO.getOrg().getCode(), getLoginedUserId());
                //保存知识与标签关系
                KnowTagRelationData knowTagRelationData = new KnowTagRelationData(null, knowledgeData, tagData);
                knowTagRelationService.save(knowTagRelationData);
                knowIndexService.updateKnowIndex(knowledgeData.getId());
            }
        }
    }
    
    public void delKnowledge() throws Exception {
        if(!ValidatorUtil.isNull(id)) {
            KnowledgeData data = knowledgeService.getKnowledgeById(id);
            knowIndexService.deleteKnowIndex(data.getId());//删索引
            CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
            cmsServiceClient.deleteArticle(data.getCmsId());//从新闻系统删除
            knowledgeService.delete(data);//从本系统删除
        }
    }
}
