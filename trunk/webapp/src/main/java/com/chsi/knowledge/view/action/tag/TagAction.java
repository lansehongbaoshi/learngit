package com.chsi.knowledge.view.action.tag;

import java.util.ArrayList;
import java.util.List;

import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.pojo.TagData;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.service.TagService;
import com.chsi.knowledge.vo.TagVO;

public class TagAction extends AjaxAction{

    private static final long serialVersionUID = 1L;
    private TagService tagService;
    private KnowledgeService knowledgeService;
    private SystemService systemService;
    private String knowledgeId;
    private String systemId;
    private String[] tagIds;
    private List<TagVO> tagVO;
    private String callback;
    
    public void getTagList() throws Exception {
        SystemData systemData = systemService.getSystemById(systemId);
        if (null == systemData) {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        } else {
            tagVO = tagService.getTagVOsBySystemIdAndStatus(systemId, KnowledgeStatus.WSH);
            ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
            ajaxMessage.setO(tagVO);
        }
        writeCallbackJSON(ajaxMessage, callback);
    }
    
     //后面用，先写好，方案一、添加，删除，没有修改这么一说，删除可以前台JS点一次直接访问后台删除
    // 方案二、完全的修改，可以随意的添加删除，到时候保存的时候 把以前的都删掉，将这次的全部保存
    public String saveTagsToKnowledge() throws Exception {
        if (null != tagIds && tagIds.length > 0 && null != knowledgeId) {
            List<TagData> list = new ArrayList<TagData>();
            KnowledgeData k = knowledgeService.getKnowledgeById(knowledgeId);
            if (null == k)
                return "global.error";
            TagData tagData = null;
            for (String tagId : tagIds) {
                tagData = tagService.getTagDataById(tagId);
                if (tagData == null)
                    return "global.error";
                else
                    list.add(tagData);
            }
            tagService.addTagsToKnowledge(k, list);
            return SUCCESS;
        }
        return "global.error";
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

    public String[] getTagIds() {
        return tagIds;
    }

    public void setTagIds(String[] tagIds) {
        this.tagIds = tagIds;
    }

    public String getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(String knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public KnowledgeService getKnowledgeService() {
        return knowledgeService;
    }

    public void setKnowledgeService(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public SystemService getSystemService() {
        return systemService;
    }

    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }
    
    
}
