package com.chsi.knowledge.action.tag;

import java.util.List;

import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.service.TagService;
import com.chsi.knowledge.vo.TagVO;

public class TagAction extends AjaxAction{

    private static final long serialVersionUID = 1L;
    private TagService tagService;
    private String systemId;
    private List<TagVO> tagVO;
    
    public String getTagList() throws Exception {
        tagVO = tagService.getTagVOsBySystemIdAndStatus(systemId,  KnowledgeStatus.WSH);
        if (null == tagVO)
            return "global.error";
        return SUCCESS;
    }
    
    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public List<TagVO> getTagVO() {
        return tagVO;
    }

    public void setTagVO(List<TagVO> tagVO) {
        this.tagVO = tagVO;
    }

    public TagService getTagService() {
        return tagService;
    }

    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }
    
}
