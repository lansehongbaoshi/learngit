package com.chsi.knowledge.view.action.tag;

import java.util.List;

import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.service.TagService;
import com.chsi.knowledge.vo.ViewTagVO;
/**
 * 用户对标签操作ACTION
 * @author chsi-pc
 */
public class TagAction extends AjaxAction{
 
    private static final long serialVersionUID = 1L;
    private TagService tagService;
    private SystemService systemService;
    private String systemId;
    private String callback;
    
    public void getTagList() throws Exception {
        SystemData systemData = systemService.getSystemById(systemId);
        if (null == systemData) {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        } else {
            List<ViewTagVO> viewTagVO = tagService.getTagVOsBySystemIdAndStatus(systemId, KnowledgeStatus.WSH);
            ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
            ajaxMessage.setO(viewTagVO);
        }
        writeCallbackJSON(ajaxMessage, callback);
    }
    
     //后面用，先写好，方案一、添加，删除，没有修改这么一说，删除可以前台JS点一次直接访问后台删除
    // 方案二、完全的修改，可以随意的添加删除，到时候保存的时候 把以前的都删掉，将这次的全部保存
    /*public String saveTagsToKnowledge() throws Exception {
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
    }*/
    
    public void setCallback(String callback) {
        this.callback = callback;
    }
    
    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
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

    public String getCallback() {
        return callback;
    }

    
}
