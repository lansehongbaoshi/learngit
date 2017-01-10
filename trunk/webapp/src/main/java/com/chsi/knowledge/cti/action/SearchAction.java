package com.chsi.knowledge.cti.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.index.service.KnowIndexService;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.QueueService;
import com.chsi.knowledge.service.ServiceFactory;
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.util.ManageCacheUtil;
import com.chsi.knowledge.util.SearchUtil;
import com.chsi.knowledge.vo.KnowListVO;
import com.chsi.knowledge.vo.SearchVO;
import com.chsi.search.client.util.SolrQueryUtil;
import com.chsi.search.client.vo.KnowledgeVO;

/**
 * 客服相关系统（比如邮件系统）调用的jsonp api
 * 
 * @author zhangzh
 */
public class SearchAction extends AjaxAction {

    /**
     * 
     */
    private static final long serialVersionUID = -7506196822824287063L;

    private KnowIndexService knowIndexService;
    private KnowledgeService knowledgeService;
    private SystemService systemService;
    private String keywords;
    private String systemId;
    private String[] ids;
    private String id;
    private int curPage;
    private String callback;
    private String type;
    private QueueService queueService = ServiceFactory.getQueueService();

    // 指定系统内,关键字自动完成
    /*
     * public void quickSearchKnow() throws Exception { keywords =
     * SearchUtil.keywordsFilter(keywords); if (ValidatorUtil.isNull(keywords))
     * { ajaxMessage.setO(new ArrayList<SearchVO>()); } else if (null ==
     * systemService.getSystemById(systemId)) { ajaxMessage.setO(new
     * ArrayList<SearchVO>()); } else { KnowListVO<KnowledgeVO> listVO =
     * knowIndexService.searchKnow(keywords, systemId, (curPage - 1) *
     * Constants.SEARCH_PAGE_SIZE, Constants.SEARCH_PAGE_SIZE); List<SearchVO>
     * list = SearchUtil.exchangeResultList(listVO, keywords, 14); //
     * saveSearchLog(list); ajaxMessage.setO(list); }
     * ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
     * writeCallbackJSON(callback); }
     */

    // 指定系统内搜索，支持多系统
    public void searchKnow() throws Exception {
        try {
            Map<String, String> queryParams = new HashMap<String, String>();
            if (ValidatorUtil.isNull(keywords)) {
                queryParams.put("q", "*:*");
            } else {
                queryParams.put("q", keywords);
            }
            if (ids != null && ids.length > 0) {
                queryParams.put("fq", SolrQueryUtil.generateFilterOrQuery("system_ids", ids)+"AND type:"+type);
            }else{
                queryParams.put("fq", "type:"+type);
            }
            queryParams.put("bf", "ord(cti_visit_cnt)^0.1");
            KnowListVO<KnowledgeVO> listVO = knowIndexService.customSearch(queryParams, (curPage - 1) * Constants.PAGE_SIZE, Constants.PAGE_SIZE);
            List<SearchVO> list = SearchUtil.exchangeResultList(listVO, keywords, 15);
            KnowListVO<SearchVO> result = new KnowListVO<SearchVO>(list, listVO.getPagination());
            ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
            ajaxMessage.setO(result);
        } catch (Exception ex) {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        }
        writeCallbackJSON(callback);
    }

    // 全系统搜索标题（自动完成处用,如机器人）
    /*
     * public String autoTitle() throws Exception { keywords =
     * SearchUtil.keywordsFilter(keywords); Map<String, String> queryParams =
     * new HashMap<String, String>(); queryParams.put("q", keywords);
     * queryParams.put("qf", "title"); queryParams.put("fq", "type:PUBLIC");
     * queryParams.put("fl", "title,id"); KnowListVO<KnowledgeVO> listVO =
     * knowIndexService.customSearch(queryParams, (curPage - 1) *
     * Constants.SEARCH_PAGE_SIZE, Constants.SEARCH_PAGE_SIZE); List<SearchVO>
     * list = SearchUtil.exchangeResultList(listVO, keywords, 14); //
     * saveSearchLog(list); KnowListVO<SearchVO> result = new
     * KnowListVO<SearchVO>(list, listVO.getPagination());
     * ajaxMessage.setO(result);
     * ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
     * writeCallbackJSON(callback); return NONE; }
     */

    // 知识详情
    public void detailKnow() throws Exception {
        if (ValidatorUtil.isNull(id)) {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
            ajaxMessage.addMessage("id为空");
        } else {
            KnowledgeData data = ManageCacheUtil.getKnowledgeDataById(id);
            if (data == null) {
                ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
                ajaxMessage.addMessage("未查到知识");
            } else {
                KnowledgeVO vo = new KnowledgeVO();
                vo.setKnowledgeId(data.getId());
                vo.setTitle(data.getArticle().getTitle());
                vo.setContent(data.getArticle().getContent());
                vo.setUpdaterName(data.getUpdaterName());
                vo.setHasImage(SearchUtil.hasImgTag(data.getArticle().getContent()));
                ajaxMessage.setO(vo);
                ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
                queueService.addCtiVisitKnowledgeId(id);
            }
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

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
