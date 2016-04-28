package com.chsi.knowledge.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.chsi.framework.pojos.PersistentObject;
import com.chsi.framework.service.BaseDbService;
import com.chsi.framework.util.TimeUtil;
import com.chsi.knowledge.ServiceConstants;
import com.chsi.knowledge.dao.CommonDAO;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.index.service.KnowIndexService;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.KnowledgeVisitLogData;
import com.chsi.knowledge.pojo.SearchLogData;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.pojo.SystemOpenTimeData;
import com.chsi.knowledge.service.CommonService;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.ServiceFactory;
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.util.ManageCacheUtil;
import com.chsi.knowledge.util.SearchUtil;
import com.chsi.knowledge.vo.CntVO;
import com.chsi.knowledge.vo.LineChartVO;
import com.chsi.knowledge.vo.SeriesVO;
import com.chsi.search.client.vo.KnowledgeVO;

public class CommonServiceImpl extends BaseDbService implements CommonService {
    private CommonDAO commonDAO;

    protected void doCreate() {
        commonDAO = getDAO(ServiceConstants.COMMON_DAO, CommonDAO.class);
    }

    protected void doRemove() {
        
    }

    public void save(PersistentObject po) {
        commonDAO.save(po);
    }

    public List<KnowledgeVO> getTopSearchKnow(int n) {
        Calendar startTime = Calendar.getInstance();
        startTime.add(Calendar.DAY_OF_MONTH, -10);
        List<Object[]> systemAndKeywords = commonDAO.getTopKeyword(n*4, startTime, Calendar.getInstance());//防止出现无效的关键词（即：没有搜索结果的关键词）所以多搜点
        List<KnowledgeVO> result = new ArrayList<KnowledgeVO>();
        KnowIndexService knowIndexService = ServiceFactory.getKnowIndexService();
        for(Object[] objs:systemAndKeywords) {
            String systemId = (String)objs[0];
            String keyword = (String)objs[1];
            if(!SearchUtil.isGoodKeyword(keyword)) continue;
            if(result.size()>=n) break;
            List<KnowledgeVO> list;
            if(systemId==null) {
                list = knowIndexService.searchKnow(keyword, 0, 2).getKnows();
            } else {
                list = knowIndexService.searchKnow(keyword, systemId, 0, 2).getKnows();
            }
            KnowledgeVO knowledgeVO = list!=null&&list.size()>0 ? list.get(0):null;
            if(knowledgeVO!=null&&!result.contains(knowledgeVO)) {
                result.add(knowledgeVO);
            }
        }
        return result;
    }

    public List<KnowledgeData> getTopKnowl(int total) {
        SystemService systemService = ServiceFactory.getSystemService();
        List<SystemOpenTimeData> systems = systemService.getOpenSystems();
        List<Integer> iList = splitCnt(total, systems.size());
        List<KnowledgeData> result = new ArrayList<KnowledgeData>();
        for(int i=0;i<systems.size();i++) {
            SystemOpenTimeData data = systems.get(i);
            int myCnt = iList.get(i);
            List<KnowledgeData> list2 = getTopKnowlBySystem(data, myCnt);
            result.addAll(list2);
        }
        return result;
    }
    
    public List<KnowledgeData> getTopKnowlBySystem(SystemOpenTimeData systemOpenTimeData, int total) {
        Calendar startTime = Calendar.getInstance();
        startTime.add(Calendar.DAY_OF_MONTH, -10);
        Calendar endTime = Calendar.getInstance();
        List<CntVO> listTemp = commonDAO.getTopVisitKnowl(systemOpenTimeData, startTime, endTime);
        Collections.sort(listTemp);
        List<KnowledgeData> result = new ArrayList<KnowledgeData>();
        int cnt = 0;
        for(int i=listTemp.size()-1;i>-1;i--) {
            if(cnt>=total) break;
            CntVO vo = listTemp.get(i);
            KnowledgeData data = ManageCacheUtil.getKnowledgeDataById(vo.getId());
            if(data!=null) {
                result.add(data);
                cnt++;
            }
        }
        return result;
    }
    
