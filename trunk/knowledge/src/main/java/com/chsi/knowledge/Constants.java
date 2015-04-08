package com.chsi.knowledge;

/**
 * pojo/功能属性等使用的静态变量配置
 * @author chenjian
 */
public class Constants {
    
    /**
     * 每页显示数
     */
    public static final int PAGE_SIZE = 10;
    
    /**
     * ajaxMessage返回flag
     */
    public static final String AJAX_FLAG_SUCCESS = "true";
    public static final String AJAX_FLAG_ERROR = "false";
    
    /**
     * 默认的jsonp调用callback方法名称
     */
    public static final String DEFAULT_CALLBACKNAME = "knowledge";
    
    /**
     * 更新访问次数队列名称
     */
    public static final String QUEUE_VISIT_KNOWLEDGEID_NAME = "visit_knowledgeId";
    
    /**
     * 缓存有效时间
     */
    public static final int CACHE_EXPIRE_TIME_IN_HOUR = 24;
    
}
