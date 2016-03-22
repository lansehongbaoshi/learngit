package com.chsi.knowledge.htgl.action;

import java.util.List;

import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.pojo.KnowTagRelationData;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.pojo.SystemOpenTimeData;
import com.chsi.knowledge.pojo.TagData;
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.service.TagService;
import com.chsi.knowledge.util.ManageCacheUtil;
import com.chsi.knowledge.web.util.AjaxMessage;

/**
 * 后台管理 系统ACTION
 * @author chenjian
 *
 */
public class SystemAction extends AjaxAction{

    private static final long serialVersionUID = 12312412L;
    private SystemService systemService;
    private TagService tagService;
    private String id;
    private String name;
    private String description;
    private SystemData systemData;
    private List<SystemData> systemDatas;
    private List<TagData> tagDatas;
    private int sort;
    
    public SystemService getSystemService() {
        return systemService;
    }

    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    public TagService getTagService() {
        return tagService;
    }

    public void setTagService(TagService tagService) {
        this.tagService = tagService;
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

//    public String getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(String startTime) {
//        this.startTime = startTime;
//    }
//
//    public String getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(String endTime) {
//        this.endTime = endTime;
//    }

    public SystemData getSystemData() {
        return systemData;
    }

    public void setSystemData(SystemData systemData) {
        this.systemData = systemData;
    }

    public List<SystemData> getSystemDatas() {
        return systemDatas;
    }

    public void setSystemDatas(List<SystemData> systemDatas) {
        this.systemDatas = systemDatas;
    }

    public List<TagData> getTagDatas() {
        return tagDatas;
    }

    public void setTagDatas(List<TagData> tagDatas) {
        this.tagDatas = tagDatas;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }



    protected AjaxMessage ajaxMessage = new AjaxMessage();
    
    public void getSystems() throws Exception{
        List<SystemData> systems=systemService.getSystems();
        ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
        ajaxMessage.setO(systems);
        writeJSON(ajaxMessage);
    }
    
    public String listSystems() throws Exception{
        systemDatas = systemService.getSystems();
        for(SystemData data:systemDatas) {
            List<TagData> list = ManageCacheUtil.getTagList(data.getId());
            data.setTagCnt(list==null?0:list.size());
        }
//        List<SystemOpenTimeData> strList = systemService.getOpenSystems();//测试用的
        return SUCCESS;
    }
    
    public String updateIndex() throws Exception{
        systemData = systemService.getSystemById(id);
        if (null == systemData) {
            request.put(Constants.REQUEST_ERROR, "未查到要更新的系统");
            return ERROR;
        }
        return SUCCESS;
    }
    
    public String add() throws Exception {
        if(ValidatorUtil.isNull(id) || ValidatorUtil.isNull(name)){
            request.put(Constants.REQUEST_ERROR, "参数不能为空");
            return ERROR;
        }
        SystemData data = new SystemData(id, name, description, sort);
//        if(!ValidatorUtil.isNull(startTime) && !ValidatorUtil.isNull(endTime)) {
//            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date startDate =sdf.parse(startTime);
//            Date endDate =sdf.parse(endTime);
//            data.setStartTime(startDate);
//            data.setEndTime(endDate);
//        }
        
        String[] startTime = getParameters().get("startTime");
        String[] endTime = getParameters().get("endTime");
        systemService.save(data, startTime, endTime);
        ManageCacheUtil.addSystem(id, data);
        ManageCacheUtil.removeUnderwaySystem();
        return SUCCESS;
    }
    
    public String update() throws Exception {
        if(ValidatorUtil.isNull(id) || ValidatorUtil.isNull(name)) {
            request.put(Constants.REQUEST_ERROR, "id和name不能为空");
            return ERROR;
        }
        
        SystemData data = systemService.getSystemById(id);
        if (null == data) {
            request.put(Constants.REQUEST_ERROR, "未查到要更新的系统");
            return ERROR;
        }
        data.setName(name);
        data.setDescription(description);
        data.setSort(sort);
//        if((!ValidatorUtil.isNull(startTime) && !ValidatorUtil.isNull(endTime))) {
//            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date startDate =sdf.parse(startTime);
//            Date endDate =sdf.parse(endTime);
//            data.setStartTime(startDate);
//            data.setEndTime(endDate);
//        }
//        if((ValidatorUtil.isNull(startTime) && ValidatorUtil.isNull(endTime))) {
//            data.setStartTime(null);
//            data.setEndTime(null);
//        }
        String[] startTime = getParameters().get("startTime");
        String[] endTime = getParameters().get("endTime");
        systemService.update(data, startTime, endTime);
        ManageCacheUtil.removeSystem(id);
        ManageCacheUtil.removeUnderwaySystem();
        return SUCCESS;
    }

    public String delete() throws Exception {
        if (!ValidatorUtil.isNull(id)) {
            SystemData systemData = systemService.getSystemById(id);
            if(systemData!=null) {
                List<TagData> tags = tagService.get(id);
                if(tags.size()>0) {
                    ajaxMessage.addMessage("删除系统前必须先清空标签！");
                    ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
                } else {
                    systemService.delete(systemData);
                    ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
                }
            } else {
                ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
            }
        } else {
            ajaxMessage.addMessage("id不能为空！");
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        }
        writeJSON(ajaxMessage);
        return NONE;
    }
    
    public String list() throws Exception {
        SystemOpenTimeData systemOpenTimeData = systemService.getSystemOpenTimeDataById(id);
        tagDatas = tagService.get(systemOpenTimeData.getSystemId());
        String tagIds = systemOpenTimeData.getTagIds();
        if(tagIds != null && !"".equals(tagIds)){
            for(TagData data:tagDatas){
                if(tagIds.indexOf(data.getId()) > -1)
                    data.setFlag(true);
            }
        }
        return SUCCESS;
    }
    
    public String updateTag() throws Exception {
        SystemOpenTimeData systemOpenTimeData = systemService.getSystemOpenTimeDataById(id);
        systemOpenTimeData.setTagIds(name);
        systemService.update(systemOpenTimeData);
        ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
        writeJSON(ajaxMessage);
        return NONE;
    }
}
