package com.chsi.knowledge.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.knowledge.dao.KnowTagRelationDataDAO;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.dic.KnowledgeType;
import com.chsi.knowledge.pojo.KnowTagRelationData;
import com.chsi.knowledge.pojo.KnowledgeData;

public class KnowTagRelationDataDAOImpl extends BaseHibernateDAO implements KnowTagRelationDataDAO{

    private static final String SELECT_KNOWTAGRELATION = "select p from KnowTagRelationData p ";
    private static final String W = " where ";
    private static final String A = " and ";
    private static final String KNOWLEDGE_ID = " p.knowledgeData.id=:id";
    private static final String TAG_ID = " p.tagData.id=:tagId";
    private static final String KNOWLEDGE_KNOWLEDGESTATUS = " p.knowledgeData.knowledgeStatus=:knowledgeStatus";
    private static final String KNOWLEDGE_TYPE = " p.knowledgeData.type=:type";
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
    public List<KnowTagRelationData> getKnowTagDatas(String tagId, KnowledgeStatus knowledgeStatus, KnowledgeType type) {
        String hql = SELECT_KNOWTAGRELATION + W + TAG_ID + A + KNOWLEDGE_KNOWLEDGESTATUS;
        if(type!=null) {
            hql += A + KNOWLEDGE_TYPE;
        }
        hql += ORDERBY_KNOWLEDGE_VISITCNT_SORT;
        Query query = hibernateUtil.getSession().createQuery(hql).setInteger("knowledgeStatus", knowledgeStatus.getOrdinal())
                      .setString("tagId", tagId);
        if(type!=null) {
            query.setString("type", type.toString());
        }
        List<KnowTagRelationData> list = query.list();
        return list;
    }
    
    public List<KnowTagRelationData> getKnowTagDatas(String tagId) {
        String hql = SELECT_KNOWTAGRELATION + W + TAG_ID + A + NOT_KNOWLEDGE_KNOWLEDGESTATUS + ORDERBY_KNOWLEDGE_VISITCNT_SORT;
        Query query = hibernateUtil.getSession().createQuery(hql).setInteger("knowledgeStatus", KnowledgeStatus.YSC.getOrdinal()).setString("tagId", tagId);
        List<KnowTagRelationData> list = query.list();
        return list;
    }
    
    public List<KnowTagRelationData> getAllKnowTagDatas(String tagId) {
        String hql = SELECT_KNOWTAGRELATION + W + TAG_ID;
        Query query = hibernateUtil.getSession().createQuery(hql).setString("tagId", tagId);
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

    @Override
    public long getKnowsCntBySystemId(String systemId, String type) {
        // TODO Auto-generated method stub
        String hql = "SELECT COUNT(*) FROM KnowledgeData C WHERE C.id IN " +
        		" (SELECT A.knowledgeData.id FROM KnowTagRelationData A WHERE A.tagData.id IN (SELECT id FROM TagData B WHERE B.systemData.id =:systemId )) " +
        		" AND C.type =:type";
        Query query = hibernateUtil.getSession().createQuery(hql);
        query.setString("systemId", systemId);
        query.setString("type", type);
        return (Long) query.uniqueResult();
    }

    @Override
    public List<KnowledgeData> getKnowsBySystemId(String systemId) {
        // TODO Auto-generated method stub
        String hql = "SELECT C FROM KnowledgeData C WHERE C.id IN " +
                " (SELECT A.knowledgeData.id FROM KnowTagRelationData A WHERE A.tagData.id IN (SELECT id FROM TagData B WHERE B.systemData.id =:systemId )) " +
                " AND C.knowledgeStatus =:knowledgeStatus";
        Query query = hibernateUtil.getSession().createQuery(hql);
        query.setString("systemId", systemId);
        query.setString("knowledgeStatus", String.valueOf(KnowledgeStatus.YSH.getNum()));
        return query.list();
    }
}
