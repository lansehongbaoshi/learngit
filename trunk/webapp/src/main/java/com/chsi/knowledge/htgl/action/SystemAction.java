package com.chsi.knowledge.htgl.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.dic.KnowledgeType;
import com.chsi.knowledge.index.service.LogOperService;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.LogOperData;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.pojo.SystemOpenTimeData;
import com.chsi.knowledge.pojo.TagData;
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.service.TagService;
import com.chsi.knowledge.util.ManageCacheUtil;
import com.chsi.knowledge.vo.SystemVO;

/**
 * 后台管理 系统ACTION
 * 
 * @author chenjian
 * 
 */
public class SystemAction extends AjaxAction {

    private static final long serialVersionUID = 12312412L;
    private SystemService systemService;
    private TagService tagService;
    private LogOperService logOperService;
    private String id;
    private String name;
    private String description;
    private SystemData systemData;
    private List<SystemVO> systemDatas;
    private List<TagData> tagDatas;
    private String sort;
    private String callback;

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

    // public String getStartTime() {
    // return startTime;
    // }
    //
    // public void setStartTime(String startTime) {
    // this.startTime = startTime;
    // }
    //
    // public String getEndTime() {
    // return endTime;
    // }
    //
    // public void setEndTime(String endTime) {
    // this.endTime = endTime;
    // }

    public LogOperService getLogOperService() {
        return logOperService;
    }

    public void setLogOperService(LogOperService logOperService) {
        this.logOperService = logOperService;
    }

    public SystemData getSystemData() {
        return systemData;
    }

    public void setSystemData(SystemData systemData) {
        this.systemData = systemData;
    }

    public List<SystemVO> getSystemDatas() {
        return systemDatas;
    }

    public void setSystemDatas(List<SystemVO> systemDatas) {
        this.systemDatas = systemDatas;
    }

    public List<TagData> getTagDatas() {
        return tagDatas;
    }

    public void setTagDatas(List<TagData> tagDatas) {
        this.tagDatas = tagDatas;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void getSystems() throws Exception {
        List<SystemData> systems = systemService.getSystems(false);
        ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
        ajaxMessage.setO(systems);
        writeCallbackJSON(callback);
    }

    public String listSystems() throws Exception {
        List<SystemData> listSystem = systemService.getSystems(true);
        systemDatas = new ArrayList<SystemVO>();
        for (SystemData system : listSystem) {
            List<TagData> list = ManageCacheUtil.getTagList(system.getId());
            system.setTagCnt(list == null ? 0 : list.size());
            SystemVO date = new SystemVO(system);
            int priCnt = systemService.getKnowsCntBySystem(system.getId(), KnowledgeType.PRIVATE.toString());
            int pubCnt = systemService.getKnowsCntBySystem(system.getId(), KnowledgeType.PUBLIC.toString());
            date.setKnowPrivateCnt(priCnt);
            date.setKnowPublicCnt(pubCnt);
            systemDatas.add(date);
        }
        // List<SystemOpenTimeData> strList =
        // systemService.getOpenSystems();//测试用的
        return SUCCESS;
    }

    public String updateIndex() throws Exception {
        systemData = systemService.getSystemById(id);
        if (null == systemData) {
            request.put(Constants.REQUEST_ERROR, "未查到要更新的系统");
            return ERROR;
        }
        return SUCCESS;
    }

    public String add() throws Exception {
        if (ValidatorUtil.isNull(id) || ValidatorUtil.isNull(name)) {
            request.put(Constants.REQUEST_ERROR, "参数不能为空");
            return ERROR;
        }
        SystemData data = new SystemData(id, name, description, ValidatorUtil.isNumber(sort) ? Integer.parseInt(sort) : 0);
        // if(!ValidatorUtil.isNull(startTime) &&
        // !ValidatorUtil.isNull(endTime)) {
        // SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Date startDate =sdf.parse(startTime);
        // Date endDate =sdf.parse(endTime);
        // data.setStartTime(startDate);
        // data.setEndTime(endDate);
        // }

        String[] startTime = getParameters().get("startTime");
        String[] endTime = getParameters().get("endTime");
        systemService.save(data, startTime, endTime);
        ManageCacheUtil.removeUnderwaySystem();
        
        saveLogOper("系统管理", "", "新增", "系统", id);
        
        return SUCCESS;
    }

    public String update() throws Exception {
        if (ValidatorUtil.isNull(id) || ValidatorUtil.isNull(name)) {
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
        data.setSort(ValidatorUtil.isNumber(sort) ? Integer.parseInt(sort) : 0);
        // if((!ValidatorUtil.isNull(startTime) &&
        // !ValidatorUtil.isNull(endTime))) {
        // SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Date startDate =sdf.parse(startTime);
        // Date endDate =sdf.parse(endTime);
        // data.setStartTime(startDate);
        // data.setEndTime(endDate);
        // }
        // if((ValidatorUtil.isNull(startTime) &&
        // ValidatorUtil.isNull(endTime))) {
        // data.setStartTime(null);
        // data.setEndTime(null);
        // }
        String[] startTime = getParameters().get("startTime");
        String[] endTime = getParameters().get("endTime");
        systemService.update(data, startTime, endTime);
        ManageCacheUtil.removeSystem(id);
        ManageCacheUtil.removeUnderwaySystem();
        
        saveLogOper("系统管理", "", "修改", "系统", id);
        return SUCCESS;
    }

    public String delete() throws Exception {
        if (!ValidatorUtil.isNull(id)) {
            SystemData systemData = systemService.getSystemById(id);
            if (systemData != null) {
                List<TagData> tags = tagService.get(id);
                if (tags.size() > 0) {
                    ajaxMessage.addMessage("删除系统前必须先清空标签！");
                    ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
                } else {
                    systemService.delete(systemData);
                    ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
                    saveLogOper("系统管理", "", "删除", "系统", id);
                }
            } else {
                saveLogOper("系统管理", "", "删除", "系统", id);
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
        if (tagIds != null && !"".equals(tagIds)) {
            for (TagData data : tagDatas) {
                if (tagIds.indexOf(data.getId()) > -1)
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

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
    public void saveLogOper(String m1,String m2,String oper,String message,String key){
        LogOperData logOper = new LogOperData();
        logOper.setCreateTime(Calendar.getInstance());
        com.chsi.knowledge.vo.LoginUserVO user = com.chsi.knowledge.web.util.WebAppUtil.getLoginUserVO(httpRequest);
        logOper.setUserId(user.getAcc().getId());
        logOper.setM1(m1);
        logOper.setM2(m2);
        logOper.setOper(oper);
        logOper.setMessage(message);
        logOper.setKeyId(key);
        logOperService.save(logOper);
    }
    public String updateKnowledgeTime(){
        if(!ValidatorUtil.isNull(id)) {
            List<KnowledgeData> knows = systemService.getKnowsBySystem(id);
            if(knows!=null) {
                for(KnowledgeData know : knows){
                    know.setUpdateTime(Calendar.getInstance());
                    know.setUpdater(getLoginedUserId());
                }

                systemService.updateSystemKnowTime(knows);
            }
        }
        return SUCCESS;
    }
    
}
