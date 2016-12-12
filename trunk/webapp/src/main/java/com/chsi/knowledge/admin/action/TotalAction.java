package com.chsi.knowledge.admin.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.chsi.framework.page.Page;
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
    private String start;
    private String startTime;
    private String endTime;
    private String systemId;

    private List<PieVO> totalList;
    private List<QALogData> qaLogList;
    private Page<QALogData> page;

    public String option() throws Exception {
        if (!ValidatorUtil.isNull(type)) {
            if (type.equals("session")) {
                totalList = robotService.totalSession();
            } else if (type.equals("q")) {
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
        if (!ValidatorUtil.isNull(type) && ValidatorUtil.isNumber(start)) {
            int currentPage = Integer.parseInt(start);
            if ("无答案".equals(type)) {
                page = robotService.pageQALogDataByAType(systemId, AType.NONE, currentPage, Constants.PAGE_SIZE_20, startTime, endTime);
            } else if ("确定答案".equals(type)) {
                page = robotService.pageQALogDataByAType(systemId, AType.DEFINITE, currentPage, Constants.PAGE_SIZE_20, startTime, endTime);
            } else if ("不确定答案".equals(type)) {
                page = robotService.pageQALogDataByAType(systemId, AType.INDEFINITE, currentPage, Constants.PAGE_SIZE_20, startTime, endTime);
            }
            ServletActionContext.getRequest().setAttribute("page", page);
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public Page<QALogData> getPage() {
        return page;
    }

    public void setPage(Page<QALogData> page) {
        this.page = page;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

}
