package com.chsi.knowledge.service.impl;

import java.util.Iterator;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.chsi.framework.page.Page;
import com.chsi.framework.page.PageUtil;
import com.chsi.framework.service.BaseDbService;
import com.chsi.knowledge.ServiceConstants;
import com.chsi.knowledge.dao.DiscussDataDAO;
import com.chsi.knowledge.dic.DiscussStatus;
import com.chsi.knowledge.pojo.DiscussData;
import com.chsi.knowledge.service.DiscussService;
import com.chsi.knowledge.util.Pagination;
import com.chsi.knowledge.vo.DiscussCountVO;
import com.chsi.knowledge.vo.DiscussInfoVO;
import com.chsi.knowledge.vo.KnowListVO;
import com.chsi.search.client.vo.KnowledgeVO;

public class DiscussServiceImpl extends BaseDbService implements DiscussService {

    private DiscussDataDAO discussDataDAO;
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Override
    protected void doCreate() {
        discussDataDAO=getDAO(ServiceConstants.DISCUSSDATA_DAO, DiscussDataDAO.class);
    }

    @Override
    protected void doRemove() {
       
    }

    @Override
    public void saveOrUpdate(DiscussData discussData) {
        discussDataDAO.saveOrUpdate(discussData);
    }

    @Override
    public DiscussCountVO getDiscussCountVOByKId(String KId) {
        return discussDataDAO.getDiscussCountVOByKId(KId);
    }

    @Override
    public KnowListVO<DiscussInfoVO> getDiscussInfoVOList(String KId, int start, int pageSize) {
        int count = discussDataDAO.getDiscussInfoVOList(KId);
        Iterator<DiscussInfoVO> iterator = discussDataDAO.getDiscussInfoVOList(KId, start*pageSize, pageSize).iterator();
        Page<DiscussInfoVO> page = PageUtil.getPage(iterator, start*pageSize, pageSize, count);
        Pagination pagination = new Pagination(page.getTotalCount(), page.getPageCount(), start);
        KnowListVO<DiscussInfoVO> knowListVO = new KnowListVO<DiscussInfoVO>(page.getList(), pagination);
        return knowListVO;
    }

    @Override
    public JSONObject getDiscussCount(String kId) {
        // TODO Auto-generated method stub
        JSONObject json = new JSONObject();
        
        int total = discussDataDAO.getCountByKId(kId,null);
        int useful = discussDataDAO.getCountByKId(kId,DiscussStatus.USEFUL);
        int unuseful = discussDataDAO.getCountByKId(kId,DiscussStatus.UNUSEFUL);
        json.put("total", total);
        json.put("useful", useful);
        json.put("unuseful", unuseful);
        return json;
    }

   /* @Override
    public DiscussVO getDiscussVOByKnowledgeId(String knowledgeId) {
        List<DiscussData> list = discussDataDAO.getDiscusssByKnowledgeId(knowledgeId);
        KnowledgeService knowledgeService = ServiceFactory.getKnowledgeService();
        KnowledgeVO knowledgeVO = knowledgeService.getKnowledgeVOById(knowledgeId);
        DiscussVO discussVO = new DiscussVO();
        discussVO.setDiscussDataList(list);
        discussVO.setKnowledgeVO(knowledgeVO);
        return discussVO;
    }*/
   
}