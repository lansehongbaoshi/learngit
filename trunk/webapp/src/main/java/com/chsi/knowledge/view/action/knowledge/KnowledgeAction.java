package com.chsi.knowledge.view.action.knowledge;

import com.chsi.framework.page.Page;
import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.vo.KnowledgeVO;

public class KnowledgeAction extends AjaxAction{

    private static final long serialVersionUID = 1L;
    private KnowledgeService knowledgeService;
    private SystemService systemService;
    private String id;
    private String systemId;
    private String tagId;
    private int currentNum;   
    private Page<KnowledgeVO> page;
    private KnowledgeVO knowledgeVO;
    private String callback;

    public void getKnowledgeList() throws Exception{
        SystemData systemData = systemService.getSystemById(systemId);
        if (null == systemData) {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        } else {
            page = knowledgeService.getKnowledgeVOPage(systemId, tagId, KnowledgeStatus.WSH, (currentNum - 1) * Constants.PAGE_SIZE, Constants.PAGE_SIZE);
            ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
            ajaxMessage.setO(page);
        }
        writeCallbackJSON(ajaxMessage, callback);
    }
    
    public void getKnowledge() throws Exception{
        knowledgeVO = knowledgeService.getKnowledgeVOById(id);
        if (null == knowledgeVO)  {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        }else{
            knowledgeService.updateVisitCntPlusOne(id);
            ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
            ajaxMessage.setO(knowledgeVO);
        }
        writeCallbackJSON(ajaxMessage, callback);
    }
    
    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public KnowledgeService getKnowledgeService() {
        return knowledgeService;
    }

    public void setKnowledgeService(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public int getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(int currentNum) {
        this.currentNum = currentNum;
    }

    public Page<KnowledgeVO> getPage() {
        return page;
    }

    public void setPage(Page<KnowledgeVO> page) {
        this.page = page;
    }

    public KnowledgeVO getKnowledgeVO() {
        return knowledgeVO;
    }

    public void setKnowledgeVO(KnowledgeVO knowledgeVO) {
        this.knowledgeVO = knowledgeVO;
    }

    public SystemService getSystemService() {
        return systemService;
    }

    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }
    
    
}
