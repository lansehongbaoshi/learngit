package com.chsi.knowledge.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.framework.pojos.PersistentObject;
import com.chsi.knowledge.dao.RobotDAO;
import com.chsi.knowledge.pojo.RobotASetData;
import com.chsi.knowledge.pojo.RobotQSetData;

public class RobotDAOImpl extends BaseHibernateDAO implements RobotDAO{
    private static String query_robot_a_by_like_q = "select aa from RobotQSetData qq,RobotASetData aa where aa.qId=qq.id and qq.q like (:q)";
    private static String query_robot_a_by_q = "select aa from RobotQSetData qq,RobotASetData aa where aa.qId=qq.id and qq.q = :q";
    private static String query_all_q = "from RobotQSetData";
    private static String from_a = "from RobotQSetData";
    private static String query_a_by_q = "select p from RobotASetData p where p.qId=:qId";
    private static String w_id = " where id=:id";

    @Override
    public void save(PersistentObject pojo) {
        hibernateUtil.getSession().save(pojo);
    }

    @Override
    public List<RobotASetData> getAByQ(String q) {
        String hql = query_robot_a_by_like_q;
        Query query = hibernateUtil.getSession().createQuery(hql);
        query.setString("q", "%"+q+"%");
        return query.list();
    }

    @Override
    public List<RobotQSetData> allQ() {
        String hql = query_all_q;
        Query query = hibernateUtil.getSession().createQuery(hql);
        return query.list();
    }

    @Override
    public List<RobotASetData> getAByQSet(RobotQSetData robotQSetData) {
        String hql = query_a_by_q;
        Query query = hibernateUtil.getSession().createQuery(hql);
        query.setString("qId", robotQSetData.getId());
        return query.list();
    }

    @Override
    public RobotQSetData getRobotQSetData(String id) {
        String hql = query_all_q + w_id;
        Query query = hibernateUtil.getSession().createQuery(hql);
        query.setString("id", id);
        return (RobotQSetData)query.uniqueResult();
    }

    @Override
    public void del(PersistentObject pojo) {
        hibernateUtil.getSession().delete(pojo);
    }

    @Override
    public RobotASetData getRobotASetData(String id) {
        String hql = from_a + w_id;
        Query query = hibernateUtil.getSession().createQuery(hql);
        query.setString("id", id);
        return (RobotASetData)query.uniqueResult();
    }

    @Override
    public List<RobotASetData> getAByExplicitQ(String q) {
        String hql = query_robot_a_by_q;
        Query query = hibernateUtil.getSession().createQuery(hql);
        query.setString("q", q);
        return query.list();
    }

}
