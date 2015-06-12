package com.chsi.knowledge.web.util;

import com.chsi.framework.callcontrol.CallInfoHelper;


/**
 * web工具类
 * @author chenjian
 */
public class WebAppUtil {

    /**
     * 获取用户UserId
     * @return
     */
    public static String getUserId() {
        String userId = CallInfoHelper.getCurrentUser();
        return userId;
    }
    
}