    @Override
    public void removeDuplicatedDatas() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Calendar cal2 = (Calendar)cal.clone();
        cal2.add(Calendar.DAY_OF_MONTH, 1);
        List<Object[]> list = commonDAO.getDuplicatedDatas(cal, cal2);
        log.info("冗余关键词总量："+list.size());
        int cnt = 0;
        for(Object[] objs:list) {
            String systemId = (String)objs[0];
            String keyword = (String)objs[1];
            String userIP = (String)objs[2];
//            log.info(String.format("冗余关键词{systemId:'%s',keyword:'%s',userIP:'%s'}", systemId, keyword, userIP));
            List<SearchLogData> logs = commonDAO.getTheDuplicatedData(systemId, keyword, userIP);
            for(int i=0;i<logs.size()-1;i++) {
                SearchLogData logData = logs.get(i);
                commonDAO.del(logData);
                log.info(String.format("删除冗余SEARCH_LOG{id:'%s',systemId:'%s',keyword:'%s',userIP:'%s',userID:'%s',createTime:'%s'}", logData.getId(), systemId, keyword, userIP, logData.getUserId(), TimeUtil.getTime(logData.getCreateTime(), "yyyyMMdd HH:mm:ss")));
                cnt++;
            }
        }
        log.info("删除的冗余数据量："+cnt);
    }
    
    @Override
    public void removeTrashKeywords() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Calendar cal2 = (Calendar)cal.clone();
        cal2.add(Calendar.DAY_OF_MONTH, 1);
        List<SearchLogData> logs = commonDAO.getSearchLogData(cal, cal2);
        int cnt = 0;
        for(SearchLogData logData:logs) {
            if(!SearchUtil.isGoodKeyword(logData.getKeyword()) && logData.getSearchResult()==null) {//学历认证涉及到英文，所以再加上搜索为空作为条件
                commonDAO.del(logData);
                log.info(String.format("删除垃圾SEARCH_LOG{id:'%s',systemId:'%s',keyword:'%s',userIP:'%s',userID:'%s',createTime:'%s'}", logData.getId(), logData.getSystemId(), logData.getKeyword(), logData.getUserIP(), logData.getUserId(), TimeUtil.getTime(logData.getCreateTime(), "yyyyMMdd HH:mm:ss")));
                cnt++;
            }
        }
        log.info("删除的垃圾关键词数据量："+cnt);
    }

    @Override
    public void recordVisitLog() {
        SystemService systemService = ServiceFactory.getSystemService();
        KnowledgeService knowledgeService = ServiceFactory.getKnowledgeService();
        List<SystemData> list = systemService.getSystems();
        for(SystemData data:list) {
            String systemId = data.getId();
            List<KnowledgeData> list2 = knowledgeService.get(systemId, KnowledgeStatus.YSH);
            for(KnowledgeData knowlData:list2) {
                KnowledgeVisitLogData pojo = new KnowledgeVisitLogData(knowlData);
                commonDAO.save(pojo);
            }
        }
    }
    
    //把total均分为n份，无法均分时把余数依次累加到前面
    private List<Integer> splitCnt(int total, int n) {
        int every = total/n;
        int mod = total%n;
        List<Integer> result = new ArrayList<Integer>();
        for(int i=0;i<n;i++) {
            result.add(every);
        }
        for(int j=0;j<mod;j++) {
            int cnt = result.get(j);
            result.set(j, cnt+1);
        }
        return result;
    }

    @Override
    public LineChartVO getTopKnowlVisitLog(String systemId, int topCnt, String startTime, String endTime) {
        SystemOpenTimeData sotd = new SystemOpenTimeData();
        sotd.setSystemId(systemId);
        Calendar cal1 = TimeUtil.getCalendar(startTime);
        Calendar cal2 = TimeUtil.getCalendar(endTime);
        cal1.set(Calendar.HOUR_OF_DAY, -24);//因为要统计每日访问量，存的是总量，为了减出每日访问量要多统计一天
        cal2.set(Calendar.HOUR_OF_DAY, 24);
        List<CntVO> list = commonDAO.getTopVisitKnowl(sotd, cal1, cal2);
        List<String> legend = new ArrayList<String>();
        List<SeriesVO> series = new ArrayList<SeriesVO>();
        String startTimeOneDayAgo = TimeUtil.getTime(cal1, "yyyyMMdd");
        for(int i=0;i<topCnt&&i<list.size();i++) {
            CntVO cntVO = list.get(i);
            String knowlId = cntVO.getId();
            KnowledgeData knowledgeData = ManageCacheUtil.getKnowledgeDataById(knowlId);
            String title = knowledgeData.getArticle().getTitle();
            legend.add(title);
            List<BigDecimal> datas = commonDAO.totalVisitLog(knowlId, startTimeOneDayAgo, endTime);
            List<Long> datas2 = getDayVisit(datas);
            SeriesVO seriesVO = new SeriesVO(title, datas2);
            series.add(seriesVO);
        }
        List<String> xAxis = getDays(startTime, endTime);
        return new LineChartVO(legend, xAxis, series);
    }
    
    //获取两个日期间的所有日期
    private List<String> getDays(String startTime, String endTime) {
        List<String> result = new ArrayList<String>();
        String str = startTime;
        Calendar cal;
        while(!endTime.equals(str)) {
            result.add(str);
            cal = TimeUtil.getCalendar(str);
            cal.add(Calendar.DAY_OF_YEAR, 1);
            str = TimeUtil.getTime(cal, "yyyyMMdd");
        }
        result.add(endTime);
        return result;
    }
    
    private List<Long> getDayVisit(List<BigDecimal> list) {
        List<Long> result = new ArrayList<Long>();
        for(int i=1;i<list.size();i++) {
            result.add(list.get(i).longValue()-list.get(i-1).longValue());
        }
        return result;
    }
}
