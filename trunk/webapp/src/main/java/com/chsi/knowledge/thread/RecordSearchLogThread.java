package com.chsi.knowledge.thread;

import com.chsi.knowledge.pojo.SearchLogData;
import com.chsi.knowledge.service.QueueService;
import com.chsi.knowledge.service.SearchService;
import com.chsi.knowledge.service.ServiceFactory;
/**
 * 记录搜索日志线程，从队列获取搜索日志保存到数据库
 * @author zhangzh
 */
public class RecordSearchLogThread  extends BaseThread{

    private QueueService queueService;
    private SearchService searchService;
    
    public RecordSearchLogThread(){
        super();
        queueService = ServiceFactory.getQueueService();
        searchService = ServiceFactory.getSearchService();
    }
    
    @Override
    public void doRun() {
        SearchLogData data = null;
        while (!Thread.currentThread().isInterrupted()) {
            try { 
                data = queueService.getSearchLog();
                if (null != data){
                    searchService.save(data);
                }
            } catch (Exception e) {
                log.error(e);
            }
        }
        
    }

}
