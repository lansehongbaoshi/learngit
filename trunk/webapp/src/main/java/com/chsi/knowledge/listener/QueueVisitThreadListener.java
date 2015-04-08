package com.chsi.knowledge.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.chsi.knowledge.thread.QueueVisitThread;

public class QueueVisitThreadListener implements ServletContextListener {

    private QueueVisitThread queueVisitThread;
    
    
    public void contextDestroyed(ServletContextEvent arg0) {
        if(!queueVisitThread.isInterrupted())
            queueVisitThread.interrupt();
    }

    public void contextInitialized(ServletContextEvent arg0) {
        queueVisitThread=new QueueVisitThread();
        queueVisitThread.start();
    }

}
