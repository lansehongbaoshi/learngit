package com.chsi.knowledge.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.service.DiscussService;
import com.chsi.knowledge.service.ServiceFactory;
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.util.Pagination;
import com.chsi.knowledge.vo.DiscussCountVO;
import com.chsi.knowledge.vo.DiscussInfoVO;
import com.chsi.knowledge.vo.KnowListVO;
import com.chsi.knowledge.vo.SystemStatisticsVO;
import com.chsi.search.client.vo.KnowledgeVO;

public class DiscussServiceImpl extends BaseDbService implements DiscussService {

    private DiscussDataDAO discussDataDAO;

    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    protected void doCreate() {
        discussDataDAO = getDAO(ServiceConstants.DISCUSSDATA_DAO, DiscussDataDAO.class);
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
        Iterator<DiscussInfoVO> iterator = discussDataDAO.getDiscussInfoVOList(KId, start * pageSize, pageSize).iterator();
        Page<DiscussInfoVO> page = PageUtil.getPage(iterator, start * pageSize, pageSize, count);
        Pagination pagination = new Pagination(page.getTotalCount(), page.getPageCount(), start);
        KnowListVO<DiscussInfoVO> knowListVO = new KnowListVO<DiscussInfoVO>(page.getList(), pagination);
        return knowListVO;
    }

    @Override
    public JSONObject getDiscussCount(String kId) {
        // TODO Auto-generated method stub
        JSONObject json = new JSONObject();

        int total = discussDataDAO.getCountByKId(kId, null);
        int useful = discussDataDAO.getCountByKId(kId, DiscussStatus.USEFUL);
        int unuseful = discussDataDAO.getCountByKId(kId, DiscussStatus.UNUSEFUL);
        json.put("total", total);
        json.put("useful", useful);
        json.put("unuseful", unuseful);
        return json;
    }

    @Override
    public List<SystemStatisticsVO> getSystemStatistics() {
        // TODO Auto-generated method stub
        SystemService systemService = ServiceFactory.getSystemService();
        List<SystemData> systems = systemService.getSystems(false,null);
        List<SystemStatisticsVO> systemStatisticsVOs = new ArrayList<SystemStatisticsVO>();
        if(systems!=null&&systems.size()>0){
            for(SystemData system : systems){
                List<KnowledgeData> knowledges = systemService.getKnowsBySystem(system.getId());
                if(knowledges!=null&&knowledges.size()>0){
                    SystemStatisticsVO vo = new SystemStatisticsVO();
                    Object[] obj = discussDataDAO.getSystemStatictics(system.getId());
                    vo.setName(system.getName());
                    vo.setSystem(system);
                    if("0".equals(obj[0].toString())){
                        vo.setGoodNum(0);
                        vo.setGoodPercent(0);
                        vo.setBadNum(0);
                        vo.setBadPercent(0);
                    }else{
                        vo.setGoodNum(Integer.parseInt(obj[1].toString()));
                        float a = Float.parseFloat(new Integer(Math.round(Float.parseFloat(obj[1].toString())/Float.parseFloat(obj[0].toString())*10000)).toString())/100;
                        vo.setGoodPercent(a);
                        vo.setBadNum(Integer.parseInt(obj[0].toString())-Integer.parseInt(obj[1].toString()));
                        vo.setBadPercent(100-a);
                    }
                    
                    systemStatisticsVOs.add(vo);
                }
            }
        }else{
            return null;
        }
        
        
        return systemStatisticsVOs;
    }

    @Override
    public List<DiscussCountVO> getBadKnowledgeRank() {
        // TODO Auto-generated method stub
        List<DiscussCountVO> list = discussDataDAO.getBadKnowledgeRank();
        for(DiscussCountVO vo : list){
            String unusefulPersent =String.format("%.2f", new Float(vo.getUnusefulPersent())) ;
            vo.setUnusefulPersent(unusefulPersent);
            KnowledgeData know = vo.getKnowledge();
            vo.setTitle(know.getTitle());
        }
        return list;
    }

    @Override
    public List<DiscussCountVO> getKnowledgeInSystemTopBad(String systemId) {
        // TODO Auto-generated method stub
        int discuss = 0;
        List<DiscussCountVO> list = discussDataDAO.getKnowledgeInSystemTop(systemId,discuss);
        for(DiscussCountVO vo : list){
            String unusefulPersent =String.format("%.2f", new Float(vo.getUnusefulPersent())) ;
            vo.setUnusefulPersent(unusefulPersent);
            KnowledgeData know = vo.getKnowledge();
            vo.setTitle(know.getTitle());
        }
        return list;
    }

    @Override
    public List<DiscussCountVO> getKnowledgeInSystemTopGood(String systemId) {
        // TODO Auto-generated method stub
        int discuss = 1;
        List<DiscussCountVO> list = discussDataDAO.getKnowledgeInSystemTop(systemId,discuss);
        for(DiscussCountVO vo : list){
            String usefulPersent =String.format("%.2f", new Float(vo.getUsefulPersent())) ;
            vo.setUnusefulPersent(usefulPersent);
            KnowledgeData know = vo.getKnowledge();
            vo.setTitle(know.getTitle());
        }
        return list;
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