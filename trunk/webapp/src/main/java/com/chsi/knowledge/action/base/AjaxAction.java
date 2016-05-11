package com.chsi.knowledge.action.base;

import java.io.IOException;

import net.sf.json.JSONObject;

import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.web.util.AjaxMessage;

/**
 * 基于BasicAction添加ajax相关功能
 * @author chenjian
 */
public class AjaxAction extends BasicAction{

    private static final long serialVersionUID = 21231231L;
    protected AjaxMessage ajaxMessage = new AjaxMessage();
    
    protected void writePlainJSON(Object obj) throws IOException{
        response.setHeader("P3P","CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().print(JSONObject.fromObject(obj).toString());
        response.getWriter().flush();
    }
    
    protected void writeJSON(AjaxMessage msg) throws IOException{
        response.setHeader("P3P","CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().print(JSONObject.fromObject(msg).toString());
        response.getWriter().flush();
    }
    
    protected void writeCallbackJSON(String callback) throws IOException{
        response.setHeader("P3P","CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
        response.setContentType("application/json;charset=UTF-8");
        String json = null;
        if(ValidatorUtil.isNull(callback)){
            json = JSONObject.fromObject(ajaxMessage).toString();
        }else{
            json = callback+"("+JSONObject.fromObject(ajaxMessage).toString()+");";
        }
        response.getWriter().print(json);
        response.getWriter().flush();
    }
    
}
