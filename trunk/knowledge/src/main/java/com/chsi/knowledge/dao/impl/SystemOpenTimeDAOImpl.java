package com.chsi.knowledge.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.knowledge.dao.SystemOpenTimeDAO;
import com.chsi.knowledge.pojo.SystemOpenTimeData;

public class SystemOpenTimeDAOImpl extends BaseHibernateDAO implements SystemOpenTimeDAO {

    private static String SELECT_SYSTEMOPENTIMEDATA = "select p from SystemOpenTimeData p ";
    private static String DELETE_SYSTEMOPENTIMEDATA = "delete SystemOpenTimeData p ";
    
    private static String CONDITION_SYSTEM_ID = " where p.systemId=:systemId ";

    @Override
    public void save(SystemOpenTimeData systemOpenTimeData){
        hibernateUtil.save(systemOpenTimeData);
    }
    @Override
    public void delete(String systemId){
        Query query = hibernateUtil.getSession().createQuery(DELETE_SYSTEMOPENTIMEDATA+CONDITION_SYSTEM_ID).setString("systemId", systemId);
        query.executeUpdate();
    }
    @Override
    public List<SystemOpenTimeData> getList(String systemId){
        Query query = hibernateUtil.getSession().createQuery(SELECT_SYSTEMOPENTIMEDATA+CONDITION_SYSTEM_ID).setString("systemId", systemId);
        return query.list();
    }
    
}
