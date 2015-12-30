package com.chsi.knowledge.htgl.action;

import java.util.List;

import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.pojo.TagData;
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.service.TagService;
import com.chsi.knowledge.util.ManageCacheUtil;
/**
 * 后台管理 标签ACTION
 * @author chenjian
 *
 */
public class TagAction extends AjaxAction{

    private static final long serialVersionUID = 12312412L;
    private TagService tagService;
    private SystemService systemService;
    private String systemId;
    private String id;
    private String name;
    private String description;
    
    private TagData tagData;
    
    private List<TagData> tags;
    
    private int sort;
    
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

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TagData getTagData() {
        return tagData;
    }

    public void setTagData(TagData tagData) {
        this.tagData = tagData;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
    
    public List<TagData> getTags() {
        return this.tags;
    }

    public void setTags(List<TagData> tags) {
        this.tags = tags;
    }

    public void list() throws Exception {
        tags = tagService.get(systemId);
        ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
        ajaxMessage.setO(tags);
        writeJSON(ajaxMessage);
    }

    public String add() throws Exception {
        SystemData system = systemService.getSystemById(systemId);
        if (system == null) {
            request.put(Constants.REQUEST_ERROR, "未查到系统");
            return ERROR;
        }
        TagData tagData = new TagData(null, system, name, description, sort);
        tagService.saveOrUpdate(tagData);
        id = tagData.getId();
        ManageCacheUtil.removeTagList(systemId);
        return SUCCESS;
    }
    
    //更新首页
    public String updateIndex() throws Exception {
        tagData = tagService.getTagData(id);
        if (null == tagData) {
            request.put(Constants.REQUEST_ERROR, "未查到要更新的标签");
            return ERROR;
        }
        return SUCCESS;
    }
    
    public String update() throws Exception {
        TagData tagData = tagService.getTagData(id);
        if (null == tagData) {
            request.put(Constants.REQUEST_ERROR, "未查到要更新的标签");
            return ERROR;
        }
        tagData.setName(name);
        tagData.setSort(sort);
        tagData.setDescription(description);
        tagService.saveOrUpdate(tagData);
        ManageCacheUtil.removeTagList(systemId);
        return SUCCESS;
    }

}
