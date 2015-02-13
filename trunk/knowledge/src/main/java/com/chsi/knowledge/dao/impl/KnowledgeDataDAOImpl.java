package com.chsi.knowledge.dao.impl;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;

import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.knowledge.dao.KnowledgeDataDAO;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.pojo.KnowledgeData;

public class KnowledgeDataDAOImpl extends BaseHibernateDAO implements KnowledgeDataDAO {

    private static final String SELECT_KNOWLEDGE = "select p from KnowledgeData p";
    private static final String COUNT_KNOWLEDGE = "select count(p) from KnowledgeData p";
    private static final String UPDATE_KNOWLEDGEVISITCNT = "update KnowledgeData p set p.visitCnt=p.visitCnt+1";
    
    private static final String ID = " p.id=:id";
    private static final String SYSTEMID = " p.tagData.systemData.id=:systemId";
    private static final String TAGNAME = " p.tagData.name=:tagName";
    private static final String KNOWLEDGESTATUS = " p.knowledgeStatus=:knowledgeStatus";
    private static final String W = " where ";
    private static final String A = " and ";
    private static final String ORDERBY_VISITCNT_SORT = " order by p.visitCnt, p.sort";

    @Override
    public void save(KnowledgeData knowledgeData) {
        hibernateUtil.save(knowledgeData);
    }
    
    @Override
    public void update(KnowledgeData knowledgeData) {
        hibernateUtil.update(knowledgeData);
    }

    @Override
    public KnowledgeData getKnowledgeById(String id) {
        String hql = SELECT_KNOWLEDGE + W + ID;
        Query query = hibernateUtil.getSession().createQuery(hql).setString("id", id);
        List<KnowledgeData> list = query.list();
        return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public List<KnowledgeData> getKnowledges(String systemId, String tagName, KnowledgeStatus knowledgeStatus, int start, int size) {
        String hql = SELECT_KNOWLEDGE + W + SYSTEMID + A + TAGNAME + A + KNOWLEDGESTATUS + ORDERBY_VISITCNT_SORT;
        Query query = hibernateUtil.getSession().createQuery(hql)
                      .setString("systemId", systemId).setString("tagName", tagName)
                      .setInteger("knowledgeStatus", knowledgeStatus.getOrdinal())
                      .setFirstResult(start).setMaxResults(size);
        List<KnowledgeData> list = query.list();
        return list.size() == 0 ? null : list;
    }

    @Override
    public Long countKnowledges(String systemId, String tagName, KnowledgeStatus knowledgeStatus) {
        String hql = COUNT_KNOWLEDGE + W + SYSTEMID + A + TAGNAME + A + KNOWLEDGESTATUS;
        Query query = hibernateUtil.getSession().createQuery(hql)
                      .setString("systemId", systemId).setString("tagName", tagName)
                      .setInteger("knowledgeStatus", knowledgeStatus.getOrdinal());
        List<Long> list = query.list();
        return list.size() == 0 ? 0 : list.get(0);
    }

    @Override
    public void updateVisitCntPlusOne(String id) {
        String hql = UPDATE_KNOWLEDGEVISITCNT + W + ID;
        Query query = hibernateUtil.getSession().createQuery(hql).setString("id", id);
        query.executeUpdate();
    }
}
