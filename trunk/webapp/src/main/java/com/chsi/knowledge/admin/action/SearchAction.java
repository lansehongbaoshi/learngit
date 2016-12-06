package com.chsi.knowledge.admin.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chsi.cms.client.CmsServiceClient;
import com.chsi.cms.client.CmsServiceClientFactory;
import com.chsi.framework.page.Page;
import com.chsi.framework.page.PageUtil;
import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.index.service.KnowIndexService;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.TagData;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.util.Pagination;
import com.chsi.knowledge.util.SearchUtil;
import com.chsi.knowledge.vo.KnowListVO;
import com.chsi.knowledge.vo.LoginUserVO;
import com.chsi.knowledge.vo.SearchVO;
import com.chsi.news.vo.Article;
import com.chsi.search.client.vo.KnowledgeVO;

/**
 * 后台知识管理和查询用的搜索action
 * 
 * @author chenjian
 */
public class SearchAction extends AjaxAction {

    private static final long serialVersionUID = 1L;
    private KnowIndexService knowIndexService;
    private KnowledgeService knowledgeService;
    private SystemService systemService;
    private String keywords;
    private String systemId;
    private String tag;
    private int curPage;
    private String callback;
    private String type;

    // private QueueService queueService = ServiceFactory.getQueueService();

    // 指定系统内搜索,不过滤关键字（如：*:*）
    public void searchAllKnow() throws Exception {
        ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
        Map<String, String> queryParams = new HashMap<String, String>();
        if (ValidatorUtil.isNull(keywords)) {
            keywords = "*:*";
        }
        queryParams.put("q", keywords);
        List<String> fqs = new ArrayList<String>();
        if (!ValidatorUtil.isNull(systemId)) {
            fqs.add("system_ids:" + systemId);
        }
        if (!ValidatorUtil.isNull(tag)) {
            fqs.add("tag_ids:" + tag);
        }
        if (!ValidatorUtil.isNull(type)) {
            fqs.add(String.format("type:%s", type));
        }
        if (fqs.size() > 0) {
            String fq = org.apache.commons.lang3.StringUtils.join(fqs, " AND ");
            queryParams.put("fq", fq);
        }
        /*
         * if("*:*".equals(keywords)) { queryParams.put("sort",
         * "visit_cnt desc"); }
         */

        KnowListVO<KnowledgeVO> listVO = knowIndexService.searchKnow(queryParams, (curPage - 1) * Constants.PAGE_SIZE, Constants.PAGE_SIZE);

        List<SearchVO> list = SearchUtil.exchangeResultList(listVO, keywords, 40);
        // saveSearchLog(list);

        KnowListVO<SearchVO> result = new KnowListVO<SearchVO>(list, listVO.getPagination());
        ajaxMessage.setO(result);
        writeCallbackJSON(callback);
    }

    public void searchDSHKnow() throws Exception {
        int start = (curPage - 1) * Constants.PAGE_SIZE < 0 ? 0 : (curPage - 1) * Constants.PAGE_SIZE;
        int size = Constants.PAGE_SIZE;
        ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
        List<KnowledgeData> listKnows = knowledgeService.getKnowledgeByStatus(systemId, tag, KnowledgeStatus.DSH, type, start, size);
        long count = knowledgeService.getKnowledgeCount(systemId, tag, KnowledgeStatus.DSH, type, "");
        CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
        List<KnowledgeVO> knowList = new ArrayList<KnowledgeVO>();
        for (KnowledgeData know : listKnows) {
            Article article = cmsServiceClient.getArticle(know.getCmsId());
            List<TagData> tags = knowledgeService.getTagDatasByKnowId(know);
            KnowledgeVO vo = new KnowledgeVO();

            StringBuffer str = new StringBuffer();
            List<String> tagIds = new ArrayList<String>();
            List<String> systemIds = new ArrayList<String>();
            for (TagData tag : tags) {
                str.append(tag.getName() + " ");
                tagIds.add(tag.getId());
                if (!systemIds.contains(tag.getSystemData().getId()))
                    systemIds.add(tag.getSystemData().getId());
            }

            vo.setSystemIds(systemIds);
            vo.setKnowledgeId(know.getId());
            vo.setTitle(article.getTitle());
            vo.setContent(article.getContent());
            vo.setKeywords(know.getKeywords());
            vo.setTagIds(tagIds);
            vo.setSort(know.getSort());
            vo.setType(know.getType());
            vo.setTags(str.toString());
            knowList.add(vo);
        }

        Page<KnowledgeVO> page = PageUtil.getPage(knowList.iterator(), start, size, count);
        Pagination pagination = new Pagination(page.getTotalCount(), page.getPageCount(), page.getCurPage());
        KnowListVO<KnowledgeVO> listVO = new KnowListVO<KnowledgeVO>(page.getList(), pagination);

        List<SearchVO> list = SearchUtil.exchangeResultList(listVO, keywords, 40);
        // saveSearchLog(list);

        KnowListVO<SearchVO> result = new KnowListVO<SearchVO>(list, listVO.getPagination());
        ajaxMessage.setO(result);
        writeCallbackJSON(callback);
    }

