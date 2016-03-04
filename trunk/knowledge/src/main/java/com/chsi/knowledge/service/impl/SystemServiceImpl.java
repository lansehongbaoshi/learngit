package com.chsi.knowledge.service.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.chsi.framework.service.BaseDbService;
import com.chsi.knowledge.ServiceConstants;
import com.chsi.knowledge.dao.SystemDataDAO;
import com.chsi.knowledge.dao.SystemOpenTimeDAO;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.pojo.SystemOpenTimeData;
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.util.ManageCacheUtil;

public class SystemServiceImpl extends BaseDbService implements SystemService{

    private SystemDataDAO systemDataDAO;
    private SystemOpenTimeDAO systemOpenTimeDAO;
    
    @Override
    protected void doCreate() {
        systemDataDAO = getDAO(ServiceConstants.SYSTEMDATA_DAO, SystemDataDAO.class);
        systemOpenTimeDAO = getDAO(ServiceConstants.SYSTEMOPENTIME_DAO, SystemOpenTimeDAO.class);
    }

    @Override
    protected void doRemove() {
        
    }
    
    @Override
    public SystemData getSystemById(String id) {
        SystemData systemData = systemDataDAO.getSystemById(id);
        systemData.setList(systemOpenTimeDAO.getList(id));
        return systemData;
    }

    @Override
    public void save(SystemData systemData) {
        systemDataDAO.save(systemData);
    }
    
    @Override
    public void save(SystemData systemData,String[] startTime, String[] endTime) throws ParseException {
        systemDataDAO.save(systemData);
        if(startTime != null && endTime != null){
            if(startTime.length >0 && endTime.length >0){
                for(int i=0;i<startTime.length;i++){
                    if(!"".equals(startTime[i]) || !"".equals(endTime[i])){
                        SystemOpenTimeData systemOpenTimeData = new SystemOpenTimeData();
                        systemOpenTimeData.setSystemId(systemData.getId());
                        systemOpenTimeData.setStartTime(getCalendar(startTime[i]));
                        systemOpenTimeData.setEndTime(getCalendar(endTime[i]));
                        systemOpenTimeDAO.save(systemOpenTimeData);
                    }
                }
            }
        }
    }
    
    private Calendar getCalendar(String time) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
        Date date = sdf.parse(time); 
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
    
    @Override
    public void update(SystemData systemData) {
        systemDataDAO.update(systemData);
        ManageCacheUtil.removeSystem(systemData.getId());
    }
    
    @Override
    public void update(SystemData systemData, String[] startTime, String[] endTime) throws ParseException {
        systemDataDAO.update(systemData);
        ManageCacheUtil.removeSystem(systemData.getId());
        systemOpenTimeDAO.delete(systemData.getId());
        if(startTime != null && endTime != null){
            if(startTime.length >0 && endTime.length >0){
                for(int i=0;i<startTime.length;i++){
                    if(!"".equals(startTime[i]) || !"".equals(endTime[i])){
                        SystemOpenTimeData systemOpenTimeData = new SystemOpenTimeData();
                        systemOpenTimeData.setSystemId(systemData.getId());
                        systemOpenTimeData.setStartTime(getCalendar(startTime[i]));
                        systemOpenTimeData.setEndTime(getCalendar(endTime[i]));
                        systemOpenTimeDAO.save(systemOpenTimeData);
                    }
                }
            }
        }
    }

    @Override
    public List<SystemData> getSystems() {
        List<SystemData> list = systemDataDAO.getSystems();
        if(list == null || list.size() == 0){
            return null;
        }
        for(SystemData systemData:list){
            systemData.setList(systemOpenTimeDAO.getList(systemData.getId()));
        }
        return list;
    }

    @Override
    public void delete(SystemData systemData) {
        systemOpenTimeDAO.delete(systemData.getId());
        systemDataDAO.delete(systemData);
    }

    @Override
    public List<String> getSystemId() {
        return systemOpenTimeDAO.getSystemId();
    }



}
