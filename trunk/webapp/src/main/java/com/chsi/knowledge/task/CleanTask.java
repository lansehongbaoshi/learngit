package com.chsi.knowledge.task;

import java.util.TimerTask;

import com.chsi.knowledge.service.CommonService;
import com.chsi.knowledge.service.ServiceFactory;

//清理任务
public class CleanTask extends TimerTask {
    CommonService commonService = ServiceFactory.getCommonService();

    @Override
    public void run() {//清理同一个ip的同一关键词的重复搜索
        try {
            commonService.removeDuplicatedDatas();
            commonService.removeTrashKeywords();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}
