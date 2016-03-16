package com.chsi.knowledge.dao.impl;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;

import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.framework.pojos.PersistentObject;
import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.dao.SearchDAO;
import com.chsi.knowledge.pojo.SearchLogData;

public class SearchDAOImpl extends BaseHibernateDAO implements SearchDAO {
    private static String TOP_KEYWORD = "SELECT SYSTEM_ID,KEYWORD FROM (SELECT SYSTEM_ID,KEYWORD,COUNT(1) CNT FROM SEARCH_LOG WHERE CREATE_TIME BETWEEN :START_TIME AND :END_TIME GROUP BY SYSTEM_ID,KEYWORD ORDER BY CNT DESC) WHERE ROWNUM<:N";
    private static String TOP_VISIT = "SELECT ID FROM (SELECT ID FROM KNOWLEDGE ORDER BY VISIT_CNT DESC,SORT DESC) WHERE ROWNUM<:N";
    private static String DUPLICATED_DATA = "SELECT SYSTEM_ID,KEYWORD,USER_IP,COUNT(*) AS CNT FROM SEARCH_LOG WHERE CREATE_TIME BETWEEN :START_TIME AND :END_TIME GROUP BY SYSTEM_ID,KEYWORD,USER_IP HAVING COUNT(*)>1";
    private static String fetch_search_log_data = "select p from SearchLogData p ";
    private static String condition_system_id = " p.systemId=:systemId ";
    private static String condition_system_id_null = " p.systemId is null ";
    private static String condition_keyword = " p.keyword=:keyword ";
    private static String condition_user_ip = " p.userIP=:userIP ";
    private static String condition_user_ip_null = " p.userIP is null ";
    private static String condition_create_time = " p.createTime between :startTime and :endTime";
    private static String order_create_time = " order by p.createTime desc ";
    
    private static String where = " where ";
    private static String and = " and ";

    @Override
    public void save(PersistentObject po) {
        hibernateUtil.getSession().save(po);
    }
    
    @Override
    public void del(PersistentObject po) {
        hibernateUtil.getSession().delete(po);
    }

    @Override
    public List<Object[]> getTopKeyword(int n, Calendar startTime, Calendar endTime) {
        Query query = hibernateUtil.getSession().createSQLQuery(TOP_KEYWORD);
        query.setInteger("N", n);
        query.setCalendar("START_TIME", startTime);
        query.setCalendar("END_TIME", endTime);
        return query.list();
    }

    @Override
    public List<String> getTopVisitKnowl(int n) {
        Query query = hibernateUtil.getSession().createSQLQuery(TOP_VISIT);
        query.setInteger("N", n);
        return query.list();
    }

    @Override
    public List<Object[]> getDuplicatedDatas(Calendar startTime, Calendar endTime) {
        Query query = hibernateUtil.getSession().createSQLQuery(DUPLICATED_DATA);
        query.setCalendar("START_TIME", startTime);
        query.setCalendar("END_TIME", endTime);
        return query.list();
    }

    @Override
    public List<SearchLogData> getTheDuplicatedData(String systemId, String keyword, String userIP) {
        String hql = fetch_search_log_data + where;
        if(ValidatorUtil.isNull(systemId)) {
            hql += condition_system_id_null;
        } else {
            hql += condition_system_id;
        }
        hql+= and + condition_keyword + and;
        if(ValidatorUtil.isNull(userIP)) {
            hql += condition_user_ip_null;
        } else {
            hql += condition_user_ip;
        }
        hql += order_create_time;
        Query query = hibernateUtil.getSession().createQuery(hql);
        if(!ValidatorUtil.isNull(systemId)) {
            query.setString("systemId", systemId);
        }
        query.setString("keyword", keyword);
        if(!ValidatorUtil.isNull(userIP)) {
            query.setString("userIP", userIP);
        }
        return query.list();
    }

    @Override
    public List<SearchLogData> getSearchLogData(Calendar startTime, Calendar endTime) {
        String hql = fetch_search_log_data;
        if(startTime!=null && endTime!=null) {
            hql += where + condition_create_time;
        }
        Query query = hibernateUtil.getSession().createQuery(hql);
        if(startTime!=null && endTime!=null) {
            query.setCalendar("startTime", startTime);
            query.setCalendar("endTime", endTime);
        }
        return query.list();
    }

}
