package com.chsi.knowledge.htgl.action;

import java.util.List;

import com.chsi.cms.client.CmsServiceClient;
import com.chsi.cms.client.CmsServiceClientFactory;
import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.service.SystemService;
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
    private String id;
    private String name;
    private String description;
    
    private SystemData systemData;
    private List<SystemData> systemDatas;
    
    public SystemService getSystemService() {
        return systemService;
    }

    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
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

    protected AjaxMessage ajaxMessage = new AjaxMessage();
    
    public void getSystems() throws Exception{
        List<SystemData> systems=systemService.getSystems();
        ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
        ajaxMessage.setO(systems);
        writeJSON(ajaxMessage);
    }
    
    public String listSystems() throws Exception{
        systemDatas = systemService.getSystems();
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
        SystemData data = new SystemData(id, name, description);
        systemService.save(data);
        ManageCacheUtil.addSystem(id, data);
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
        systemService.update(data);
        ManageCacheUtil.removeSystem(id);
        return SUCCESS;
    }

    public String delete() throws Exception {
        if (!ValidatorUtil.isNull(id)) {
            SystemData systemData = systemService.getSystemById(id);
            if(systemData!=null) {
                systemService.delete(systemData);
            }
            ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
        } else {
            ajaxMessage.addMessage("id不能为空！");
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        }
        writeJSON(ajaxMessage);
        return NONE;
    }
}
