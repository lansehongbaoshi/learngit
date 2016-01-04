package com.chsi.knowledge.action.search;

import java.util.Calendar;
import java.util.List;

import com.chsi.framework.callcontrol.CallInfoHelper;
import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.index.service.KnowIndexService;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.SearchLogData;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.QueueService;
import com.chsi.knowledge.service.SearchService;
import com.chsi.knowledge.service.ServiceFactory;
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.util.ManageCacheUtil;
import com.chsi.knowledge.vo.KnowListVO;
import com.chsi.knowledge.vo.SearchVO;
import com.chsi.knowledge.web.util.SearchUtil;
import com.chsi.search.client.vo.KnowledgeVO;

/**
 * 搜索action
 * @author chenjian
 */
public class SearchAction extends AjaxAction {

    private static final long serialVersionUID = 1L;
    private KnowIndexService knowIndexService;
    private KnowledgeService knowledgeService;
    private SystemService systemService;
    private SearchService searchService;
    private String keywords;
    private String systemId;
    private int curPage;
    private String callback;
    private QueueService queueService = ServiceFactory.getQueueService();

    //指定系统内搜索
    public void quickSearchKnow() throws Exception {
        keywords = SearchUtil.keywordsFilter(keywords);
        if (ValidatorUtil.isNull(keywords)) {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        } else if (null == systemService.getSystemById(systemId)) {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        } else {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
            KnowListVO<KnowledgeVO> listVO = knowIndexService.searchKnow(keywords, systemId, (curPage - 1) * Constants.SEARCH_PAGE_SIZE, Constants.SEARCH_PAGE_SIZE);
            ajaxMessage.setO(SearchUtil.exchangeResultList(listVO, keywords, 14));
        }
        writeCallbackJSON(callback);
    }
    
    //指定系统内搜索
    public void searchAllKnow() throws Exception {
        keywords = SearchUtil.keywordsFilter(keywords);
        if (ValidatorUtil.isNull(keywords)) {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        } else if (null == systemService.getSystemById(systemId)) {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        } else {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
            KnowListVO<KnowledgeVO> listVO = knowIndexService.searchKnow(keywords, systemId, (curPage - 1) * Constants.PAGE_SIZE, Constants.PAGE_SIZE);
            List<SearchVO> list = SearchUtil.exchangeResultList(listVO, keywords, 40);
            saveSearchLog(list);
            KnowListVO<SearchVO> result = new KnowListVO<SearchVO>(list, listVO.getPagination());
            ajaxMessage.setO(result);
        }
        writeCallbackJSON(callback);
    }
    
    //全系统搜索
    public String quickAll() throws Exception {
        keywords = SearchUtil.keywordsFilter(keywords);
        if (ValidatorUtil.isNull(keywords)) {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        } else {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
            KnowListVO<KnowledgeVO> listVO = knowIndexService.searchKnow(keywords, (curPage - 1) * Constants.SEARCH_PAGE_SIZE, Constants.SEARCH_PAGE_SIZE);
            ajaxMessage.setO(SearchUtil.exchangeResultList(listVO, keywords, 14));
        }
        writeCallbackJSON(callback);
        return NONE;
    }
    
    //全系统搜索
    public String allSearch() throws Exception {
        keywords = SearchUtil.keywordsFilter(keywords);
        if (ValidatorUtil.isNull(keywords)) {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        } else {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
            KnowListVO<KnowledgeVO> listVO = knowIndexService.searchKnow(keywords, (curPage - 1) * Constants.PAGE_SIZE, Constants.PAGE_SIZE);
            List<SearchVO> list = SearchUtil.exchangeResultList(listVO, keywords, 40);
            saveSearchLog(list);
            KnowListVO<SearchVO> result = new KnowListVO<SearchVO>(list, listVO.getPagination());
            ajaxMessage.setO(result);
        }
        writeCallbackJSON(callback);
        return NONE;
    }
    
    //搜索关键字热度排名前几个
    public String topKeywords() throws Exception {
        List<String> strs = ManageCacheUtil.getTopKeywords();
        ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
        ajaxMessage.setO(strs);
        writeJSON(ajaxMessage);
        return NONE;
    }
    
    //访问最热的n个知识点
    public String topKnowl() throws Exception {
        List<KnowledgeData> result = ManageCacheUtil.getTopKnowl();
        ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
        ajaxMessage.setO(result);
        writeJSON(ajaxMessage);
        return NONE;
    }

    public KnowIndexService getKnowIndexService() {
        return knowIndexService;
    }

    public void setKnowIndexService(KnowIndexService knowIndexService) {
        this.knowIndexService = knowIndexService;
    }

    public SystemService getSystemService() {
        return systemService;
    }

    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    public SearchService getSearchService() {
        return searchService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public KnowledgeService getKnowledgeService() {
        return knowledgeService;
    }

    public void setKnowledgeService(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }


    public String getCallback() {
        return callback;
    }


    public void setCallback(String callback) {
        this.callback = callback;
    }
    
    private void saveSearchLog(List<SearchVO> list) {
        SearchLogData data = new SearchLogData();
        data.setKeyword(this.keywords);
        data.setSystemId(this.systemId);
        StringBuffer sb = new StringBuffer();
        int i=0;
        for(SearchVO vo : list) {
            i++;
            if(i>10) break;//最多存储10个搜索结果id
            sb.append(vo.getKnowId());
            sb.append(",");
        }
        data.setSearchResult(sb.toString());
        data.setCreateTime(Calendar.getInstance());
        data.setUserId(CallInfoHelper.getCurrentUser());
        data.setUserIP(CallInfoHelper.getCurrentUserIp());
        queueService.addSearchLog(data);
    }
}
