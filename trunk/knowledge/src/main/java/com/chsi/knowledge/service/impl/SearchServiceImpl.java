package com.chsi.knowledge.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chsi.framework.page.Page;
import com.chsi.framework.service.BaseDbService;
import com.chsi.framework.util.TimeUtil;
import com.chsi.knowledge.ServiceConstants;
import com.chsi.knowledge.dao.SearchDAO;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.SearchLogData;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.SearchService;
import com.chsi.knowledge.service.ServiceFactory;
import com.chsi.knowledge.util.SearchUtil;
import com.chsi.search.client.SearchServiceClient;
import com.chsi.search.client.SearchServiceClientFactory;
import com.chsi.search.client.vo.KnowledgeVO;

public class SearchServiceImpl extends BaseDbService implements SearchService {
    private SearchDAO searchDAO;

    protected void doCreate() {
        searchDAO = getDAO(ServiceConstants.SEARCH_DAO, SearchDAO.class);
    }

    protected void doRemove() {
        
    }

    public void save(SearchLogData po) {
        searchDAO.save(po);
    }

    public List<KnowledgeVO> getTopSearchKnow(int n) {
        List<String> keywords = searchDAO.getTopKeyword(n*4);//防止出现无效的关键词（即：没有搜索结果的关键词）
        List<KnowledgeVO> result = new ArrayList<KnowledgeVO>();
        for(String keyword:keywords) {
            if(!SearchUtil.isGoodKeyword(keyword)) continue;
            if(result.size()>=n) break;
            KnowledgeVO knowledgeVO = getFirstSearchResult(keyword);
            if(knowledgeVO!=null&&!result.contains(knowledgeVO)) {
                result.add(knowledgeVO);
            }
        }
        return result;
    }

    public List<KnowledgeData> getTopKnowl(int n) {
        List<String> knowIds = searchDAO.getTopVisitKnowl(n);
        KnowledgeService knowledgeService = ServiceFactory.getKnowledgeService();
        List<KnowledgeData> result = new ArrayList<KnowledgeData>();
        for(String id:knowIds) {
            KnowledgeData data = knowledgeService.getKnowledgeWithArticleById(id);
            if(data!=null) {
                result.add(data);
            }
        }
        return result;
    }
    
    @Override
    public void removeDuplicatedDatas() {
        List<Object[]> list = searchDAO.getDuplicatedDatas();
        log.info("冗余关键词总量："+list.size());
        int cnt = 0;
        for(Object[] objs:list) {
            String systemId = (String)objs[0];
            String keyword = (String)objs[1];
            String userIP = (String)objs[2];
            log.info(String.format("冗余关键词{systemId:'%s',keyword:'%s',userIP:'%s'}", systemId, keyword, userIP));
            List<SearchLogData> logs = searchDAO.getTheDuplicatedData(systemId, keyword, userIP);
            for(int i=0;i<logs.size()-1;i++) {
                SearchLogData logData = logs.get(i);
                searchDAO.del(logData);
                log.info(String.format("删除冗余SEARCH_LOG{id:'%s',systemId:'%s',keyword:'%s',userIP:'%s',userID:'%s',createTime:'%s'}", logData.getId(), systemId, keyword, userIP, logData.getUserId(), TimeUtil.getTime(logData.getCreateTime(), "yyyyMMdd HH:mm:ss")));
                cnt++;
            }
        }
        log.info("删除的冗余数据量："+cnt);
    }
    
    @Override
    public void removeTrashKeywords() {
        List<SearchLogData> logs = searchDAO.getSearchLogData(null, null);
        int cnt = 0;
        for(SearchLogData logData:logs) {
            if(!SearchUtil.isGoodKeyword(logData.getKeyword())) {
                searchDAO.del(logData);
                log.info(String.format("删除垃圾SEARCH_LOG{id:'%s',systemId:'%s',keyword:'%s',userIP:'%s',userID:'%s',createTime:'%s'}", logData.getId(), logData.getSystemId(), logData.getKeyword(), logData.getUserIP(), logData.getUserId(), TimeUtil.getTime(logData.getCreateTime(), "yyyyMMdd HH:mm:ss")));
                cnt++;
            }
        }
        log.info("删除的垃圾关键词数据量："+cnt);
    }
    
    //返回排名第一的搜索结果
    private KnowledgeVO getFirstSearchResult(String keyword) {
        SearchServiceClient searchClient = SearchServiceClientFactory.getSearchServiceClient();
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("q", keyword);
        queryParams.put("qf", "title");
        queryParams.put("defType", "edismax");
        Page<KnowledgeVO> page = searchClient.searchKnow(queryParams, 0, 1);
        List<KnowledgeVO> list = page.getList();
        if(list!=null&&list.size()>0) {
            return list.get(0);
        } else {
            return null;
        }
    }
    
}
