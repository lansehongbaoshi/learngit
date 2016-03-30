package com.chsi.knowledge.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.framework.pojos.PersistentObject;
import com.chsi.knowledge.dao.RobotDAO;
import com.chsi.knowledge.pojo.RobotASetData;
import com.chsi.knowledge.pojo.RobotQSetData;
import com.chsi.knowledge.vo.PieVO;

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

    @Override
    public List<PieVO> totalSession() {
        List<PieVO> list = new ArrayList<PieVO>();
        
        String SQL = "SELECT COUNT(*) FROM QA_SESSION";
        Query query = hibernateUtil.getSession().createSQLQuery(SQL);
        BigDecimal total = (BigDecimal)query.uniqueResult();
        
        SQL = "SELECT COUNT(DISTINCT SESSION_ID) FROM QA_LOG";
        query = hibernateUtil.getSession().createSQLQuery(SQL);
        BigDecimal cnt = (BigDecimal)query.uniqueResult();
        
        list.add(new PieVO("有效会话", cnt.longValue()));
        list.add(new PieVO("空会话", total.longValue()-cnt.longValue()));
        
        return list;
    }

    @Override
    public List<PieVO> totalQ() {
        List<PieVO> list = new ArrayList<PieVO>();
        
        String SQL = "SELECT SUM(DECODE(A_TYPE,0,1,0)) AS N,SUM(DECODE(A_TYPE,1,1,0)) AS D,SUM(DECODE(A_TYPE,2,1,0)) AS I FROM QA_LOG";
        Query query = hibernateUtil.getSession().createSQLQuery(SQL);
        List<Object[]> result = query.list();
        if(result.size()>0) {
            Object[] objs = result.get(0);
            list.add(new PieVO("无答案", ((BigDecimal)objs[0]).longValue()));
            list.add(new PieVO("确定答案", ((BigDecimal)objs[1]).longValue()));
            list.add(new PieVO("不确定答案", ((BigDecimal)objs[2]).longValue()));
        }
        return list;
    }

}
