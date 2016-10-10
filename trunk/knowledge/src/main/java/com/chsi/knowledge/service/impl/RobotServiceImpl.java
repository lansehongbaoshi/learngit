package com.chsi.knowledge.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.chsi.framework.page.Page;
import com.chsi.framework.pojos.PersistentObject;
import com.chsi.framework.service.BaseDbService;
import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.ServiceConstants;
import com.chsi.knowledge.dao.RobotDAO;
import com.chsi.knowledge.dao.WeatherCodeDataDAO;
import com.chsi.knowledge.dic.AType;
import com.chsi.knowledge.dic.QType;
import com.chsi.knowledge.index.service.KnowIndexService;
import com.chsi.knowledge.pojo.ALogData;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.QALogData;
import com.chsi.knowledge.pojo.QASessionData;
import com.chsi.knowledge.pojo.RobotASetData;
import com.chsi.knowledge.pojo.RobotQSetData;
import com.chsi.knowledge.pojo.WeatherCodeData;
import com.chsi.knowledge.robot.intent.Intent;
import com.chsi.knowledge.service.RobotService;
import com.chsi.knowledge.service.ServiceFactory;
import com.chsi.knowledge.util.ManageCacheUtil;
import com.chsi.knowledge.util.Pagination;
import com.chsi.knowledge.util.SearchUtil;
import com.chsi.knowledge.vo.AnswerVO;
import com.chsi.knowledge.vo.KnowListVO;
import com.chsi.knowledge.vo.PieVO;
import com.chsi.knowledge.vo.RobotQAListVO;
import com.chsi.knowledge.vo.SearchVO;
import com.chsi.search.client.SearchServiceClient;
import com.chsi.search.client.SearchServiceClientFactory;
import com.chsi.search.client.vo.KnowledgeVO;
import com.chsi.search.client.vo.RobotQABean;
@SuppressWarnings("unchecked")
public class RobotServiceImpl extends BaseDbService implements RobotService {
    RobotDAO robotDAO;
    WeatherCodeDataDAO weatherCodeDataDAO;
    
    @Override
    protected void doCreate() {
        robotDAO = getDAO(ServiceConstants.ROBOT_DAO, RobotDAO.class);
        weatherCodeDataDAO = getDAO(ServiceConstants.WeatherCodeData_DAO, WeatherCodeDataDAO.class);
    }

    @Override
    protected void doRemove() {
        
    }

    @Override
    public void save(PersistentObject pojo) {
        robotDAO.save(pojo);
    }
    
    @Override
    public void update(PersistentObject pojo) {
        robotDAO.update(pojo);
    }
    
    @Override
    public QASessionData getQASessionDataById(String id) {
        return robotDAO.getQASessionDataById(id);
    }
    
    public RobotQAListVO<RobotQABean> searchRobotConf(String text,int start,int max){
        SearchServiceClient searchClient = SearchServiceClientFactory
                .getSearchServiceClient();
        Map<String, String> map = new HashMap<String, String>();
        String keywords = SearchUtil.keywordsFilter(text);
        if("".equals(keywords)){
            keywords = "*:*";
        }
        map.put("q", keywords);
        map.put("qf", "q");
        Page<RobotQABean> page = searchClient.searchRobotConf(map, start, max);
        Pagination pagination = new Pagination(page.getTotalCount(), page.getPageCount(), page.getCurPage());
        RobotQAListVO<RobotQABean> robotQAListVO = new RobotQAListVO<RobotQABean>(page.getList(), pagination);
        return robotQAListVO;
        
    }
    
