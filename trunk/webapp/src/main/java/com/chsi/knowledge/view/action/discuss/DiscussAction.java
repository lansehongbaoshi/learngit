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
 * @author chenjian
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
    private String userId;

    public void discuss() throws Exception {
        if (null != session.get(knowledgeId)) {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
            errorMessages.add("您已经评论过了");
        }else{
            KnowledgeData knowledge = knowledgeService.getKnowledgeById(knowledgeId);
            if (null == knowledge) {
                ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
                errorMessages.add("知识id有误");
            }
            DiscussStatus disStatus = DiscussStatus.getType(discussStatus);
            if (null == disStatus) {
                ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
                errorMessages.add("评价信息错误");
            }
            if(!ValidatorUtil.isNull(content) && content.length()>200){
                ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
                errorMessages.add("评论内容过长");
            }
            if(ValidatorUtil.isNull(userId))
               userId = getIp(httpRequest);
            if (ValidatorUtil.isNull(userId)) {
                ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
                errorMessages.add("用户名或者ip错误");
            }
            if (errorMessages.size() == 0) {
                DiscussData discussData = new DiscussData(null, knowledgeId, userId, disStatus, content, Calendar.getInstance());
                discussService.saveOrUpdate(discussData);
                ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
                session.put(knowledgeId, discussData);
            }
        }
        ajaxMessage.setErrorMessages(errorMessages);
        writeCallbackJSON(ajaxMessage, callback);
    }

    private String getIp(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

    public void setCallback(String callback) {
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
   
}
