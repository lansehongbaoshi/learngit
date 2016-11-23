package com.chsi.knowledge.admin.action;

import java.io.IOException;

import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.index.service.LogOperService;
import com.chsi.knowledge.vo.LogOperListVO;
import com.chsi.knowledge.vo.LogOperVO;

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
    

    public String index(){
        return SUCCESS;
    }
    

    public String searchLogOper() throws Exception{
        LogOperListVO<LogOperVO> result = logOperService.searchLogOper(startDate,endDate,curPage);
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
    

}
