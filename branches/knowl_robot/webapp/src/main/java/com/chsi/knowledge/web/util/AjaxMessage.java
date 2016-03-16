package com.chsi.knowledge.web.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 作为Ajax返回信息封装对象
 * @author chenjian
 *
 */
public class AjaxMessage {
    /**
     * 判断执行结果的标识
     */
    private String flag;
    
    /**
     * 提示信息。
     */
    private List<String> errorMessages;
    
    /**
     * 放置任何页面需要的对象
     */
    private Object o;
    
    public void addMessage(String message){
        if(null == errorMessages){
            errorMessages = new ArrayList<String>();
        }
        errorMessages.add(message);
    }
    
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<String> getErrorMessages() {
        return null == errorMessages ? new ArrayList<String>():errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public void setO(Object o) {
        this.o = o;
    }

    public Object getO() {
        return o;
    }
    
}
