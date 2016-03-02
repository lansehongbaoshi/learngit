package com.chsi.knowledge.thread;

import com.chsi.knowledge.pojo.SearchLogData;
import com.chsi.knowledge.service.CommonService;
import com.chsi.knowledge.service.QueueService;
import com.chsi.knowledge.service.ServiceFactory;
/**
 * 记录搜索日志线程，从队列获取搜索日志保存到数据库
 * @author zhangzh
 */
public class RecordSearchLogThread  extends BaseThread{

    private QueueService queueService;
    private CommonService commonService;
    
    public RecordSearchLogThread(){
        super();
        queueService = ServiceFactory.getQueueService();
        commonService = ServiceFactory.getCommonService();
    }
    
    @Override
    public void doRun() {
        SearchLogData data = null;
        while (!Thread.currentThread().isInterrupted()) {
            try { 
                data = queueService.getSearchLog();
                if (null != data){
                    commonService.save(data);
                }
                sleep(100);
            } catch (Exception e) {
                log.error(e);
            }
        }
        
    }

}
