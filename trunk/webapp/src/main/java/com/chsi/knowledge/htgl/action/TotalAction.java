package com.chsi.knowledge.htgl.action;

import java.util.List;

import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.dic.AType;
import com.chsi.knowledge.pojo.QALogData;
import com.chsi.knowledge.service.RobotService;
import com.chsi.knowledge.vo.PieVO;

public class TotalAction extends AjaxAction {
    

    /**
     * 
     */
    private static final long serialVersionUID = 1896259291368641361L;
    
    private RobotService robotService;
    
    private String type;
    
    private List<PieVO> totalList;
    private List<QALogData> qaLogList;

    public String option() throws Exception {
        if(!ValidatorUtil.isNull(type)) {
            if(type.equals("session")) {
                totalList = robotService.totalSession();
            } else if(type.equals("q")) {
                totalList = robotService.totalQ();
            }
            return SUCCESS;
        }
        return ERROR;
    }
    
    public void totalQ() throws Exception {
        totalList = robotService.totalQ();
        ajaxMessage.setO(totalList);
        ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
        writeJSON(ajaxMessage);
    }
    
    public String listQ() throws Exception {
        if(!ValidatorUtil.isNull(type)) {
            if("无答案".equals(type)) {
                qaLogList = robotService.listQALogDataByAType(AType.NONE);
            } else if("确定答案".equals(type)) {
                qaLogList = robotService.listQALogDataByAType(AType.DEFINITE);
            } else if("不确定答案".equals(type)) {
                qaLogList = robotService.listQALogDataByAType(AType.INDEFINITE);
            }
        }
        return SUCCESS;
    }

    public RobotService getRobotService() {
        return robotService;
    }

    public void setRobotService(RobotService robotService) {
        this.robotService = robotService;
    }

    public List<PieVO> getTotalList() {
        return totalList;
    }

    public void setTotalList(List<PieVO> totalList) {
        this.totalList = totalList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<QALogData> getQaLogList() {
        return qaLogList;
    }

    public void setQaLogList(List<QALogData> qaLogList) {
        this.qaLogList = qaLogList;
    }
}
