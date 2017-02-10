package com.chsi.knowledge.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;

import com.chsi.account.client.AccountServiceClient;
import com.chsi.account.client.AccountServiceClientFactory;
import com.chsi.account.client.UserAccountData;
import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.knowledge.Constants;
import com.chsi.knowledge.dao.DiscussDataDAO;
import com.chsi.knowledge.dic.DiscussStatus;
import com.chsi.knowledge.pojo.DiscussData;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.util.ManageCacheUtil;
import com.chsi.knowledge.util.MemCachedUtil;
import com.chsi.knowledge.vo.DiscussCountVO;
import com.chsi.knowledge.vo.DiscussInfoVO;

public class DiscussDataDAOImpl extends BaseHibernateDAO implements DiscussDataDAO {

    private static final String SELECT_DISCUSS = "select p from DiscussData p";

    private static final String KNOWLEDGEID = " p.knowledgeId= :knowledgeId";
    private static final String W = " where ";

    @Override
    public void saveOrUpdate(DiscussData discussData) {
        hibernateUtil.saveOrUpdate(discussData);
    }

    @Override
    public List<DiscussData> getDiscusssByKnowledgeId(String knowledgeId) {
        String hql = SELECT_DISCUSS + W + KNOWLEDGEID;
        Query query = hibernateUtil.getSession().createQuery(hql).setString("knowledgeId", knowledgeId);
        List<DiscussData> list = query.list();
        return list.size() == 0 ? null : list;
    }

    @Override
    public DiscussCountVO getDiscussCountVOByKId(String KId) {
        String sql = " SELECT SUM(DECODE (D.DISCUSS,1,1,0)), SUM(DECODE (D.DISCUSS,0,1,0)), SUM(1), ROUND(SUM(DECODE (D.DISCUSS,1,1,0))/SUM(1), 2)*100, 100-ROUND(SUM(DECODE (D.DISCUSS,  1, 1 , 0))/SUM(1), 2)*100 FROM DISCUSS D WHERE D.KNOWLEDGE_ID = :KId ";
        Query query = hibernateUtil.getSession().createSQLQuery(sql).setString("KId", KId);
        Object[] obj = (Object[]) query.uniqueResult();
        DiscussCountVO vo = new DiscussCountVO();
        vo.setUseful(obj[0] == null ? "0" : format(obj[0].toString()));
        vo.setUnuseful(obj[1] == null ? "0" : format(obj[1].toString()));
        vo.setSum(obj[2] == null ? "0" : format(obj[2].toString()));
        vo.setUsefulPersent(obj[3] == null ? "0" : obj[3].toString());
        vo.setUnusefulPersent(obj[4] == null ? "0" : obj[4].toString());
        return vo;
    }

    private static String format(String str) {
        char[] att = str.toCharArray();
        int x = att.length % 3;
        String result = "";
        for (int i = 0; i < x; i++) {
            result += att[i];
        }
        if (att.length != x) {
            for (int i = x, j = 0; i < att.length; i++, j++) {
                if (j % 3 == 0) {
                    if (x == 0 && j == 0) {
                    } else {
                        result += ',';
                    }
                }
                result += att[i];
            }
        }
        return result;
    }

