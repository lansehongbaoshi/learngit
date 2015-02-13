package com.chsi.knowledge.web.util;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.chsi.framework.callcontrol.CallInfoHelper;


/**
 * 获取用户信息工具类
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

    /**
     * 分页起始数
     *//*
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
    }*/

}
