package com.chsi.knowledge.action.tag;

import java.util.List;

import com.chsi.knowledge.action.base.BasicAjaxAction;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.service.TagService;
import com.chsi.knowledge.vo.TagVO;

public class TagAction extends BasicAjaxAction{

    private static final long serialVersionUID = 1L;
    private TagService tagService;
    private String systemId;
    private List<TagVO> tagVO;
    
    //systemId是否从数据库中读 一次？？？？还是写个静态变量，因为系统相对是比较固定的，更改很少。
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
