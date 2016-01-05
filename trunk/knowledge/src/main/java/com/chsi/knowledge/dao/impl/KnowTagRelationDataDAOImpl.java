package com.chsi.knowledge.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.knowledge.dao.KnowTagRelationDataDAO;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.pojo.KnowTagRelationData;

public class KnowTagRelationDataDAOImpl extends BaseHibernateDAO implements KnowTagRelationDataDAO{

    private static final String SELECT_KNOWTAGRELATION = "select p from KnowTagRelationData p ";
    private static final String W = " where ";
    private static final String A = " and ";
    private static final String KNOWLEDGE_ID = " p.knowledgeData.id=:id";
    private static final String TAG_ID = " p.tagData.id=:tagId";
    private static final String KNOWLEDGE_KNOWLEDGESTATUS = " p.knowledgeData.knowledgeStatus=:knowledgeStatus";
    private static final String NOT_KNOWLEDGE_KNOWLEDGESTATUS = " p.knowledgeData.knowledgeStatus!=:knowledgeStatus";
    
    private static final String DEL_RELATION = "delete from KnowTagRelationData p ";
    
    private static final String ORDERBY_KNOWLEDGE_VISITCNT_SORT = " order by p.knowledgeData.visitCnt desc, p.knowledgeData.sort desc";
    @Override
    public void save(KnowTagRelationData knowTagRelationData) {
        hibernateUtil.save(knowTagRelationData);
    }

    @SuppressWarnings("unchecked")
    @Override
    public KnowTagRelationData getKnowTagRelationByKnowId(String knowledgeId, String tagId) {
        String hql = SELECT_KNOWTAGRELATION + W + KNOWLEDGE_ID + A + TAG_ID;
        Query query = hibernateUtil.getSession().createQuery(hql).setString("id", knowledgeId).setString("tagId", tagId);
        List<KnowTagRelationData> list = query.list();
        return list.size() == 0 ? null : list.get(0);
    }
    
    public List<KnowTagRelationData> getKnowTagRelationByKnowId(String knowledgeId) {
        String hql = SELECT_KNOWTAGRELATION + W + KNOWLEDGE_ID;
        Query query = hibernateUtil.getSession().createQuery(hql).setString("id", knowledgeId);
        List<KnowTagRelationData> list = query.list();
        return list;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<KnowTagRelationData> getKnowTagDatas(String tagId, KnowledgeStatus knowledgeStatus) {
        String hql = SELECT_KNOWTAGRELATION + W + TAG_ID + A + KNOWLEDGE_KNOWLEDGESTATUS + ORDERBY_KNOWLEDGE_VISITCNT_SORT;
        Query query = hibernateUtil.getSession().createQuery(hql).setInteger("knowledgeStatus", knowledgeStatus.getOrdinal())
                      .setString("tagId", tagId);
        List<KnowTagRelationData> list = query.list();
        return list;
    }
    
    public List<KnowTagRelationData> getKnowTagDatas(String tagId) {
        String hql = SELECT_KNOWTAGRELATION + W + TAG_ID + A + NOT_KNOWLEDGE_KNOWLEDGESTATUS + ORDERBY_KNOWLEDGE_VISITCNT_SORT;
        Query query = hibernateUtil.getSession().createQuery(hql).setInteger("knowledgeStatus", KnowledgeStatus.YSC.getOrdinal()).setString("tagId", tagId);
        List<KnowTagRelationData> list = query.list();
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<KnowTagRelationData> getKnowTagDatas(KnowledgeStatus knowledgeStatus, String knowledgeId) {
        String hql = SELECT_KNOWTAGRELATION + W + KNOWLEDGE_ID + A + KNOWLEDGE_KNOWLEDGESTATUS + ORDERBY_KNOWLEDGE_VISITCNT_SORT;
        Query query = hibernateUtil.getSession().createQuery(hql).setInteger("knowledgeStatus", knowledgeStatus.getOrdinal()).setString("id", knowledgeId);
        List<KnowTagRelationData> list = query.list();
        return list;
    }

    @Override
    public int del(String knowledgeId) {
        Query query = hibernateUtil.getSession().createQuery(DEL_RELATION + W + KNOWLEDGE_ID);
        query.setString("id", knowledgeId);
        return query.executeUpdate();
    }
}
