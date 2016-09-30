package com.chsi.knowledge.dao.impl;

import java.util.List;

import org.hibernate.Query;
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

    @Override
    public List<LogOperData> getLogOperByKeyId(String keyId) {
        // TODO Auto-generated method stub
        String hql = "SELECT A FROM LogOperData A WHERE A.keyId=:keyId ORDER BY A.createTime";
        Query query = hibernateUtil.getSession().createQuery(hql);
        query.setString("keyId", keyId);
        return query.list();
    }
    
}
