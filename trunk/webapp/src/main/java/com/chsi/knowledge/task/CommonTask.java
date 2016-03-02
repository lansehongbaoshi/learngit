package com.chsi.knowledge.task;

import java.util.TimerTask;

import com.chsi.knowledge.service.CommonService;
import com.chsi.knowledge.service.ServiceFactory;

//日常任务
public class CommonTask extends TimerTask {
    CommonService commonService = ServiceFactory.getCommonService();

    @Override
    public void run() {
        try {
            commonService.removeDuplicatedDatas();//清理同一个ip的同一关键词的重复搜索
            commonService.removeTrashKeywords();//清理垃圾关键词
            commonService.recordVisitLog();//记录访问量日志
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}