    @Override
    public List<DiscussInfoVO> getDiscussInfoVOList(String KId, int start, int pageSize) {
        String sql = " SELECT D.USER_ID,D.CONTENT,TO_CHAR(D.CREATE_TIME,'yyyy-MM-dd hh24:mi:ss') FROM DISCUSS D WHERE D.KNOWLEDGE_ID =:KId AND D.CONTENT IS NOT NULL order by D.CREATE_TIME desc ";
        Query query = hibernateUtil.getSession().createSQLQuery(sql).setString("KId", KId);
        query.setFirstResult(start);
        query.setMaxResults(pageSize);
        List<Object[]> objects = query.list();
        List<DiscussInfoVO> voList = new ArrayList<DiscussInfoVO>();
        for (Object[] obj : objects) {
            DiscussInfoVO vo = new DiscussInfoVO();
            vo.setUserId((String) obj[0]);

            AccountServiceClient accountServiceClient = AccountServiceClientFactory.getAccountServiceClient();
            UserAccountData userAccountData = accountServiceClient.getAccountById((String) obj[0]).getValue();
            vo.setUserName(userAccountData != null && !"".equals(userAccountData.getUsername()) ? userAccountData.getUsername() : "游客");
            vo.setContent((String) obj[1]);
            vo.setTime((String) obj[2]);
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public int getDiscussInfoVOList(String KId) {
        String sql = " SELECT sum(1) FROM DISCUSS D WHERE D.KNOWLEDGE_ID =:KId AND D.CONTENT IS NOT NULL ";
        Query query = hibernateUtil.getSession().createSQLQuery(sql).setString("KId", KId);
        Object obj = query.uniqueResult();
        return obj == null ? 0 : Integer.parseInt(String.valueOf(obj));

    }

    @Override
    public int getCountByKId(String kId, DiscussStatus status) {
        // TODO Auto-generated method stub
        String sql = null;
        if (status == null) {
            sql = " SELECT sum(1) FROM DISCUSS D WHERE D.KNOWLEDGE_ID =:KId ";
        } else if (status == DiscussStatus.USEFUL) {
            sql = " SELECT sum(1) FROM DISCUSS D WHERE D.KNOWLEDGE_ID =:KId AND D.DISCUSS = '1' ";
        } else if (status == DiscussStatus.UNUSEFUL) {
            sql = " SELECT sum(1) FROM DISCUSS D WHERE D.KNOWLEDGE_ID =:KId AND D.DISCUSS = '0' ";
        }
        Query query = hibernateUtil.getSession().createSQLQuery(sql).setString("KId", kId);
        Object obj = query.uniqueResult();
        return obj == null ? 0 : Integer.parseInt(String.valueOf(obj));
    }

    @Override
    public List<DiscussCountVO> getBadKnowledgeRank() {
        // TODO Auto-generated method stub
        String sql = "select A.knowledge_id, A.total, B.badCount, B.badCount / A.total*100 rank " +
        		"   from (select knowledge_id, count(*) total " +
        		"           from discuss " +
        		"          group by knowledge_id " +
        		"        having count(*) > "+Constants.MIX_DISCUSS_COUNT+") A " +
        		"  left join (select knowledge_id, count(*) badCount " +
        		"                from discuss " +
        		"               group by knowledge_id, discuss " +
        		"              having discuss = '0') B " +
        		"     on A.knowledge_id = B.knowledge_id " +
        		"  where rownum < 11 " +
        		"  order by rank desc ";
        SQLQuery query = hibernateUtil.getSession().createSQLQuery(sql);
        List<Object[]> objects = query.list();
        List<DiscussCountVO> result = new ArrayList<DiscussCountVO>();
        for(Object[] obj:objects){
            DiscussCountVO vo = new DiscussCountVO();
            KnowledgeData knowledge = ManageCacheUtil.getKnowledgeDataById((String)obj[0]);
            vo.setKnowledge(knowledge);
            vo.setSum(obj[1].toString());
            vo.setUnuseful(obj[2].toString());
            vo.setUnusefulPersent(obj[3].toString());
            result.add(vo);
        }
        return result;
    }

    @Override
    public List<DiscussCountVO> getGoodKnowledgeRank() {
        String sql = "select A.knowledge_id, A.total, B.goodCount, B.goodCount / A.total*100 rank " +
                "   from (select knowledge_id, count(*) total " +
                "           from discuss " +
                "          group by knowledge_id " +
                "        having count(*) > "+Constants.MIX_DISCUSS_COUNT+") A " +
                "  left join (select knowledge_id, count(*) goodCount " +
                "                from discuss " +
                "               group by knowledge_id, discuss " +
                "              having discuss = '1') B " +
                "     on A.knowledge_id = B.knowledge_id " +
                "  where rownum < 11 " +
                "  order by rank desc ";
        SQLQuery query = hibernateUtil.getSession().createSQLQuery(sql);
        List<Object[]> objects = query.list();
        List<DiscussCountVO> result = new ArrayList<DiscussCountVO>();
        for(Object[] obj:objects){
            DiscussCountVO vo = new DiscussCountVO();
            KnowledgeData knowledge = ManageCacheUtil.getKnowledgeDataById((String)obj[0]);
            vo.setKnowledge(knowledge);
            vo.setSum(obj[1].toString());
            vo.setUseful(obj[2].toString());
            vo.setUsefulPersent(obj[3].toString());
            result.add(vo);
        }
        return result;
    }

    @Override
    public Object[] getSystemStatictics(String systemId) {
        // TODO Auto-generated method stub
        String sql ="select count(*),sum(discuss) from discuss A where A.Knowledge_Id in (select knowledge_id from knowledge_tag_relation where tag_id in (select id from tag where system_id='"+systemId+"')) order by A.knowledge_id";
        SQLQuery query = hibernateUtil.getSession().createSQLQuery(sql);
        List<Object[]> objects = query.list();
        Object[] obj = null;
        if(objects.size()>0){
            obj = objects.get(0);
            if(obj[0]==null) obj[0] = "0";
            if(obj[1]==null) obj[1] = "0";
            return obj;
        }else{
            return null;
        }
    }

    @Override
    public List<DiscussCountVO> getKnowledgeInSystemTop(String systemId,
            int discuss) {
        // TODO Auto-generated method stub
        String sql ="select A.knowledge_id,A.total,B.xSum,B.xSum/A.total*100 ranks from (select knowledge_id,count(*) total from discuss where Knowledge_Id in (select knowledge_id from knowledge_tag_relation where tag_id in (select id from tag where system_id='"+systemId+"'))  group by knowledge_id having count(*)>"+Constants.MIX_DISCUSS_COUNT+" order by knowledge_id) A left join (select knowledge_id,count(*) xSum from discuss where Knowledge_Id in (select knowledge_id from knowledge_tag_relation where tag_id in (select id from tag where system_id='"+systemId+"')) and discuss="+discuss+" group by knowledge_id order by knowledge_id) B on A.knowledge_id=B.knowledge_id where B.xSum>0 and rownum<11 order by ranks desc";
        SQLQuery query = hibernateUtil.getSession().createSQLQuery(sql);
        List<Object[]> objects = query.list();
        List<DiscussCountVO> result = new ArrayList<DiscussCountVO>();
        for(Object[] obj:objects){
            DiscussCountVO vo = new DiscussCountVO();
            KnowledgeData knowledge = ManageCacheUtil.getKnowledgeDataById((String)obj[0]);
            vo.setKnowledge(knowledge);
            vo.setSum(obj[1].toString());
            if(discuss==0){
                if(obj[2]==null){
                    vo.setUnuseful("0");
                    vo.setUnusefulPersent("0");
                }else{
                    vo.setUnuseful(obj[2].toString());
                    vo.setUnusefulPersent(obj[3].toString());
                }
            }else{
                if(obj[2]==null){
                    vo.setUseful("0");
                    vo.setUsefulPersent("0");
                }else{
                    vo.setUseful(obj[2].toString());
                    vo.setUsefulPersent(obj[3].toString());
                }
            }
            
            
            result.add(vo);
        }
        return result;
    }

}
