package com.chsi.knowledge.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.knowledge.dao.TagDataDAO;
import com.chsi.knowledge.pojo.TagData;

public class TagDataDAOImpl extends BaseHibernateDAO implements TagDataDAO {

    private static final String SELECT_TAGDATA = "select p from TagData p";

    private static final String SYSTEMID = " p.systemData.id =:systemId";
    private static final String NAME = " p.name =:name";
    private static final String W = " where ";
    private static final String A = " and ";

    @Override
    public List<TagData> getTagDataBySystemId(String systemId) {
        String hql = SELECT_TAGDATA + W + SYSTEMID;
        Query query = hibernateUtil.getSession().createQuery(hql).setString("systemId", systemId);
        List<TagData> list = query.list();
        return list.size() == 0 ? null : list;
    }

    @Override
    public void saveOrUpdate(TagData tagData) {
        hibernateUtil.saveOrUpdate(tagData);
    }

    @Override
    public TagData getTagDataBySystemIdAndName(String systemId, String name) {
        String hql = SELECT_TAGDATA + W + SYSTEMID + A + NAME;
        Query query=hibernateUtil.getSession().createQuery(hql).setString("systemId", systemId).setString("name", name);
        List<TagData> list = query.list();
        return list.size() == 0 ? null : list.get(0);
    }

}
