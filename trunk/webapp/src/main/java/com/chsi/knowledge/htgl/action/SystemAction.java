package com.chsi.knowledge.htgl.action;

import java.util.List;

import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.action.base.BasicAction;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.service.SystemService;

/**
 * 后台管理 系统ACTION
 * @author chenjian
 *
 */
public class SystemAction extends BasicAction{

    private static final long serialVersionUID = 12312412L;
    private SystemService systemService;
    private String id;
    private String name;
    private String description;
    
    public String getSystems() throws Exception{
        List<SystemData> systems=systemService.getSystems();
        
        
        return null;
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
