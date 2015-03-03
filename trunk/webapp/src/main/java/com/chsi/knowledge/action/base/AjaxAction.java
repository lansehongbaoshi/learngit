package com.chsi.knowledge.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

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
        msg.setErrorMessages(errorMessages);
        response.setContentType("text/plain;charset=UTF-8");
        try {
            response.getWriter().print(JSONObject.fromObject(msg).toString());
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
