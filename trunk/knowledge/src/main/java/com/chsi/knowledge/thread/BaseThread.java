package com.chsi.knowledge.thread;

public abstract class BaseThread extends Thread{
    
    public void run(){
        try{
            doRun();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            
        }
    }
    public abstract void doRun();
}
