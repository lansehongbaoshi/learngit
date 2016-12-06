package com.chsi.knowledge.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.knowledge.dao.SystemOpenTimeDAO;
import com.chsi.knowledge.pojo.SystemOpenTimeData;

public class SystemOpenTimeDAOImpl extends BaseHibernateDAO implements SystemOpenTimeDAO {

    private static String SELECT_SYSTEMOPENTIMEDATA = "select p from SystemOpenTimeData p ";
    private static String DELETE_SYSTEMOPENTIMEDATA = "delete SystemOpenTimeData p ";
    private static String SELECT_SYSTEMID = "select p from SystemOpenTimeData p,SystemData s where s.property=0 and p.systemId=s.id and :systime < TO_char(p.endTime, 'yyyy-mm-dd hh24:mi:ss') and :systime > TO_char(p.startTime,'yyyy-mm-dd hh24:mi:ss') order by s.sort";

    private static String CONDITION_SYSTEM_ID = " where p.systemId=:systemId ";
    private static String CONDITION_ID = " where p.id=:id ";
    private static String CONDITION_Time = " where :time between p. ";

    @Override
    public void save(SystemOpenTimeData systemOpenTimeData) {
        hibernateUtil.save(systemOpenTimeData);
    }

    @Override
    public void delete(String systemId) {
        Query query = hibernateUtil.getSession().createQuery(DELETE_SYSTEMOPENTIMEDATA + CONDITION_SYSTEM_ID).setString("systemId", systemId);
        query.executeUpdate();
    }

    @Override
    public List<SystemOpenTimeData> getList(String systemId) {
        Query query = hibernateUtil.getSession().createQuery(SELECT_SYSTEMOPENTIMEDATA + CONDITION_SYSTEM_ID).setString("systemId", systemId);
        return query.list();
    }

    @Override
    public List<SystemOpenTimeData> getUnderwaySystem() {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dateTime = dateformat.format(date);
        Query query = hibernateUtil.getSession().createQuery(SELECT_SYSTEMID).setString("systime", dateTime);
        return query.list();
    }

    @Override
    public SystemOpenTimeData getDataById(String id) {
        Query query = hibernateUtil.getSession().createQuery(SELECT_SYSTEMOPENTIMEDATA + CONDITION_ID).setString("id", id);
        return (SystemOpenTimeData) query.uniqueResult();
    }

    @Override
    public void update(SystemOpenTimeData systemOpenTimeData) {
        hibernateUtil.update(systemOpenTimeData);
    }

}
