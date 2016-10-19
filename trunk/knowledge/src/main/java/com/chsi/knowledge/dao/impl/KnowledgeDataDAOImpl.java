package com.chsi.knowledge.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;

import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.dao.KnowledgeDataDAO;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.dic.KnowledgeType;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.TagData;

public class KnowledgeDataDAOImpl extends BaseHibernateDAO implements KnowledgeDataDAO {

    private static final String SELECT_KNOWLEDGE = "select p from KnowledgeData p";
    private static final String COUNT_KNOWTAGDATARELATION = "select count(p) from KnowTagRelationData p";
    private static final String UPDATE_KNOWLEDGE_VISITCNT = "update KnowledgeData p set p.visitCnt=p.visitCnt+1";
    private static final String UPDATE_KNOWLEDGE_CTI_VISITCNT = "update KnowledgeData p set p.ctiVisitCnt=p.ctiVisitCnt+1";
    
    private static final String SELECT_KNOWLEDGE_BY_SYSTEM = "select distinct p.knowledgeData from KnowTagRelationData p where p.tagData.systemData.id=:id";
    private static final String SELECT_TOP_KNOWLEDGE_BY_SYSTEM = "select p.knowledgeData from KnowTagRelationData p where p.tagData.systemData.id=:systemId and p.knowledgeData.topTime is not null order by p.knowledgeData.topTime desc";
    private static final String SELECT_KNOWLEDGE_BY_STATUS = "select distinct p.knowledgeData from KnowTagRelationData p where p.knowledgeData.knowledgeStatus=:status";
    
    private static final String W = " where ";
    private static final String A = " and ";
    private static final String ID = " p.id=:id";
    private static final String TAG_ID = " p.tagData.id=:tagId";
    private static final String KNOWLEDGE_KNOWLEDGESTATUS = " p.knowledgeData.knowledgeStatus=:knowledgeStatus";
    private static final String KNOWLEDGE_TYPE = " p.knowledgeData.type=:type";
    private static final String TAG_SYSTEM_ID = " p.tagData.systemData.id=:systemId ";
    
    private static final String ORDER_TAG_SORT = " order by p.tagData.sort";

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
    public int countKnowledges(String tagId, KnowledgeStatus knowledgeStatus, KnowledgeType type) {
        String hql = COUNT_KNOWTAGDATARELATION + W + TAG_ID + A + KNOWLEDGE_KNOWLEDGESTATUS;
        if(type!=null) {
            hql += A + KNOWLEDGE_TYPE;
        }
        Query query = hibernateUtil.getSession().createQuery(hql).setInteger("knowledgeStatus", knowledgeStatus.getOrdinal())
                      .setString("tagId", tagId);
        if(type!=null) {
            query.setString("type", type.toString());
        }
        List<Long> list = query.list();
        return list.size() == 0 ? 0 : new Long(list.get(0)).intValue();
    }

    @Override
    public void updateVisitCntPlusOne(String id) {
        String hql = UPDATE_KNOWLEDGE_VISITCNT + W + ID;
        Query query = hibernateUtil.getSession().createQuery(hql).setString("id", id);
        query.executeUpdate();
    }
    
