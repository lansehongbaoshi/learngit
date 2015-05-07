package com.chsi.knowledge.action.search;

import java.util.List;

import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.index.service.KnowIndexService;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.SystemService;

/**
 * 搜索action
 * 
 * @author chenjian
 * 
 */
public class SearchAction extends AjaxAction {

    private static final long serialVersionUID = 1L;
    private KnowIndexService knowIndexService;
    private KnowledgeService knowledgeService;
    private SystemService systemService;
    private String keywords;
    private String systemId;
    private int curPage;

    public void searchKnow() throws Exception {
        if (ValidatorUtil.isNull(keywords)) {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        } else if (null == systemService.getSystemById(systemId)) {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        } else {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
            ajaxMessage.setO(knowIndexService.searchKnow(keywords, systemId, (curPage - 1) * Constants.SEARCH_PAGE_SIZE, Constants.SEARCH_PAGE_SIZE));
        }
        writeJSON(ajaxMessage);
    }
    
    public void refreshIndex() throws Exception{
        
        List<KnowledgeData> list=knowledgeService.get();
        if(null!=list)
        {
            for(KnowledgeData temp: list)
            {
                knowIndexService.updateKnowIndex(temp.getId());
            }
        }
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
    

}