    public AnswerVO answer(String sessionId, String knowId, String q) {
        AnswerVO answerVO = null;
        if (!ValidatorUtil.isNull(knowId)) {// 确定知识时传递knowId、q
            KnowledgeData knowledgeData = ManageCacheUtil.getKnowledgeDataById(knowId);
            QALogData qaLogData = new QALogData();
            qaLogData.setSessionId(sessionId);
            qaLogData.setQType(QType.NONCUSTOM);
            qaLogData.setQ(knowledgeData.getArticle().getTitle());
            qaLogData.setaType(AType.DEFINITE);
            qaLogData.setCreateTime(Calendar.getInstance());
            save(qaLogData);
            ALogData aLogData = new ALogData();
            aLogData.setQaLogId(qaLogData.getId());
            aLogData.setCmsId(knowledgeData.getCmsId());
            // aLogData.setCmsVersion(cmsVersion);后期要用到版本信息
            save(aLogData);
            SearchVO searchVO = new SearchVO(knowledgeData.getSystemDatas(), knowId, knowledgeData.getArticle().getTitle(), knowledgeData.getArticle().getContent());
            answerVO = new AnswerVO<SearchVO>();
            answerVO.setAType(AType.DEFINITE);
            answerVO.setContent(knowledgeData.getArticle().getContent());
            List<SearchVO> list = new ArrayList<SearchVO>();
            list.add(searchVO);
            answerVO.setResult(list);
        } else {
            answerVO = new AnswerVO<SearchVO>();
            q = q==null?"":q.trim();
            String keywords = SearchUtil.keywordsFilter2(q);
            if(ValidatorUtil.isNull(keywords)) {
                String content = ManageCacheUtil.getRobotABySpecialQ("#blank");
                answerVO.setAType(AType.ROBOT);
                answerVO.setContent(content);
                return answerVO;
            }
//            List<RobotASetData> robotAList = robotDAO.getAByQ(keywords);//先查是否是打招呼
            /*判断用户的某种意图             */
            Intent intent = new Intent(keywords,sessionId);
            if(intent.isExist()){//如果有用户的某种意图
                answerVO.setAType(AType.ROBOT);
                answerVO.setContent(intent.getContent());
            }else{
                SearchServiceClient searchClient = SearchServiceClientFactory
                        .getSearchServiceClient();
                Map<String, String> map = new HashMap<String, String>();
                map.put("q", "text:"+keywords);
                map.put("qf", "q");
                map.put("hl", "true");
                map.put("hl.fl", "q");
                map.put("hl.simple.pre", "<strong style='color:#c30'>");
                map.put("hl.simple.post", "</strong>");
                List<RobotQABean> robotAList = searchClient.searchRobotQA(map, 0, 100);
                if(robotAList.size()>0) {//机器人常用语回答不记录后台日志
                    int randomIndex = (int)(Math.random()*robotAList.size());
                    String[] anser = robotAList.get(0).getA();
                    String content = anser[(int)(Math.random()*anser.length)];
                    answerVO.setAType(AType.ROBOT);
                    answerVO.setContent(content);
                } else {
                    String definiteKeyword = SearchUtil.formatFullMatchKeyword(q);
                    Map<String, String> queryParams = new HashMap<String, String>();
                    queryParams.put("q", definiteKeyword);
                    queryParams.put("qf", "title");
                    queryParams.put("fq", "type:PUBLIC");
                    KnowIndexService knowIndexService = ServiceFactory.getKnowIndexService();
                    KnowListVO<KnowledgeVO> list = knowIndexService.customSearch(queryParams, 0, 5);//不分词，全匹配搜索
                    AType aType = null;
                    if(list.getKnows().size()>0) {
                        if(list.getKnows().size()==1) {
                            aType = AType.DEFINITE;
                            answerVO.setContent(list.getKnows().get(0).getContent());
                        } else {
                            aType = AType.INDEFINITE;
                        }
                    } else {
                        queryParams.put("q", keywords);
                        list = knowIndexService.customSearch(queryParams, 0, 5);//全匹配搜索不到再分词搜索
                        if(list.getKnows().size()>0) {
                            aType = AType.INDEFINITE;
                        } else {
                            aType = AType.NONE;
                            String content = ManageCacheUtil.getRobotABySpecialQ("#noanswer");
                            answerVO.setContent(content);
                        }
                    }
                    answerVO.setAType(aType);
                    
                    QALogData qaLogData = new QALogData();
                    qaLogData.setSessionId(sessionId);
                    qaLogData.setQType(QType.CUSTOM);
                    qaLogData.setQ(q);
                    qaLogData.setaType(aType);
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
                        
                        SearchVO searchVO = new SearchVO(knowledgeData.getSystemDatas(), knowledgeVO.getKnowledgeId(), knowledgeVO.getTitle(), "");
                        list1.add(searchVO);
                    }
                    answerVO.setResult(list1);
                }
            }
        }
        return answerVO;
    }

    @Override
    public Map<RobotQSetData, List<RobotASetData>> getRobotQASet(String qID) {
        Map<RobotQSetData, List<RobotASetData>> result = new LinkedHashMap<RobotQSetData, List<RobotASetData>>();
        RobotQSetData rqsd = robotDAO.getRobotQSetData(qID);
        List<RobotASetData> value = robotDAO.getAByQSet(rqsd);
        result.put(rqsd, value);
//        List<RobotQSetData> qs = robotDAO.pageQ(0, 10);
//        for(RobotQSetData key:qs) {
//            if(!ValidatorUtil.isNull(qID) && !qID.equals(key.getId())) continue;
//            List<RobotASetData> value = robotDAO.getAByQSet(key);
//            result.put(key, value);
//        }
        return result;
    }
    
    public List<RobotQABean> getRobotBasicConf(String[] qs){
        List<RobotQABean> list = new ArrayList<RobotQABean>();
        for(String question :qs){
            RobotQABean rqab = new RobotQABean();
            RobotQSetData rqs = robotDAO.getRobotQSetByQ(question);
            if(rqs!=null) {
            List<RobotASetData> listRAS = robotDAO.getAByQSet(rqs);
            rqab.setId(rqs.getId());
            rqab.setQ(rqs.getQ());
            rqab.setNum(rqs.getNum());
            String[] ans = new String[listRAS.size()];
            for(int i=0;i<listRAS.size();i++){
                ans[i]=listRAS.get(i).getA();
            }
            rqab.setA(ans);
            list.add(rqab);
            }
        }
        return list;
    }

    @Override
    public void deleteRobotQASet(String id) {
        RobotQSetData robotQSetData = robotDAO.getRobotQSetData(id);
        if(null==robotQSetData){
            return;
        }
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
    
    @Override
    public List<PieVO> totalSession(String startTime, String endTime) {
        return robotDAO.totalSession(startTime, endTime);
    }

    @Override
    public List<PieVO> totalQ(String startTime, String endTime) {
        return robotDAO.totalQ(startTime, endTime);
    }

    @Override
    public List<QALogData> listQALogDataByAType(AType aType) {
        return robotDAO.listQALogDataByAType(aType);
    }

    @Override
    public Page<QALogData> pageQALogDataByAType(AType aType, int currentPage, int pageSize, String startTime, String endTime) {
        return robotDAO.pageQALogDataByAType(aType, currentPage, pageSize, startTime, endTime);
    }

    @Override
    public boolean addWeatherAddr(String string) {
        // TODO Auto-generated method stub
        File file = new File(string);
        SAXReader reader = new SAXReader();
        Document doc = null;
        try {
            doc = reader.read(file);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<WeatherCodeData> weatherCodeDatas = new ArrayList<WeatherCodeData>();
        
        Element root = doc.getRootElement();
        List<Element> provinces = root.elements("province");
        for(Element province : provinces){
            List<Element> citys = province.elements("city");
            for(Element city : citys){
                List<Element> countys = city.elements("county");
                for(Element county : countys){
                    weatherCodeDatas.add(new WeatherCodeData(county.attribute("id").getValue(),county.attribute("name").getValue(),county.attribute("weatherCode").getValue()));
                }
            }
        }
        weatherCodeDataDAO.save(weatherCodeDatas);
        return false;
    }

    @Override
    public WeatherCodeData getWeatherCode(String string) {
        // TODO Auto-generated method stub
        
        for(int i=string.length();i>0;i--){
            for(int index=0;index<=string.length()-i;index++){
                String add = string.substring(index,index+i);
                WeatherCodeData weatherCodeData = weatherCodeDataDAO.getWeatherCodeByName(add);
                if(weatherCodeData!=null){
                    return weatherCodeData;
                }
            }
        }
        return null;
    }

    @Override
    public WeatherCodeData getWeatherCode(List<String> addrs) {
        // TODO Auto-generated method stub
        for(int i=addrs.size();i>0;i--){
            for(int index=0;index<=addrs.size()-i;index++){
                String add = "";
                for(int j=index;j<index+i;j++){
                    add += addrs.get(j);
                }
                WeatherCodeData weatherCodeData = weatherCodeDataDAO.getWeatherCodeByName(add);
                if(weatherCodeData!=null){
                    return weatherCodeData;
                }
            }
        }
        return null;
    }


}
