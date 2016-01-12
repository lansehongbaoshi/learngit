package com.chsi.knowledge.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.chsi.account.client.AccountServiceClient;
import com.chsi.account.client.AccountServiceClientFactory;
import com.chsi.account.client.UserAccountData;
import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.knowledge.dao.DiscussDataDAO;
import com.chsi.knowledge.pojo.DiscussData;
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
        Object[] obj = (Object[])query.uniqueResult();
        DiscussCountVO vo = new DiscussCountVO();
        vo.setUseful(obj[0] == null ? "0" : format(obj[0].toString()));
        vo.setUnuseful(obj[1] == null ? "0" : format(obj[1].toString()));
        vo.setSum(obj[2] == null ? "0" : format(obj[2].toString()));
        vo.setUsefulPersent(obj[3] == null ? "0" : obj[3].toString());
        vo.setUnusefulPersent(obj[4] == null ? "0" : obj[4].toString());
        return vo;
    }

    private static String format(String str){
        char[] att = str.toCharArray();
        int x = att.length % 3;
        String result = "";
        for(int i=0;i<x;i++){
            result += att[i];
        }
        if(att.length != x){
            for(int i=x,j=0;i<att.length;i++,j++){
                if(j % 3 == 0) {
                    if(x==0 && j==0 ){
                    }else{
                        result += ',';
                    }
                }   
                result += att[i];
            }
        }
        return result;
    }
    
    @Override
    public List<DiscussInfoVO> getDiscussInfoVOList(String KId,int start,int pageSize) {
        String sql = " SELECT D.USER_ID,D.CONTENT,TO_CHAR(D.CREATE_TIME,'yyyy-MM-dd hh24:mm:ss') FROM DISCUSS D WHERE D.KNOWLEDGE_ID =:KId AND D.CONTENT IS NOT NULL order by D.CREATE_TIME desc ";
        Query query = hibernateUtil.getSession().createSQLQuery(sql).setString("KId", KId);
        query.setFirstResult(start);
        query.setMaxResults(pageSize);
        List<Object[]> objects = query.list();
        List<DiscussInfoVO> voList = new ArrayList<DiscussInfoVO>();
        for(Object[] obj:objects){
            DiscussInfoVO vo = new DiscussInfoVO();
            vo.setUserId((String)obj[0]);
            
            AccountServiceClient accountServiceClient = AccountServiceClientFactory.getAccountServiceClient();
            UserAccountData userAccountData  =  accountServiceClient.getAccountById((String)obj[0]).getValue();
                vo.setUserName( userAccountData != null && !"".equals(userAccountData.getUsername()) ? userAccountData.getUsername() : "游客");
            vo.setContent((String)obj[1]);
            vo.setTime((String)obj[2]);
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public int getDiscussInfoVOList(String KId) {
        String sql = " SELECT sum(1) FROM DISCUSS D WHERE D.KNOWLEDGE_ID =:KId AND D.CONTENT IS NOT NULL ";
        Query query = hibernateUtil.getSession().createSQLQuery(sql).setString("KId", KId);
        Object obj = query.uniqueResult();
        return obj==null?0:Integer.parseInt(String.valueOf(obj));
        
    }

}
