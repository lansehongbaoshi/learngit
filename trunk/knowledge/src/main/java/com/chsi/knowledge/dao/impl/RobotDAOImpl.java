package com.chsi.knowledge.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.framework.page.Page;
import com.chsi.framework.pojos.PersistentObject;
import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.dao.RobotDAO;
import com.chsi.knowledge.dic.AType;
import com.chsi.knowledge.pojo.ALogData;
import com.chsi.knowledge.pojo.QALogData;
import com.chsi.knowledge.pojo.QASessionData;
import com.chsi.knowledge.pojo.RobotASetData;
import com.chsi.knowledge.pojo.RobotQSetData;
import com.chsi.knowledge.util.PageUtil;
import com.chsi.knowledge.vo.PieVO;

public class RobotDAOImpl extends BaseHibernateDAO implements RobotDAO {
    private static String query_robot_a_by_like_q = "select aa from RobotQSetData qq,RobotASetData aa where aa.qId=qq.id and qq.q like (:q)";
    private static String query_robot_a_by_q = "select aa from RobotQSetData qq,RobotASetData aa where aa.qId=qq.id and qq.q = :q";
    private static String query_all_q = "from RobotQSetData";
    private static String query_qa_log_by_a_type = "from QALogData where aType=:aType";
    private static String from_qa_log_by_a_type_page = "from QALogData p,QASessionData q where p.sessionId=q.id and p.aType=? ";
    private static String select_qa_log_by_a_type_page = "select p from QALogData p,QASessionData q where p.sessionId=q.id and p.aType=? ";
    private static String query_a_log_by_a_type_page = "from ALogData where qaLogId=:qaLogId";
    private static String query_qa_session_by_id = "from QASessionData where id=:id";
    private static String from_a = "from RobotQSetData";
    private static String query_a_by_q = "select p from RobotASetData p where p.qId=:qId";
    private static String query_q_by_q = "select q from RobotASetData q where q.q=:q";

    private static String count = "select count(*) ";

    private static String w_id = " where id=:id";
    private static String w_q = " where q=:q";
    private static String a_p_createTime = " and to_char(p.createTime,'yyyy-mm-dd') between ? and ?";
    private static String a_q_system_id_null = " and q.systemId is null";
    private static String a_q_system_id = " and q.systemId=?";

    private static String order_by_session_id_create_time_desc = " order by p.sessionId,p.createTime desc";

    @Override
    public void save(PersistentObject pojo) {
        hibernateUtil.getSession().save(pojo);
    }

    @Override
    public void update(PersistentObject pojo) {
        hibernateUtil.getSession().update(pojo);
    }

    @Override
    public QASessionData getQASessionDataById(String id) {
        String hql = query_qa_session_by_id;
        Query query = hibernateUtil.getSession().createQuery(hql);
        query.setString("id", id);
        return (QASessionData) query.uniqueResult();
    }

    @Override
    public List<RobotASetData> getAByQ(String q) {
        String hql = query_robot_a_by_like_q;
        Query query = hibernateUtil.getSession().createQuery(hql);
        if (ValidatorUtil.isNull(q)) {
            query.setString("q", "#blank");
        } else {
            query.setString("q", "%" + q + "%");
        }
        return query.list();
    }

    public RobotQSetData getRobotQSetByQ(String ques) {
        String hql = query_all_q + w_q;
        Query query = hibernateUtil.getSession().createQuery(hql);
        query.setString("q", ques);
        return (RobotQSetData) query.uniqueResult();
    }

    @Override
    public List<RobotQSetData> allQ() {
        String hql = query_all_q;
        Query query = hibernateUtil.getSession().createQuery(hql);
        return query.list();
    }

    public List<RobotQSetData> pageQ(int start, int max) {
        String hql = query_all_q;
        Query query = hibernateUtil.getSession().createQuery(hql);
        query.setFirstResult(0);
        query.setMaxResults(10);
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
        return (RobotQSetData) query.uniqueResult();
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
        return (RobotASetData) query.uniqueResult();
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
        BigDecimal total = (BigDecimal) query.uniqueResult();

        SQL = "SELECT COUNT(DISTINCT SESSION_ID) FROM QA_LOG";
        query = hibernateUtil.getSession().createSQLQuery(SQL);
        BigDecimal cnt = (BigDecimal) query.uniqueResult();

        list.add(new PieVO("有效会话", cnt.longValue()));
        list.add(new PieVO("空会话", total.longValue() - cnt.longValue()));

        return list;
    }

