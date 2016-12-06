package com.chsi.knowledge.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.knowledge.dao.TagDataDAO;
import com.chsi.knowledge.pojo.TagData;

public class TagDataDAOImpl extends BaseHibernateDAO implements TagDataDAO {

    private static final String SELECT_TAG = "select p from TagData p";

    private static final String SYSTEM_ID = " p.systemData.id =:systemId";
    private static final String NAME = " p.name =:name";
    private static final String PROPERTY = " p.property =:property";
    private static final String ID = " p.id =:id";
    private static final String W = " where ";
    private static final String A = " and ";
    private static final String ORDERBY_SORT = " order by p.sort desc";

    @Override
    public List<TagData> getTagDataBySystemId(String systemId) {
        String hql = SELECT_TAG + W + SYSTEM_ID + ORDERBY_SORT;
        Query query = hibernateUtil.getSession().createQuery(hql).setString("systemId", systemId);
        List<TagData> list = query.list();
        return list;
    }

    @Override
    public void saveOrUpdate(TagData tagData) {
        hibernateUtil.saveOrUpdate(tagData);
    }

    @Override
    public TagData getTagData(String id) {
        String hql = SELECT_TAG + W + ID;
        Query query = hibernateUtil.getSession().createQuery(hql).setString("id", id);
        List<TagData> list = query.list();
        return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public TagData getTagData(String systemId, String name) {
        String hql = SELECT_TAG + W + SYSTEM_ID + A + NAME;
        Query query = hibernateUtil.getSession().createQuery(hql).setString("systemId", systemId).setString("name", name);
        List<TagData> list = query.list();
        return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public void delete(TagData tagData) {
        hibernateUtil.delete(tagData);
    }

    @Override
    public List<TagData> getTagData(String systemId, Integer property) {
        String hql = SELECT_TAG + W + SYSTEM_ID + A + PROPERTY;
        Query query = hibernateUtil.getSession().createQuery(hql).setString("systemId", systemId).setInteger("property", property);
        List<TagData> list = query.list();
        return list;
    }

}
