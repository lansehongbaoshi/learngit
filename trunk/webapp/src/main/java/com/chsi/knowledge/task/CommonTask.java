package com.chsi.knowledge.task;

import java.util.TimerTask;

import com.chsi.knowledge.index.service.KnowIndexService;
import com.chsi.knowledge.service.CommonService;
import com.chsi.knowledge.service.ServiceFactory;

//日常任务,每日执行一次
public class CommonTask extends TimerTask {
    CommonService commonService = ServiceFactory.getCommonService();
    KnowIndexService knowIndexService = ServiceFactory.getKnowIndexService();

    @Override
    public void run() {
        try {
            commonService.removeDuplicatedDatas();//清理同一个ip的同一关键词的重复搜索
            commonService.removeTrashKeywords();//清理垃圾关键词
            commonService.recordVisitLog();//记录访问量日志
            knowIndexService.updateAllKnowledgeIndex();//刷新所有系统下的知识索引
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}
