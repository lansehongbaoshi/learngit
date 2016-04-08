package com.chsi.knowledge.robot.action;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.chsi.framework.callcontrol.CallInfoHelper;
import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.dic.QAType;
import com.chsi.knowledge.index.service.KnowIndexService;
import com.chsi.knowledge.pojo.QASessionData;
import com.chsi.knowledge.service.RobotService;
import com.chsi.knowledge.util.ManageCacheUtil;
import com.chsi.knowledge.util.RemoteCallUtil;
import com.chsi.knowledge.vo.AnswerVO;

public class QAAction extends AjaxAction {
    private String sessionId;
    private String systemId;
    private String q;
    private String knowId;
    private String hello;

    RobotService robotService;
    KnowIndexService knowIndexService;

    /**
     * 
     */
    private static final long serialVersionUID = 7375563004547750062L;

    public String index() throws Exception {
        QASessionData qaSessionData = new QASessionData();
        qaSessionData.setSystemId(systemId);
        qaSessionData.setType(QAType.ROBOT);
        Calendar cal = Calendar.getInstance();
        qaSessionData.setStartTime(cal);
        String userId = CallInfoHelper.getCurrentUser();
        qaSessionData.setQUserId(userId);
        qaSessionData.setQUserIp(CallInfoHelper.getCurrentUserIp());
        qaSessionData.setAUserId(null);
        robotService.save(qaSessionData);
        
        hello = getHelloWord(cal, userId);
        
        sessionId = qaSessionData.getId();
        hello += ManageCacheUtil.getRobotABySpecialQ("#hello");
        return SUCCESS;
    }

    public void qa() throws Exception {
        if (!ValidatorUtil.isNull(sessionId)) {// sessionId必选参数，没有就说明异常
            AnswerVO answerVO = robotService.answer(sessionId, knowId, q);
            ajaxMessage.setO(answerVO);
            ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
            writeJSON(ajaxMessage);
        }
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getKnowId() {
        return knowId;
    }

    public void setKnowId(String knowId) {
        this.knowId = knowId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public RobotService getRobotService() {
        return robotService;
    }

    public void setRobotService(RobotService robotService) {
        this.robotService = robotService;
    }

    public KnowIndexService getKnowIndexService() {
        return knowIndexService;
    }

    public void setKnowIndexService(KnowIndexService knowIndexService) {
        this.knowIndexService = knowIndexService;
    }

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }

    private String getHelloWord(Calendar cal, String userId) {
        String helloword = "";
        if(!"Anonymous".equals(userId)) {
            helloword += RemoteCallUtil.getXmByUserId(userId);
        }
        if(!"".equals(helloword)) {
            helloword+="，";
        }
        int i = cal.get(GregorianCalendar.AM_PM);
        helloword +=  i==0?"上午好。":"下午好。";
        return helloword;
        
    }
}
