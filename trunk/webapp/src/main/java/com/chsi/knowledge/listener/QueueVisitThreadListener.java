package com.chsi.knowledge.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.chsi.knowledge.thread.QueueVisitThread;
import com.chsi.knowledge.thread.RecordSearchLogThread;
/**
 * 访问次数队列监听启动线程
 * @author chenjian
 */
public class QueueVisitThreadListener implements ServletContextListener {

    private QueueVisitThread queueVisitThread;
    private RecordSearchLogThread recordSearchLogThread;
    
    public void contextDestroyed(ServletContextEvent arg0) {
        if(!queueVisitThread.isInterrupted()) {
            queueVisitThread.interrupt();
        }
        if(recordSearchLogThread.isAlive()) {
            recordSearchLogThread.interrupt();
        }
    }
    public void contextInitialized(ServletContextEvent arg0) {
        queueVisitThread = new QueueVisitThread();
        queueVisitThread.start();
        recordSearchLogThread = new RecordSearchLogThread();
        recordSearchLogThread.start();
    }

}
