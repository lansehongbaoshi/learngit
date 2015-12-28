package com.chsi.knowledge.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.framework.pojos.PersistentObject;
import com.chsi.knowledge.dao.SearchDAO;

public class SearchDAOImpl extends BaseHibernateDAO implements SearchDAO {
    private static String TOP_KEYWORD = "SELECT KEYWORD FROM (SELECT KEYWORD,COUNT(1) CNT FROM SEARCH_LOG GROUP BY KEYWORD ORDER BY CNT DESC) WHERE ROWNUM<:N";

    @Override
    public void save(PersistentObject po) {
        hibernateUtil.getSession().save(po);
    }

    @Override
    public List<String> getTopKeyword(int n) {
        Query query = hibernateUtil.getSession().createSQLQuery(TOP_KEYWORD);
        query.setInteger("N", n);
        return query.list();
    }

}
