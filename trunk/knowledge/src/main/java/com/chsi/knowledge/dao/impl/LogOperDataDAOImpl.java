package com.chsi.knowledge.dao.impl;

import org.hibernate.Session;

import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.knowledge.dao.LogOperDataDAO;
import com.chsi.knowledge.pojo.LogOperData;

public class LogOperDataDAOImpl extends BaseHibernateDAO implements
        LogOperDataDAO {

    @Override
    public void save(LogOperData logOper) {
        // TODO Auto-generated method stub
        hibernateUtil.getSession().save(logOper);
    }
    
}
