package com.chsi.knowledge.action.discuss;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.dic.DiscussStatus;
import com.chsi.knowledge.pojo.DiscussData;
import com.chsi.knowledge.service.DiscussService;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.vo.KnowledgeVO;

public class DiscussAction extends AjaxAction {

    private static final long serialVersionUID = 1L;
    private DiscussService discussService;
    private KnowledgeService knowledgeService;
    private String knowledgeId;
    private int discussStatus;
    private String callback;

    public void discuss() throws Exception {

        KnowledgeVO knowledgeVO = knowledgeService.getKnowledgeVOById(knowledgeId);
        if (null == knowledgeVO) {
            ajaxMessage.setFlag(Constants.AJAX_ERROR);
            errorMessages.add("知识id有误");
        }
        DiscussStatus disStatus = DiscussStatus.getType(discussStatus);
        if (null == disStatus) {
            ajaxMessage.setFlag(Constants.AJAX_ERROR);
            errorMessages.add("评价信息错误");
        }
        
        String userId = getUserIdOrIp(httpRequest);
        if (ValidatorUtil.isNull(userId)) {
            ajaxMessage.setFlag(Constants.AJAX_ERROR);
            errorMessages.add("用户名或者ip错误");
        }
        if (errorMessages.size() == 0) {
            DiscussData discussData = new DiscussData(null, knowledgeId, userId, disStatus, Calendar.getInstance());
            discussService.saveOrUpdate(discussData);
            ajaxMessage.setFlag(Constants.AJAX_SUCCESS);
        }
        ajaxMessage.setErrorMessages(errorMessages);
        writeCallbackJSON(ajaxMessage, callback);
    }

    private String getUserIdOrIp(HttpServletRequest request) {
        String userId=getLoginedUserId();
        if(!ValidatorUtil.isNull(userId) && !userId.equals("Anonymous")){
            return userId;
        }
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

    public DiscussService getDiscussService() {
        return discussService;
    }

    public void setDiscussService(DiscussService discussService) {
        this.discussService = discussService;
    }
    
    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(String knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public KnowledgeService getKnowledgeService() {
        return knowledgeService;
    }

    public void setKnowledgeService(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    public int getDiscussStatus() {
        return discussStatus;
    }

    public void setDiscussStatus(int discussStatus) {
        this.discussStatus = discussStatus;
    }
    

}