    public void searchSelfDSHKnow() throws Exception {
        int start = (curPage - 1) * Constants.PAGE_SIZE < 0 ? 0 : (curPage - 1) * Constants.PAGE_SIZE;
        int size = Constants.PAGE_SIZE;
        ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
        LoginUserVO user = getLoginUserVO();
        String userId = user.getAcc().getId();

        List<KnowledgeData> listKnows = knowledgeService.getKnowledgeByStatusAndUserId(systemId, tag, KnowledgeStatus.DSH, type, userId, start, size);
        long count = knowledgeService.getKnowledgeCount(systemId, tag, KnowledgeStatus.DSH, type, userId);
        CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
        List<KnowledgeVO> knowList = new ArrayList<KnowledgeVO>();
        for (KnowledgeData know : listKnows) {
            Article article = cmsServiceClient.getArticle(know.getCmsId());
            List<TagData> tags = knowledgeService.getTagDatasByKnowId(know);
            KnowledgeVO vo = new KnowledgeVO();

            StringBuffer str = new StringBuffer();
            List<String> tagIds = new ArrayList<String>();
            List<String> systemIds = new ArrayList<String>();
            for (TagData tag : tags) {
                str.append(tag.getName() + " ");
                tagIds.add(tag.getId());
                if (!systemIds.contains(tag.getSystemData().getId()))
                    systemIds.add(tag.getSystemData().getId());
            }

            vo.setSystemIds(systemIds);
            vo.setKnowledgeId(know.getId());
            vo.setTitle(article.getTitle());
            vo.setContent(article.getContent());
            vo.setKeywords(know.getKeywords());
            vo.setTagIds(tagIds);
            vo.setSort(know.getSort());
            vo.setType(know.getType());
            vo.setTags(str.toString());
            knowList.add(vo);
        }

        Page<KnowledgeVO> page = PageUtil.getPage(knowList.iterator(), start, size, count);
        Pagination pagination = new Pagination(page.getTotalCount(), page.getPageCount(), page.getCurPage());
        KnowListVO<KnowledgeVO> listVO = new KnowListVO<KnowledgeVO>(page.getList(), pagination);

        List<SearchVO> list = SearchUtil.exchangeResultList(listVO, keywords, 40);
        // saveSearchLog(list);
        for (SearchVO knowVO : list) {
            if ("PUBLIC".equals(knowVO.getType())) {
                knowVO.setType("公开");
            } else if ("PRIVATE".equals(knowVO.getType())) {
                knowVO.setType("内部");
            } else {
                knowVO.setType("其他");
            }
        }

        KnowListVO<SearchVO> result = new KnowListVO<SearchVO>(list, listVO.getPagination());
        ajaxMessage.setO(result);
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /*
     * private void saveSearchLog(List<SearchVO> list) { SearchLogData data =
     * new SearchLogData(); data.setKeyword(this.keywords);
     * data.setSystemId(this.systemId); StringBuffer sb = new StringBuffer();
     * int i = 0; for (SearchVO vo : list) { i++; if (i > 10) break;//
     * 最多存储10个搜索结果id sb.append(vo.getKnowId()); sb.append(","); }
     * data.setSearchResult(sb.toString());
     * data.setCreateTime(Calendar.getInstance());
     * data.setUserId(CallInfoHelper.getCurrentUser());
     * data.setUserIP(CallInfoHelper.getCurrentUserIp());
     * queueService.addSearchLog(data); }
     */
}