    @Override
    public void updateCtiVisitCntPlusOne(String id) {
        String hql = UPDATE_KNOWLEDGE_CTI_VISITCNT + W + ID;
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

    @Override
    public List<KnowledgeData> get(String systemId, KnowledgeStatus knowledgeStatus) {
        String hql = SELECT_KNOWLEDGE_BY_STATUS;
        if(!ValidatorUtil.isNull(systemId)) {
            hql += A + TAG_SYSTEM_ID;
        }
        Query query = hibernateUtil.getSession().createQuery(hql);
        if(!ValidatorUtil.isNull(systemId)) {
            query.setString("systemId", systemId);
        }
        query.setInteger("status", knowledgeStatus.getOrdinal());
        List<KnowledgeData> list = query.list();
        return list;
    }

    @Override
    public List<KnowledgeData> get(String systemId, KnowledgeStatus knowledgeStatus, String type) {
        String hql = SELECT_KNOWLEDGE_BY_STATUS;
        if(!ValidatorUtil.isNull(systemId)) {
            hql += A + TAG_SYSTEM_ID;
        }
        if(!ValidatorUtil.isNull(type)) {
            hql += A + KNOWLEDGE_TYPE;
        }
//        hql += ORDER_TAG_SORT;
        Query query = hibernateUtil.getSession().createQuery(hql);
        if(!ValidatorUtil.isNull(systemId)) {
            query.setString("systemId", systemId);
        }
        if(!ValidatorUtil.isNull(type)) {
            query.setString("type", type);
        }
        query.setInteger("status", knowledgeStatus.getOrdinal());
        List<KnowledgeData> list = query.list();
        return list;
    }

    @Override
    public List<KnowledgeData> getTop(String systemId) {
        String hql = SELECT_TOP_KNOWLEDGE_BY_SYSTEM;
        Query query = hibernateUtil.getSession().createQuery(hql);
        query.setString("systemId", systemId);
        List<KnowledgeData> list = query.list();
        return list;
    }

    @Override
    public List<KnowledgeData> get(String systemId, String tag,
            KnowledgeStatus dsh, String type,int start,int size) {
        // TODO Auto-generated method stub
        String hql = "select A from KnowledgeData A where 1=1 ";
        if(dsh != null ){
            hql += " and A.knowledgeStatus = "+dsh.getNum()+" ";
        }
        
        if(!ValidatorUtil.isNull(type) ){
            hql += " and A.type = '"+type+"' ";
        }
        
        if(!ValidatorUtil.isNull(systemId) &&  ValidatorUtil.isNull(tag)){
            hql += " and A.id in ( select distinct B.knowledgeData.id from KnowTagRelationData B where B.tagData.id in (select C.id from TagData C where C.systemData.id = '"+systemId+"'))";
            
        }
        if(!ValidatorUtil.isNull(tag)){
            hql += " and A.id in ( select distinct B.knowledgeData.id from KnowTagRelationData B where B.tagData.id in ('"+tag+"'))";
        }
        hql += " order by A.createTime ";

        System.out.println(hql);
        Query query = hibernateUtil.getSession().createQuery(hql);
        query.setFirstResult(start);
        query.setMaxResults(size);
        
        List<KnowledgeData> list = query.list();
        return list;
    }

    @Override
    public List<TagData> getTagDatasByKnowId(String id) {
        // TODO Auto-generated method stub
        String hql = "select a from TagData a where a.id in (select b.tagData.id from KnowTagRelationData b where b.knowledgeData.id=:id )";
        Query query = hibernateUtil.getSession().createQuery(hql);
        query.setString("id", id);
        List<TagData> list = query.list();
        return list;
    }

    @Override
    public long getKnowledgeCount(String systemId, String tag,
            KnowledgeStatus dsh, String type) {
        // TODO Auto-generated method stub
        String hql = "select COUNT(*) from KnowledgeData A where 1=1 ";
        if(dsh != null ){
            hql += " and A.knowledgeStatus = "+dsh.getNum()+" ";
        }
        
        if(!ValidatorUtil.isNull(type) ){
            hql += " and A.type = '"+type+"' ";
        }
        
        if(!ValidatorUtil.isNull(systemId) &&  ValidatorUtil.isNull(tag)){
            hql += " and A.id in ( select distinct B.knowledgeData.id from KnowTagRelationData B where B.tagData.id in (select C.id from TagData C where C.systemData.id = '"+systemId+"'))";
            
        }
        if(!ValidatorUtil.isNull(tag)){
            hql += " and A.id in ( select distinct B.knowledgeData.id from KnowTagRelationData B where B.tagData.id in ('"+tag+"'))";
        }
        hql += " order by A.createTime ";

        System.out.println(hql);
        Query query = hibernateUtil.getSession().createQuery(hql);
        return (Long) query.uniqueResult();

    }

    @Override
    public void update(List<KnowledgeData> knows) {
        // TODO Auto-generated method stub
        for(KnowledgeData knowledgeData : knows){
            hibernateUtil.update(knowledgeData);
        }
    }
    
    

}
