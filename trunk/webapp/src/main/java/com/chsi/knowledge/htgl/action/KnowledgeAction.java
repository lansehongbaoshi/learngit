package com.chsi.knowledge.htgl.action;

import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.BasicAction;
import com.chsi.knowledge.index.service.KnowIndexService;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.TagService;
import com.chsi.knowledge.vo.LoginUserVO;
import com.chsi.knowledge.vo.ViewKnowVO;

/**
 * 知识后台管理ACTION
 * @author chenjian
 */
public class KnowledgeAction extends BasicAction{
    private String id;
    private String tagId;
    private String title;
    private String content;
    
    private String systemId;
    
    private ViewKnowVO viewKnowVO;
    private KnowledgeData knowledgeData;

    private KnowledgeService knowledgeService;
    private KnowIndexService knowIndexService;
    private TagService tagService;
    
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
            knowledgeService.update(data, title, content, loginUserVO.getAcc().getId());
            knowIndexService.updateKnowIndex(data.getId());
        }
        return SUCCESS;
    }
}