    @Override
    public List<PieVO> totalQ() {
        List<PieVO> list = new ArrayList<PieVO>();

        String SQL = "SELECT SUM(DECODE(A_TYPE,0,1,0)) AS N,SUM(DECODE(A_TYPE,1,1,0)) AS D,SUM(DECODE(A_TYPE,2,1,0)) AS I FROM QA_LOG";
        Query query = hibernateUtil.getSession().createSQLQuery(SQL);
        List<Object[]> result = query.list();
        if (result.size() > 0) {
            Object[] objs = result.get(0);
            list.add(new PieVO("无答案", ((BigDecimal) objs[0]).longValue()));
            list.add(new PieVO("确定答案", ((BigDecimal) objs[1]).longValue()));
            list.add(new PieVO("不确定答案", ((BigDecimal) objs[2]).longValue()));
        }
        return list;
    }

    @Override
    public List<PieVO> totalSession(String startTime, String endTime) {
        List<PieVO> list = new ArrayList<PieVO>();

        String SQL = "SELECT COUNT(*) FROM QA_SESSION where to_char(START_TIME,'yyyy-mm-dd') between :startTime and :endTime";
        Query query = hibernateUtil.getSession().createSQLQuery(SQL).setString("startTime", startTime).setString("endTime", endTime);
        BigDecimal total = (BigDecimal) query.uniqueResult();

        SQL = "SELECT COUNT(DISTINCT SESSION_ID) FROM QA_LOG where to_char(CREATE_TIME,'yyyy-mm-dd') between :startTime and :endTime";
        query = hibernateUtil.getSession().createSQLQuery(SQL).setString("startTime", startTime).setString("endTime", endTime);
        BigDecimal cnt = (BigDecimal) query.uniqueResult();

        list.add(new PieVO("有效会话", cnt.longValue()));
        list.add(new PieVO("空会话", total.longValue() - cnt.longValue()));

        return list;
    }

    @Override
    public List<PieVO> totalQ(String systemId, String startTime, String endTime) {
        List<PieVO> list = new ArrayList<PieVO>();

        String SQL = "SELECT SUM(DECODE(A_TYPE,0,1,0)) AS N,SUM(DECODE(A_TYPE,1,1,0)) AS D,SUM(DECODE(A_TYPE,2,1,0)) AS I FROM QA_LOG A,QA_SESSION B where to_char(A.CREATE_TIME,'yyyy-mm-dd') between :startTime and :endTime AND A.SESSION_ID=B.ID";
        if (ValidatorUtil.isNull(systemId)) {
            SQL += " AND B.SYSTEM_ID IS NULL";
        } else {
            SQL += " AND B.SYSTEM_ID=:systemId";
        }
        Query query = hibernateUtil.getSession().createSQLQuery(SQL).setString("startTime", startTime).setString("endTime", endTime);
        if (!ValidatorUtil.isNull(systemId)) {
            query.setString("systemId", systemId);
        }
        Object[] result = (Object[]) query.uniqueResult();
        if (result != null) {
            list.add(new PieVO("无答案", result[0] == null ? 0 : ((BigDecimal) result[0]).longValue()));
            list.add(new PieVO("确定答案", result[1] == null ? 0 : ((BigDecimal) result[1]).longValue()));
            list.add(new PieVO("不确定答案", result[2] == null ? 0 : ((BigDecimal) result[2]).longValue()));
        }
        return list;
    }

    @Override
    public List<QALogData> listQALogDataByAType(AType aType) {
        String hql = query_qa_log_by_a_type;
        Query query = hibernateUtil.getSession().createQuery(hql);
        query.setInteger("aType", aType.ordinal());
        return query.list();
    }

    @Override
    public Page<QALogData> pageQALogDataByAType(String systemId, AType aType, int currentPage, int pageSize, String startTime, String endTime) {
        String countyHql;
        String queryHql;
        if (ValidatorUtil.isNull(systemId)) {
            countyHql = count + from_qa_log_by_a_type_page + a_p_createTime + a_q_system_id_null;
            queryHql = select_qa_log_by_a_type_page + a_p_createTime + a_q_system_id_null + order_by_session_id_create_time_desc;
        } else {
            countyHql = count + from_qa_log_by_a_type_page + a_p_createTime + a_q_system_id;
            queryHql = select_qa_log_by_a_type_page + a_p_createTime + a_q_system_id + order_by_session_id_create_time_desc;
        }

        Page page = PageUtil.getPage(hibernateUtil.getSession(), currentPage, pageSize, countyHql, queryHql, aType, startTime, endTime);
        return page;
    }

    public Map<RobotQSetData, List<RobotASetData>> getSolrByRQ(RobotQSetData robotQSetData) {
        Map<RobotQSetData, List<RobotASetData>> map = new HashMap<RobotQSetData, List<RobotASetData>>();
        List<RobotASetData> list = new ArrayList<RobotASetData>();
        list = getAByQSet(robotQSetData);
        map.put(robotQSetData, list);
        return map;
    }

    @Override
    public List<ALogData> listALogDataByQId(String qaLogId) {
        String hql = query_a_log_by_a_type_page;
        Query query = hibernateUtil.getSession().createQuery(hql);
        query.setString("qaLogId", qaLogId);
        return query.list();
    }

}
