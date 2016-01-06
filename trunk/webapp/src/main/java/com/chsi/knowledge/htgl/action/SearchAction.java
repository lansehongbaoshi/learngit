package com.chsi.knowledge.htgl.action;

import java.util.Calendar;
import java.util.List;

import com.chsi.framework.callcontrol.CallInfoHelper;
import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.index.service.KnowIndexService;
import com.chsi.knowledge.pojo.SearchLogData;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.QueueService;
import com.chsi.knowledge.service.SearchService;
import com.chsi.knowledge.service.ServiceFactory;
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.vo.KnowListVO;
import com.chsi.knowledge.vo.SearchVO;
import com.chsi.knowledge.web.util.SearchUtil;
import com.chsi.search.client.vo.KnowledgeVO;

/**
 * 搜索action
 * 
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

    // 指定系统内搜索,不过滤关键字（如：*:*）
    public void searchAllKnow() throws Exception {
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
        int i = 0;
        for (SearchVO vo : list) {
            i++;
            if (i > 10)
                break;// 最多存储10个搜索结果id
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
