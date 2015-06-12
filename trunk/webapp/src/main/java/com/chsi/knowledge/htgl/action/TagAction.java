package com.chsi.knowledge.htgl.action;

import java.util.List;

import com.chsi.knowledge.action.base.BasicAction;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.pojo.TagData;
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.service.TagService;
/**
 * 后台管理 标签ACTION
 * @author chenjian
 *
 */
public class TagAction extends BasicAction{

    private static final long serialVersionUID = 12312412L;
    private TagService tagService;
    private SystemService systemService;
    private String systemId;
    private String id;
    private String name;
    private String description;
    private int sort;
    
    public String getTags() throws Exception {
        List<TagData> tags = tagService.get(systemId);
        return SUCCESS;
    }

    public String add() throws Exception {
        SystemData system = systemService.getSystemById(systemId);
        if (system == null) {
            return ERROR;
        }
        TagData tagData = new TagData(null, system, name, description, sort);
        tagService.saveOrUpdate(tagData);
        return SUCCESS;
    }
    
    public String update() throws Exception {
        TagData tagData = tagService.getTagData(id);
        if (null == tagData) {
            return ERROR;
        }
        tagData.setName(name);
        tagData.setSort(sort);
        tagData.setDescription(description);
        tagService.saveOrUpdate(tagData);
        return SUCCESS;
    }

}
