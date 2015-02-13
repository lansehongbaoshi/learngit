package com.chsi.knowledge.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.knowledge.dao.DiscussDataDAO;
import com.chsi.knowledge.pojo.DiscussData;

public class DiscussDataDAOImpl extends BaseHibernateDAO implements DiscussDataDAO {

    private static final String SELECT_DISCUSS = "select p from DiscussData p";

    private static final String KNOWLEDGEID = " p.knowledgeId= :knowledgeId";
    private static final String W = " where ";

    @Override
    public void saveOrUpdate(DiscussData discussData) {
        hibernateUtil.saveOrUpdate(discussData);
    }

    @Override
    public List<DiscussData> getDiscusssByKnowledgeId(String knowledgeId) {
        String hql = SELECT_DISCUSS + W + KNOWLEDGEID;
        Query query = hibernateUtil.getSession().createQuery(hql).setString("knowledgeId", knowledgeId);
        List<DiscussData> list = query.list();
        return list.size() == 0 ? null : list;
    }

}
