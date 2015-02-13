package com.chsi.knowledge.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.chsi.framework.struts2.BasicSupportAction;
import com.chsi.knowledge.web.util.AjaxMessage;
import com.chsi.knowledge.web.util.WebAppUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * struts2 basic基类
 * 子类不要重复实现 ParameterAware,ServletResponseAware,SessionAware
 * @author chenjian
 *
 */
public class BasicAjaxAction extends BasicSupportAction implements RequestAware, ParameterAware, ServletResponseAware, ServletRequestAware, SessionAware{

    private static final long serialVersionUID = 1L;
    private Map<String, String[]> parameters;//是不是没啥用？
    protected HttpServletResponse response;
    protected HttpServletRequest httpRequest;
    private Map<String, Object> session;
    protected Map<String, Object> request;
    protected AjaxMessage ajaxMessage = new AjaxMessage();
    protected List<String> errorMessages = new ArrayList<String>();
    
    
    protected void writeJSON(AjaxMessage msg) throws IOException{
        msg.setErrorMessages(errorMessages);
        response.setContentType("text/plain;charset=UTF-8");
        try {
            response.getWriter().print(JSONObject.fromObject(msg).toString());
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected void rollbackTransaction(String message){
        ActionContext.getContext().getValueStack().set("exception", new Exception(message));
    }
    
    protected String getLoginedUserId(){
        return WebAppUtil.getUserId();
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
