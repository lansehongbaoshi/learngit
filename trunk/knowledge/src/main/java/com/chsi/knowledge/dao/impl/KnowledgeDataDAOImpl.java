package com.chsi.knowledge.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.knowledge.dao.KnowledgeDataDAO;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.pojo.KnowledgeData;

public class KnowledgeDataDAOImpl extends BaseHibernateDAO implements KnowledgeDataDAO {

    private static final String SELECT_KNOWLEDGE = "select p from KnowledgeData p";
    private static final String SELECT_KNOWLEDGETAGRELATION_KNOWLEDGE = "select p.knowledgeData from KnowledgeTagRelationData p ";
    private static final String COUNT_KNOWLEDGETAGDATARELATION = "select count(p) from KnowledgeTagRelationData p";
    private static final String UPDATE_KNOWLEDGEVISITCNT = "update KnowledgeData p set p.visitCnt=p.visitCnt+1";
    
    private static final String W = " where ";
    private static final String A = " and ";
    private static final String ID = " p.id=:id";
    private static final String TAG_SYSTEM_ID = " p.tagData.systemData.id=:systemId";
    private static final String TAG_ID = " p.tagData.id=:tagId";
    private static final String KNOWLEDGE_KNOWLEDGESTATUS = " p.knowledgeData.knowledgeStatus=:knowledgeStatus";

    private static final String ORDERBY_KNOWLEDGE_VISITCNT_SORT = " order by p.knowledgeData.visitCnt desc, p.knowledgeData.sort desc";

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
    public List<KnowledgeData> getKnowledges(String systemId, String tagId, KnowledgeStatus knowledgeStatus, int start, int size) {
        String hql = SELECT_KNOWLEDGETAGRELATION_KNOWLEDGE + W + TAG_ID + A + TAG_SYSTEM_ID + A + KNOWLEDGE_KNOWLEDGESTATUS + ORDERBY_KNOWLEDGE_VISITCNT_SORT;
        Query query = hibernateUtil.getSession().createQuery(hql).setInteger("knowledgeStatus", knowledgeStatus.getOrdinal())
                      .setString("systemId", systemId).setString("tagId", tagId).setFirstResult(start).setMaxResults(size);
        List<KnowledgeData> list = query.list();
        return list.size() == 0 ? null : list;
    }

    @Override
    public Long countKnowledges(String systemId, String tagId, KnowledgeStatus knowledgeStatus) {
        String hql = COUNT_KNOWLEDGETAGDATARELATION + W + TAG_SYSTEM_ID + A + TAG_ID + A + KNOWLEDGE_KNOWLEDGESTATUS;
        Query query = hibernateUtil.getSession().createQuery(hql).setInteger("knowledgeStatus", knowledgeStatus.getOrdinal())
                      .setString("systemId", systemId).setString("tagId", tagId);
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
