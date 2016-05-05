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
    public static final int PAGE_SIZE_20 = 20;
    
    /**
     * 自动完成显示数目
     */
    public static final int SEARCH_PAGE_SIZE = 5;
    
    /**
     * ajaxMessage返回flag
     */
    public static final String AJAX_FLAG_SUCCESS = "true";
    public static final String AJAX_FLAG_ERROR = "false";
    
    
    /**
     * 每个知识访问次数队列名称
     */
    public static final String QUEUE_VISIT_KNOWLEDGEID_NAME = "visit_knowledgeId";
    //搜索日志队列
    public static final String QUEUE_SEARCH_LOG = "search_log";
    
    /**
     * 缓存有效时间：小时
     */
    public static final int CACHE_EXPIRE_TIME_IN_HOUR = 24;
    
    /**
     * session中的名称前缀
     */
    public static final String DISCUSS = "discuss_";
    public static final String VISIT = "visit_";
    
    public static final String SESSION_KNOWLEDGE = "SESSION_KNOWLEDGE";
    
    public static final String REQUEST_ERROR = "r_error";
    public static final String ERRORURL = "/error/index.jsp";
    
    public static final String ROLE_KNOWLEDGE_ADMIN = "ROLE_KNOWLEDGE_ADMIN";
    
//    public static final String LOB_TYPE = "/kl/attachment";// 小文件系统里指定的type
}
