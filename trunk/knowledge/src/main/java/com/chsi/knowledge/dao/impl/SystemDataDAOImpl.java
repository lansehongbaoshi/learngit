package com.chsi.knowledge.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.knowledge.dao.SystemDataDAO;
import com.chsi.knowledge.pojo.SystemData;

public class SystemDataDAOImpl extends BaseHibernateDAO implements SystemDataDAO{
    
    private static final String SELECT_KNOWLEDGE = "select p from SystemData p";
    
    private static final String W = " where ";
    private static final String ID = " p.id=:id";
    
    @Override
    public SystemData getSystemById(String id) {
        String hql = SELECT_KNOWLEDGE + W + ID;
        Query query = hibernateUtil.getSession().createQuery(hql). setString("id", id);
        List<SystemData> list = query.list();
        return list.size() == 0 ? null : list.get(0);
    }

}
