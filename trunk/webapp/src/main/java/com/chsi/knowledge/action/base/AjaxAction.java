package com.chsi.knowledge.action.base;

import java.io.IOException;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.web.util.AjaxMessage;
import com.chsi.knowledge.web.util.MyPropertyFilter;

/**
 * 基于BasicAction添加ajax相关功能
 * @author chenjian
 */
public class AjaxAction extends BasicAction{

    private static final long serialVersionUID = 21231231L;
    protected AjaxMessage ajaxMessage = new AjaxMessage();
    
    private JsonConfig config = new JsonConfig();
    
    protected void writePlainJSON(Object obj) throws IOException{
        config.setJsonPropertyFilter(new MyPropertyFilter());
        response.setHeader("P3P","CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().print(JSONObject.fromObject(obj, config).toString());
        response.getWriter().flush();
    }
    
    protected void writeJSON(AjaxMessage msg) throws IOException{
        config.setJsonPropertyFilter(new MyPropertyFilter());
        response.setHeader("P3P","CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().print(JSONObject.fromObject(msg, config).toString());
        response.getWriter().flush();
    }
    
    protected void writeCallbackJSON(String callback) throws IOException{
        config.setJsonPropertyFilter(new MyPropertyFilter());
        response.setHeader("Access-Control-Allow-Origin", "http://cti.chsi.com.cn");
        response.setHeader("P3P","CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
        response.setContentType("application/json;charset=UTF-8");
        String json = null;
        if(ValidatorUtil.isNull(callback)){
            json = JSONObject.fromObject(ajaxMessage).toString();
        }else{
            json = callback+"("+JSONObject.fromObject(ajaxMessage, config).toString()+");";
        }
        response.getWriter().print(json);
        response.getWriter().flush();
    }
    
    protected void writeCallbackJSON(String callback, MyPropertyFilter propertyFilter) throws IOException{
        if(propertyFilter!=null) {
            config.setJsonPropertyFilter(propertyFilter);
        } else {
            config.setJsonPropertyFilter(new MyPropertyFilter());
        }
        response.setHeader("P3P","CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
        response.setContentType("application/json;charset=UTF-8");
        String json = null;
        if(ValidatorUtil.isNull(callback)){
            json = JSONObject.fromObject(ajaxMessage).toString();
        }else{
            json = callback+"("+JSONObject.fromObject(ajaxMessage, config).toString()+");";
        }
        response.getWriter().print(json);
        response.getWriter().flush();
    }
    
}
