package com.chsi.knowledge.admin.action;

import java.io.IOException;
import java.util.List;

import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.index.service.LogOperService;
import com.chsi.knowledge.util.ManageCacheUtil;
import com.chsi.knowledge.vo.LogOperListVO;
import com.chsi.knowledge.vo.LogOperVO;
import com.chsi.knowledge.vo.Operator;

public class SearchLogAction extends AjaxAction {

    /**
     * 
     */
    private static final long serialVersionUID = 8308621040824340441L;
    private LogOperService logOperService;
    private String startDate;
    private String endDate;
    private int curPage;
    private String callback;
    private List<Operator> operators;
    private List<String> operations;
    private String operator;
    private String operation;

    public String index() {
        
        operators = ManageCacheUtil.getLogOperUsers();
        operations = ManageCacheUtil.getLogOperations();
        return SUCCESS;
    }

    public String searchLogOper() throws Exception {
        System.out.println(operator+"---"+operation);
        LogOperListVO<LogOperVO> result = logOperService.searchLogOper(operator,operation,startDate, endDate, curPage);
        ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
        ajaxMessage.setO(result);
        writeCallbackJSON(callback);
        return NONE;
    }

    public LogOperService getLogOperService() {
        return logOperService;
    }

    public void setLogOperService(LogOperService logOperService) {
        this.logOperService = logOperService;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public List<Operator> getOperators() {
        return operators;
    }

    public void setOperators(List<Operator> operators) {
        this.operators = operators;
    }

    public List<String> getOperations() {
        return operations;
    }

    public void setOperations(List<String> operations) {
        this.operations = operations;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

}
