package com.chsi.knowledge.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.knowledge.dao.KnowledgeDataDAO;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.pojo.KnowledgeData;

public class KnowledgeDataDAOImpl extends BaseHibernateDAO implements KnowledgeDataDAO {

    private static final String SELECT_KNOWLEDGE = "select p from KnowledgeData p";
    private static final String COUNT_KNOWTAGDATARELATION = "select count(p) from KnowTagRelationData p";
    private static final String UPDATE_KNOWLEDGEVISITCNT = "update KnowledgeData p set p.visitCnt=p.visitCnt+1";
    
    private static final String SELECT_KNOWLEDGE_BY_SYSTEM = "select distinct p.knowledgeData from KnowTagRelationData p where p.tagData.systemData.id=:id";
    
    private static final String W = " where ";
    private static final String A = " and ";
    private static final String ID = " p.id=:id";
    private static final String TAG_ID = " p.tagData.id=:tagId";
    private static final String KNOWLEDGE_KNOWLEDGESTATUS = " p.knowledgeData.knowledgeStatus=:knowledgeStatus";

    @Override
    public void save(KnowledgeData knowledgeData) {
        hibernateUtil.save(knowledgeData);
    }
    
    @Override
    public void update(KnowledgeData knowledgeData) {
        hibernateUtil.update(knowledgeData);
    }
    
    @Override
    public void delete(KnowledgeData knowledgeData) {
        hibernateUtil.getSession().delete(knowledgeData);
    }

    @SuppressWarnings("unchecked")
    @Override
    public KnowledgeData getKnowledgeById(String id) {
        String hql = SELECT_KNOWLEDGE + W + ID;
        Query query = hibernateUtil.getSession().createQuery(hql).setString("id", id);
        List<KnowledgeData> list = query.list();
        return list.size() == 0 ? null : list.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public int countKnowledges(String tagId, KnowledgeStatus knowledgeStatus) {
        String hql = COUNT_KNOWTAGDATARELATION + W + TAG_ID + A + KNOWLEDGE_KNOWLEDGESTATUS;
        Query query = hibernateUtil.getSession().createQuery(hql).setInteger("knowledgeStatus", knowledgeStatus.getOrdinal())
                      .setString("tagId", tagId);
        List<Long> list = query.list();
        return list.size() == 0 ? 0 : new Long(list.get(0)).intValue();
    }

    @Override
    public void updateVisitCntPlusOne(String id) {
        String hql = UPDATE_KNOWLEDGEVISITCNT + W + ID;
        Query query = hibernateUtil.getSession().createQuery(hql).setString("id", id);
        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<KnowledgeData> get(String systemId) {
        String hql = SELECT_KNOWLEDGE_BY_SYSTEM;
        Query query = hibernateUtil.getSession().createQuery(hql);
        query.setString("id", systemId);
        List<KnowledgeData> list = query.list();
        return list;
    }

}
