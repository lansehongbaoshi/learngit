package com.chsi.knowledge.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.knowledge.dao.KnowTagRelationDataDAO;
import com.chsi.knowledge.pojo.KnowTagRelationData;

public class KnowTagRelationDataDAOImpl extends BaseHibernateDAO implements KnowTagRelationDataDAO{

    private static final String SELECT_KNOWTAGRELATION = "select p from KnowTagRelationData p ";
    
    private static final String W = " where ";
    private static final String KNOWLEDGE_ID = " p.knowledgeData.id=:id";
    
    
    @Override
    public void save(KnowTagRelationData knowTagRelationData) {
        hibernateUtil.save(knowTagRelationData);
    }

    @Override
    public KnowTagRelationData getKnowTagRelationByKnowId(String knowledgeId) {
        String hql = SELECT_KNOWTAGRELATION + W + KNOWLEDGE_ID;
        Query query = hibernateUtil.getSession().createQuery(hql).setString("id", knowledgeId);
        List<KnowTagRelationData> list = query.list();
        return list.size() == 0 ? null : list.get(0);
    }
}
