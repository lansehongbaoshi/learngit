package com.chsi.knowledge.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.knowledge.dao.SystemDataDAO;
import com.chsi.knowledge.pojo.SystemData;

public class SystemDataDAOImpl extends BaseHibernateDAO implements SystemDataDAO {

    private static final String SELECT_SYSTEM = "select p from SystemData p ";

    private static final String W = " where ";
    private static final String A = " and ";
    private static final String ID = " p.id=:id";
    private static final String NAME = " p.name=:name ";
    private static final String PROPERTY = " p.property=:property";
    private static final String ORDER_BY_START_TIME = " order by p.sort";

    @SuppressWarnings("unchecked")
    @Override
    public SystemData getSystemById(String id) {
        String hql = SELECT_SYSTEM + W + ID;
        Query query = hibernateUtil.getSession().createQuery(hql).setString("id", id);
        List<SystemData> list = query.list();
        return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public void save(SystemData systemData) {
        hibernateUtil.save(systemData);
    }

    @Override
    public void update(SystemData systemData) {
        hibernateUtil.update(systemData);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<SystemData> getSystems() {
        String hql = SELECT_SYSTEM + ORDER_BY_START_TIME;
        Query query = hibernateUtil.getSession().createQuery(hql);
        List<SystemData> list = query.list();
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SystemData> getSystems(int property) {
        String hql = SELECT_SYSTEM + W + PROPERTY + ORDER_BY_START_TIME;
        Query query = hibernateUtil.getSession().createQuery(hql);
        query.setInteger("property", property);
        List<SystemData> list = query.list();
        return list;
    }

    @Override
    public void delete(SystemData systemData) {
        hibernateUtil.delete(systemData);
    }

    @Override
    public List<String> getSystemIdByKnowledgeId(String knowledgeId) {
        String sql = "SELECT SYSTEM_ID FROM SYS_KNOW WHERE KNOWLEDGE_ID=:KNOWLEDGE_ID";
        Query query = hibernateUtil.getSession().createSQLQuery(sql);
        query.setString("KNOWLEDGE_ID", knowledgeId);
        List list = query.list();
        return list;
    }

    @Override
    public SystemData getKnowledgeByName(String systemName) {
        String hql = SELECT_SYSTEM + W + NAME;
        Query query = hibernateUtil.getSession().createQuery(hql). setString("name", systemName);
        List<SystemData> list = query.list();
        return list.size() == 0 ? null : list.get(0);
    }

}
