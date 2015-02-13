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
}
