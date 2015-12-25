package com.chsi.knowledge.action.search;

import java.util.ArrayList;
import java.util.List;

import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.index.service.KnowIndexService;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.SystemService;
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
    private String keywords;
    private String systemId;
    private int curPage;
    private String callback;

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
            KnowListVO<SearchVO> result = new KnowListVO<SearchVO>(list, listVO.getPagination());
            ajaxMessage.setO(result);
        }
        writeCallbackJSON(callback);
        return NONE;
    }
    
    //搜索关键字热度排名前几个
    public String topKeywords() throws Exception {
        List<String> strs = new ArrayList<String>();
        strs.add("账号注册");
        strs.add("密码找回");
        strs.add("研招报名");
        strs.add("学信档案");
        writePlainJSON(strs);
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
    

}
