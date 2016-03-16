package com.chsi.knowledge.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class BaseThread extends Thread {

    protected Log log;

    public BaseThread() {
        log = LogFactory.getLog(getClass());
    }

    public void run() {
        try {
            doRun();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public abstract void doRun();
}
