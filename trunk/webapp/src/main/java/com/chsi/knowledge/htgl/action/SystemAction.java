package com.chsi.knowledge.htgl.action;

import java.util.List;

import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.service.SystemService;
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

    protected AjaxMessage ajaxMessage = new AjaxMessage();
    
    public void getSystems() throws Exception{
        List<SystemData> systems=systemService.getSystems();
        ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
        ajaxMessage.setO(systems);
        writeJSON(ajaxMessage);
    }
    
    public String add() throws Exception {
        if(!ValidatorUtil.isNull(name)){
            return ERROR;
        }
        SystemData data = new SystemData(null, name, description);
        systemService.save(data);
        return SUCCESS;
    }
    
    public String update() throws Exception {
        SystemData data = systemService.getSystemById(id);
        if (null == data || !ValidatorUtil.isNull(name)) {
            return ERROR;
        }
        data.setName(name);
        data.setDescription(description);
        systemService.update(data);
        return SUCCESS;
    }
    
    

}
