package com.chsi.knowledge.view.action.discuss;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.dic.DiscussStatus;
import com.chsi.knowledge.pojo.DiscussData;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.service.DiscussService;
import com.chsi.knowledge.service.KnowledgeService;
/**
 * 用户评价ACTION
 * @author chsi-pc
 *
 */
public class DiscussAction extends AjaxAction {

    private static final long serialVersionUID = 1L;
    private DiscussService discussService;
    private KnowledgeService knowledgeService;
    private String knowledgeId;
    private String content;
    private int discussStatus;
    private String callback;

    public void discuss() throws Exception {

        KnowledgeData knowledge = knowledgeService.getKnowledgeById(knowledgeId);
        if (null == knowledge) {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        }
        DiscussStatus disStatus = DiscussStatus.getType(discussStatus);
        if (null == disStatus) {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        }
        
        String userId = getUserIdOrIp(httpRequest);
        if (ValidatorUtil.isNull(userId)) {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        }
        if (errorMessages.size() == 0) {
            DiscussData discussData = new DiscussData(null, knowledgeId, userId, disStatus, content, Calendar.getInstance());
            discussService.saveOrUpdate(discussData);
            ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
        }
        writeCallbackJSON(ajaxMessage, callback);
    }

    private String getUserIdOrIp(HttpServletRequest request) {
        String userId = getLoginedUserId();
        if (!ValidatorUtil.isNull(userId) && !userId.equals("Anonymous")) {
            return userId;
        }
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

    public void setCallback(String callback) {
        if (ValidatorUtil.isNull(callback))
            this.callback = Constants.DEFAULT_CALLBACKNAME;
        else
            this.callback = callback;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
   
}
