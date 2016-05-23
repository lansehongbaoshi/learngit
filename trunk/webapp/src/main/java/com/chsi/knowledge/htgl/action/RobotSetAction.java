package com.chsi.knowledge.htgl.action;

import java.util.List;
import java.util.Map;

import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.pojo.RobotASetData;
import com.chsi.knowledge.pojo.RobotQSetData;
import com.chsi.knowledge.service.RobotService;
import com.chsi.knowledge.util.ManageCacheUtil;
/**
 * 后台管理 机器人配置
 * @author zhangzh
 *
 */
public class RobotSetAction extends AjaxAction{

    /**
     * 
     */
    private static final long serialVersionUID = -8744022807636131205L;
    
    private RobotService robotService;
    
    private String id;
    private String q;
    private String[] a;
    
    private Map<RobotQSetData, List<RobotASetData>> qaSet;
    

    public String list() throws Exception {
        qaSet = robotService.getRobotQASet(null);
        return SUCCESS;
    }

    public String add() throws Exception {
        if(!ValidatorUtil.isNull(q) && a!=null && a.length>0) {
            RobotQSetData robotQSetData = new RobotQSetData();
            robotQSetData.setQ(q);
            robotService.save(robotQSetData);
            for(String str:a) {
                if(!ValidatorUtil.isNull(str)) {
                    RobotASetData robotASetData = new RobotASetData();
                    robotASetData.setqId(robotQSetData.getId());
                    robotASetData.setA(str);
                    robotService.save(robotASetData);
                }
            }
        }
        return SUCCESS;
    }
    
    //更新首页
    public String updateIndex() throws Exception {
        if(!ValidatorUtil.isNull(id)) {
            qaSet = robotService.getRobotQASet(id);
            return SUCCESS;
        } else {
            return ERROR;
        }
    }
    
    //更新操作
    public String update() throws Exception {
        if(!ValidatorUtil.isNull(id) && !ValidatorUtil.isNull(q) && a!=null && a.length>0) {//更新分解为：新增再删除原来的
            RobotQSetData robotQSetData = new RobotQSetData();
            robotQSetData.setQ(q);
            robotService.save(robotQSetData);
            for(String str:a) {
                RobotASetData robotASetData = new RobotASetData();
                robotASetData.setqId(robotQSetData.getId());
                robotASetData.setA(str);
                robotService.save(robotASetData);
            }
            robotService.deleteRobotQASet(id);
            ManageCacheUtil.removeRobotABySpecialQ(q);
        }
        return SUCCESS;
    }
    
    public String delete() throws Exception {
        if(!ValidatorUtil.isNull(id)) {
            robotService.deleteRobotQASet(id);
            ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
        } else {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        }
        writeJSON(ajaxMessage);
        return NONE;
    }

    public RobotService getRobotService() {
        return robotService;
    }

    public void setRobotService(RobotService robotService) {
        this.robotService = robotService;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String[] getA() {
        return a;
    }

    public void setA(String[] a) {
        this.a = a;
    }

    public Map<RobotQSetData, List<RobotASetData>> getQaSet() {
        return qaSet;
    }

    public void setQaSet(Map<RobotQSetData, List<RobotASetData>> qaSet) {
        this.qaSet = qaSet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
