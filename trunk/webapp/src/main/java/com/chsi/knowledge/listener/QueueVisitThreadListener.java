package com.chsi.knowledge.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.chsi.knowledge.thread.QueueVisitThread;
/**
 * 访问次数队列监听启动线程
 * @author chenjian
 */
public class QueueVisitThreadListener implements ServletContextListener {

    private QueueVisitThread queueVisitThread;
    
    public void contextDestroyed(ServletContextEvent arg0) {
        if(!queueVisitThread.isInterrupted()){
            queueVisitThread.interrupt();
        }
    }
    public void contextInitialized(ServletContextEvent arg0) {
        queueVisitThread = new QueueVisitThread();
        queueVisitThread.start();
    }

}
