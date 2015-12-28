package com.chsi.knowledge.service;

import com.chsi.framework.service.spring.SpringBeanLocator;
import com.chsi.knowledge.ServiceConstants;

public class ServiceFactory {

    public static DiscussService getDiscussService(){
        return SpringBeanLocator.getInstance(ServiceConstants.SERVICE_IMPL_CONF).getBean(ServiceConstants.DISCUSS_SERVICE);
    }
    
    public static KnowledgeService getKnowledgeService(){
        return SpringBeanLocator.getInstance(ServiceConstants.SERVICE_IMPL_CONF).getBean(ServiceConstants.KNOWLEDGE_SERVICE);
    }
    
    public static TagService getTagService(){
        return SpringBeanLocator.getInstance(ServiceConstants.SERVICE_IMPL_CONF).getBean(ServiceConstants.TAG_SERVICE);
    }
    
    public static QueueService getQueueService(){
        return SpringBeanLocator.getInstance("chsi-knowledge.xml").getBean("queueService");
    }
    
    public static SearchService getSearchService(){
        return SpringBeanLocator.getInstance(ServiceConstants.SERVICE_IMPL_CONF).getBean(ServiceConstants.SEARCH_SERVICE);
    }
    
}
