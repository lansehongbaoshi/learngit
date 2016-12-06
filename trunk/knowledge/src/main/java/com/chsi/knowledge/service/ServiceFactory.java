package com.chsi.knowledge.service;

import com.chsi.framework.service.spring.SpringBeanLocator;
import com.chsi.knowledge.ServiceConstants;
import com.chsi.knowledge.index.service.KnowIndexService;
import com.chsi.knowledge.service.impl.CommonServiceImpl;

public class ServiceFactory {
    private static SpringBeanLocator springBeanLocator = SpringBeanLocator.getInstance(ServiceConstants.SERVICE_IMPL_CONF);

    public static DiscussService getDiscussService() {
        return springBeanLocator.getBean(ServiceConstants.DISCUSS_SERVICE);
    }

    public static KnowledgeService getKnowledgeService() {
        return springBeanLocator.getBean(ServiceConstants.KNOWLEDGE_SERVICE);
    }

    public static TagService getTagService() {
        return springBeanLocator.getBean(ServiceConstants.TAG_SERVICE);
    }

    public static SystemService getSystemService() {
        return springBeanLocator.getBean(ServiceConstants.SYSTEM_SERVICE);
    }

    public static KnowTagRelationService getKnowTagRelationService() {
        return springBeanLocator.getBean(ServiceConstants.KNOWTAGRELATIONDATA_SERVICE);
    }

    public static QueueService getQueueService() {
        return SpringBeanLocator.getInstance("chsi-knowledge.xml").getBean("queueService");
    }

    public static CommonServiceImpl getCommonService() {
        return springBeanLocator.getBean(ServiceConstants.COMMON_SERVICE);
    }

    public static KnowIndexService getKnowIndexService() {
        return springBeanLocator.getBean(ServiceConstants.KNOWLEDGE_INDEX_SERVICE);
    }

    public static RobotService getRobotService() {
        return springBeanLocator.getBean(ServiceConstants.ROBOT_SERVICE);
    }

}
