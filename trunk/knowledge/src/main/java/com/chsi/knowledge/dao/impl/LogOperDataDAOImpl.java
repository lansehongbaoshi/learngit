package com.chsi.knowledge.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.knowledge.dao.LogOperDataDAO;
import com.chsi.knowledge.pojo.LogOperData;

public class LogOperDataDAOImpl extends BaseHibernateDAO implements LogOperDataDAO {

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

    @Override
    public int getLogOpersCountByDate(String operator, String operation, Date startDate, Date endDate) {
        String hql = "SELECT COUNT(*) FROM LogOperData A WHERE A.createTime between ? and ? "
                + ("".equals(operator)?"":" AND A.userId=:userId ")
                + ("".equals(operation)?"":" AND A.oper=:oper ")
                + " ORDER BY A.createTime DESC";
        Query query = hibernateUtil.getSession().createQuery(hql);
        query.setDate(0, startDate);
        query.setDate(1, endDate);
        if(!"".equals(operator)){
            query.setString("userId", operator);
        }
        if(!"".equals(operation)){
            query.setString("oper", operation);
        }
        return Integer.parseInt(query.uniqueResult().toString());
    }

    @Override
    public List<LogOperData> getLogOpersByDate(String operator, String operation, Date startDate, Date endDate, int curPage, int pageSize) {
        String hql = "SELECT A FROM LogOperData A WHERE A.createTime between ? and ? "
                + ("".equals(operator)?"":" AND A.userId=:userId ")
                + ("".equals(operation)?"":" AND A.oper=:oper ")
                + " ORDER BY A.createTime DESC";
        
        Query query = hibernateUtil.getSession().createQuery(hql);
        query.setDate(0, startDate);
        query.setDate(1, endDate);
        if(!"".equals(operator)){
            query.setString("userId", operator);
        }
        if(!"".equals(operation)){
            query.setString("oper", operation);
        }
        
        query.setMaxResults(pageSize);
        query.setFirstResult(curPage * pageSize);
        return query.list();
    }

    @Override
    public List<String> getLogOperAllUserId() {
        // TODO Auto-generated method stub
        String hql = "SELECT DISTINCT A.userId FROM LogOperData A";
        Query query = hibernateUtil.getSession().createQuery(hql);
        return query.list();
    }

    @Override
    public List<String> getLogOperAllOperations() {
        // TODO Auto-generated method stub
        String hql = "SELECT DISTINCT A.oper FROM LogOperData A";
        Query query = hibernateUtil.getSession().createQuery(hql);
        return query.list();
    }

}
