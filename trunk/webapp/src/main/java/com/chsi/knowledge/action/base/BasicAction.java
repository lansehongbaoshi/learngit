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
import com.chsi.knowledge.vo.LoginUserVO;
import com.chsi.knowledge.web.util.WebAppUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * BasicAction 实现大多接口，子类不用再去重复实现
 * 
 * @author chenjian
 */
public class BasicAction extends BasicSupportAction implements ParameterAware, RequestAware, ServletResponseAware, ServletRequestAware, SessionAware {

    private static final long serialVersionUID = 1L;
    protected HttpServletResponse response;
    protected HttpServletRequest httpRequest;
    protected Map<String, Object> session;
    protected Map<String, Object> request;
    private Map<String, String[]> parameters;

    protected String getLoginedUserId() {
        return WebAppUtil.getUserId();
    }

    protected LoginUserVO getLoginUserVO() {
        return WebAppUtil.getLoginUserVO(httpRequest);
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

    public Map<String, Object> getSession() {
        return session;
    }

    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public void setRequest(Map<String, Object> arg0) {
        this.request = arg0;
    }

    public void setServletRequest(HttpServletRequest arg0) {
        this.httpRequest = arg0;
    }

    public Map<String, String[]> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String[]> parameters) {
        this.parameters = parameters;
    }

}
