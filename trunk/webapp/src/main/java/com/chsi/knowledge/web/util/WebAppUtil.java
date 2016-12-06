package com.chsi.knowledge.web.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.WebUtils;

import com.chsi.framework.callcontrol.CallInfoHelper;
import com.chsi.knowledge.Constants;
import com.chsi.knowledge.vo.LoginUserVO;

/**
 * 
 * 
 * @author chenjian
 * 
 */
public class WebAppUtil {

    /**
     * 获取用户UserId
     * 
     * @return
     */
    public static String getUserId() {
        String userId = CallInfoHelper.getCurrentUser();
        return userId;
    }

    public static String getUserIp() {
        String userIp = CallInfoHelper.getCurrentUserIp();
        return userIp;
    }

    public static void setLoginUserVO(HttpServletRequest request, LoginUserVO data) {
        WebUtils.setSessionAttribute(request, Constants.SESSION_KNOWLEDGE, data);
    }

    public static LoginUserVO getLoginUserVO(HttpServletRequest request) {
        return (LoginUserVO) WebUtils.getSessionAttribute(request, Constants.SESSION_KNOWLEDGE);
    }

    /**
     *
     */
    public static int getStart(HttpServletRequest request) {
        int start = 0;
        try {
            String temp = request.getParameter("start");
            start = Integer.parseInt(temp);
        } catch (NumberFormatException e) {
            start = 0;
        }
        return start;
    }

    public static int getStart(Map<String, String[]> parameters) {
        int start = 0;
        try {
            String[] params = parameters.get("start");
            if (null != params && params.length != 0) {
                String temp = params[0];
                start = Integer.parseInt(temp);
            } else {
                start = 0;
            }
        } catch (NumberFormatException e) {
            start = 0;
        }
        return start;
    }

}
