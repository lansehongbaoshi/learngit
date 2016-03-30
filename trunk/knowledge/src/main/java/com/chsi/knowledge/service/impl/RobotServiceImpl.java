package com.chsi.knowledge.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.chsi.framework.pojos.PersistentObject;
import com.chsi.framework.service.BaseDbService;
import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.ServiceConstants;
import com.chsi.knowledge.dao.RobotDAO;
import com.chsi.knowledge.dic.AType;
import com.chsi.knowledge.dic.QType;
import com.chsi.knowledge.index.service.KnowIndexService;
import com.chsi.knowledge.pojo.ALogData;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.QALogData;
import com.chsi.knowledge.pojo.RobotASetData;
import com.chsi.knowledge.pojo.RobotQSetData;
import com.chsi.knowledge.service.RobotService;
import com.chsi.knowledge.service.ServiceFactory;
import com.chsi.knowledge.util.ManageCacheUtil;
import com.chsi.knowledge.vo.AnswerVO;
import com.chsi.knowledge.vo.KnowListVO;
import com.chsi.knowledge.vo.SearchVO;
import com.chsi.knowledge.vo.PieVO;
import com.chsi.search.client.vo.KnowledgeVO;

public class RobotServiceImpl extends BaseDbService implements RobotService {
    RobotDAO robotDAO;
    
    @Override
    protected void doCreate() {
        robotDAO = getDAO(ServiceConstants.ROBOT_DAO, RobotDAO.class);
    }

    @Override
    protected void doRemove() {
        
    }

    @Override
    public void save(PersistentObject pojo) {
        robotDAO.save(pojo);
    }
    
    public AnswerVO answer(String sessionId, String knowId, String q) {
        AnswerVO answerVO = null;
        if (!ValidatorUtil.isNull(knowId)) {// 确定知识时传递knowId、q
            KnowledgeData knowledgeData = ManageCacheUtil.getKnowledgeDataById(knowId);
            QALogData qaLogData = new QALogData();
            qaLogData.setSessionId(sessionId);
            qaLogData.setQType(QType.NONCUSTOM);
            qaLogData.setQ(knowledgeData.getArticle().getTitle());
            qaLogData.setAType(AType.DEFINITE);
            qaLogData.setCreateTime(Calendar.getInstance());
            save(qaLogData);
            ALogData aLogData = new ALogData();
            aLogData.setQaLogId(qaLogData.getId());
            aLogData.setCmsId(knowledgeData.getCmsId());
            // aLogData.setCmsVersion(cmsVersion);后期要用到版本信息
            save(aLogData);
            SearchVO searchVO = new SearchVO(knowledgeData.getSystemData().getId(), knowledgeData.getSystemData().getName(), knowId, knowledgeData.getArticle().getTitle(), knowledgeData.getArticle().getContent());
            answerVO = new AnswerVO<SearchVO>();
            answerVO.setAType(AType.DEFINITE);
            List<SearchVO> list = new ArrayList<SearchVO>();
            list.add(searchVO);
            answerVO.setResult(list);
        } else {
            answerVO = new AnswerVO<SearchVO>();
            q = q==null?"":q.trim();
            List<RobotASetData> robotAList = robotDAO.getAByQ(q);//先查是否是打招呼
            if(robotAList.size()>0) {//机器人常用语回答不记录后台日志
                int randomIndex = (int)(Math.random()*robotAList.size());
                String content = robotAList.get(randomIndex).getA();
                answerVO.setAType(AType.ROBOT);
                answerVO.setContent(content);
            } else {
                String keywords = q;
                KnowIndexService knowIndexService = ServiceFactory.getKnowIndexService();
                String definiteKeyword = String.format("\"%s\"", keywords);
                KnowListVO<KnowledgeVO> list = knowIndexService.searchTitle(definiteKeyword, 0, 5);//不分词，全匹配搜索
                AType aType = null;
                if(list.getKnows().size()>0) {
                    if(list.getKnows().size()==1) {
                        aType = AType.DEFINITE;
                        answerVO.setContent(list.getKnows().get(0).getContent());
                    } else {
                        aType = AType.INDEFINITE;
                    }
                } else {
                    list = knowIndexService.searchTitle(keywords, 0, 5);//全匹配搜索不到再分词搜索
                    if(list.getKnows().size()>0) {
                        aType = AType.INDEFINITE;
                    } else {
                        aType = AType.NONE;
                        robotAList = ManageCacheUtil.getRobotASetByQ("?");
                        int randomIndex = (int)(Math.random()*robotAList.size());
                        String content = robotAList.get(randomIndex).getA();
                        answerVO.setContent(content);
                    }
                }
                answerVO.setAType(aType);
                
                QALogData qaLogData = new QALogData();
                qaLogData.setSessionId(sessionId);
                qaLogData.setQType(QType.CUSTOM);
                qaLogData.setQ(q);
                qaLogData.setAType(aType);
                qaLogData.setCreateTime(Calendar.getInstance());
                save(qaLogData);
                
                List<SearchVO> list1 = new ArrayList<SearchVO>();
                for (KnowledgeVO knowledgeVO : list.getKnows()) {
                    ALogData aLogData = new ALogData();
                    aLogData.setQaLogId(qaLogData.getId());
                    KnowledgeData knowledgeData = ManageCacheUtil.getKnowledgeDataById(knowledgeVO.getKnowledgeId());
                    aLogData.setCmsId(knowledgeData.getCmsId());
                    // aLogData.setCmsVersion(cmsVersion);后期要用到版本信息
                    save(aLogData);
                    
                    SearchVO searchVO = new SearchVO(knowledgeVO.getSystemId(), knowledgeData.getSystemData().getName(), knowledgeVO.getKnowledgeId(), knowledgeVO.getTitle(), "");
                    list1.add(searchVO);
                }
                answerVO.setResult(list1);
            }
        }
        return answerVO;
    }

    @Override
    public Map<RobotQSetData, List<RobotASetData>> getRobotQASet(String qID) {
        Map<RobotQSetData, List<RobotASetData>> result = new LinkedHashMap<RobotQSetData, List<RobotASetData>>();
        List<RobotQSetData> qs = robotDAO.allQ();
        for(RobotQSetData key:qs) {
            if(!ValidatorUtil.isNull(qID) && !qID.equals(key.getId())) continue;
            List<RobotASetData> value = robotDAO.getAByQSet(key);
            result.put(key, value);
        }
        return result;
    }

    @Override
    public void deleteRobotQASet(String id) {
        RobotQSetData robotQSetData = robotDAO.getRobotQSetData(id);
        List<RobotASetData> list = robotDAO.getAByQSet(robotQSetData);
        for(RobotASetData data:list) {
            robotDAO.del(data);
        }
        robotDAO.del(robotQSetData);
    }

    @Override
    public List<RobotASetData> getAByExplicitQ(String q) {
        return robotDAO.getAByExplicitQ(q);
    }

    @Override
    public List<PieVO> totalSession() {
        return robotDAO.totalSession();
    }

    @Override
    public List<PieVO> totalQ() {
        return robotDAO.totalQ();
    }
}
