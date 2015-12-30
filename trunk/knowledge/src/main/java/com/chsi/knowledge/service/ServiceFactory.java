package com.chsi.knowledge.service;

import com.chsi.framework.service.spring.SpringBeanLocator;
import com.chsi.knowledge.ServiceConstants;

public class ServiceFactory {
    private static SpringBeanLocator springBeanLocator = SpringBeanLocator.getInstance(ServiceConstants.SERVICE_IMPL_CONF);

    public static DiscussService getDiscussService(){
        return springBeanLocator.getBean(ServiceConstants.DISCUSS_SERVICE);
    }
    
    public static KnowledgeService getKnowledgeService(){
        return springBeanLocator.getBean(ServiceConstants.KNOWLEDGE_SERVICE);
    }
    
    public static TagService getTagService(){
        return springBeanLocator.getBean(ServiceConstants.TAG_SERVICE);
    }
    
    public static SystemService getSystemService(){
        return springBeanLocator.getBean(ServiceConstants.SYSTEM_SERVICE);
    }
    
    public static QueueService getQueueService(){
        return SpringBeanLocator.getInstance("chsi-knowledge.xml").getBean("queueService");
    }
    
    public static SearchService getSearchService(){
        return springBeanLocator.getBean(ServiceConstants.SEARCH_SERVICE);
    }
    
}
