package com.chsi.knowledge.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.Constants;
import com.chsi.knowledge.web.util.AjaxMessage;

/**
 * 基于BasicAction添加ajax相关功能
 * @author chenjian
 */
public class AjaxAction extends BasicAction{

    private static final long serialVersionUID = 2L;
    protected AjaxMessage ajaxMessage = new AjaxMessage();
    protected List<String> errorMessages = new ArrayList<String>();
    
    protected void writeJSON(AjaxMessage msg) throws IOException{
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().print(JSONObject.fromObject(msg).toString());
        response.getWriter().flush();
    }
    
    protected void writeCallbackJSON(AjaxMessage msg, String callback) throws IOException{
        response.setContentType("text/plain;charset=UTF-8");
        if(ValidatorUtil.isNull(callback))
            callback = Constants.DEFAULT_CALLBACKNAME;
        String json = callback+"("+JSONObject.fromObject(ajaxMessage).toString()+")";
        response.getWriter().print(json);
        response.getWriter().flush();
    }
    
}
