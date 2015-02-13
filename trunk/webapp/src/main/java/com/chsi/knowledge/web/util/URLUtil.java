package com.chsi.knowledge.web.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.chsi.framework.web.UrlUtil;
/**
 * @author zengsong <a href="mailto:zengsong@chsi.com.cn">Jinsong Zeng</a>
 * @version $Id$
 */
public class URLUtil extends com.chsi.framework.util.URLUtil{

    /**
     * 替换jboss3.2中的StringBuffer stringBuffer = request.getRequestURL()方法
     * @param request
     * @return
     */
    public static StringBuffer getActionURL(HttpServletRequest request) {
        String strActionURL = UrlUtil.getRequestURL(request);
        StringBuffer actionURL = new StringBuffer(strActionURL); 
        return actionURL;
    }
    
    /**
     * 有URL的隐藏域
     * @param request
     * @param URL
     * @return
     */
    public static StringBuffer getHiddenWithURL(HttpServletRequest request, StringBuffer URL) {
        StringBuffer hidden = new StringBuffer();
        hidden.append("<input type='hidden' name='fromURL' value='" + URL + "' />");
        Enumeration enumeration = request.getParameterNames();
        while(enumeration.hasMoreElements()){
            String name = (String)enumeration.nextElement();
            String nameToLowerCase = name.toLowerCase();
            if(nameToLowerCase.indexOf("submit") == -1 && nameToLowerCase.indexOf("reset") == -1){
                String value = request.getParameter(name);
                hidden.append("<input type='hidden' name='" + name + "' value='" + value + "' />");
            }
        }
        return hidden;
    }
}
