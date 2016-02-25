package com.chsi.knowledge.listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.chsi.knowledge.task.CleanTask;
import com.chsi.knowledge.thread.QueueVisitThread;
import com.chsi.knowledge.thread.RecordSearchLogThread;
import com.ibm.icu.util.Calendar;

/**
 * 访问次数队列监听启动线程
 * 
 * @author chenjian
 */
public class QueueVisitThreadListener implements ServletContextListener {
    protected static Log logger = LogFactory.getLog(QueueVisitThreadListener.class);
    
    private static String machineName;

    private QueueVisitThread queueVisitThread;
    private RecordSearchLogThread recordSearchLogThread;

    public void contextDestroyed(ServletContextEvent arg0) {
        if (!queueVisitThread.isInterrupted()) {
            queueVisitThread.interrupt();
        }
        if (recordSearchLogThread.isAlive()) {
            recordSearchLogThread.interrupt();
        }
    }

    public void contextInitialized(ServletContextEvent arg0) {
        queueVisitThread = new QueueVisitThread();
        queueVisitThread.start();
        recordSearchLogThread = new RecordSearchLogThread();
        recordSearchLogThread.start();
        
        if(isWorkMachine()){
            Timer timer1 = new Timer();
            CleanTask task = new CleanTask();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 13);
//            cal.add(Calendar.MINUTE, 1);
            Date date = cal.getTime();
            timer1.scheduleAtFixedRate(task, date, 1000*60*60*24);//次日0点10分开始执行任务，每隔一天执行一次
//            timer1.scheduleAtFixedRate(task, date, 1000*60);
            logger.info("已设定定时清理任务CleanTask");
        }
    }

    public boolean isWorkMachine() {

        String propertyPath = System.getenv("propertyPath");
        Properties prop = new Properties();
        File file = null;
        FileInputStream fis = null;
        try {
            file = new File(propertyPath);
            fis = new FileInputStream(file);
            prop.load(fis);
            machineName = prop.getProperty("sys.com.chsi.knowledge.workloadStatMachine");
        } catch (Exception e) {
            logger.error("加载配置文件出错", e);
        } finally {
            if (null != fis)
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        boolean matchMachine = false;
        try {
            matchMachine = InetAddress.getLocalHost().getHostName().equalsIgnoreCase(machineName);
        } catch (Exception e) {
            logger.error("获取主机名出错", e);
        }
        if (matchMachine) {
            return true;
        }
        return false;
    }

}
