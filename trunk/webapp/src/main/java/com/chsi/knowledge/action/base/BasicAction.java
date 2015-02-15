package com.chsi.knowledge.action.base;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.chsi.framework.struts2.BasicSupportAction;
import com.chsi.knowledge.web.util.WebAppUtil;
import com.opensymphony.xwork2.ActionContext;

public class BasicAction extends BasicSupportAction implements RequestAware,
        ParameterAware, ServletResponseAware, ServletRequestAware, SessionAware {

    private static final long serialVersionUID = 1L;
    private Map<String, String[]> parameters;
    protected HttpServletResponse response;
    protected HttpServletRequest httpRequest;
    private Map<String, Object> session;
    protected Map<String, Object> request;

    protected String getLoginedUserId() {
        return WebAppUtil.getUserId();
    }

    protected void rollbackTransaction(String message) {
        ActionContext.getContext().getValueStack().set("exception", new Exception(message));
    }

    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public Map<String, String[]> getParameters() {
        return parameters;
    }

    public Map<String, Object> getSession() {
        return session;
    }

    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public void setParameters(Map<String, String[]> parameters) {
        this.parameters = parameters;
    }

    public void setRequest(Map<String, Object> arg0) {
        this.request = arg0;
    }

    public void setServletRequest(HttpServletRequest arg0) {
        this.httpRequest = arg0;
    }

}
