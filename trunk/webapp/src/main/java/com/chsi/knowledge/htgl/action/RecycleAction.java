package com.chsi.knowledge.htgl.action;

import java.util.List;

import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.index.service.KnowIndexService;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.service.KnowledgeService;

public class RecycleAction extends AjaxAction {

    /**
     * 
     */
    private static final long serialVersionUID = -6758642416847428087L;
    
    private KnowledgeService knowledgeService;
    private KnowIndexService knowIndexService;
    
    private String systemId;
    private String klId;
    private List<KnowledgeData> knowls;

    public void list() throws Exception {
        knowls = knowledgeService.get(systemId, KnowledgeStatus.YSC);
        ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
        ajaxMessage.setO(knowls);
        writeJSON(ajaxMessage);
    }
    
    public void rollback() throws Exception {
        KnowledgeData knowledgeData = knowledgeService.getKnowledgeById(klId);
        if(knowledgeData==null) {
            ajaxMessage.addMessage("不存在此知识点！");
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        } else {
            knowledgeData.setKnowledgeStatus(KnowledgeStatus.YSH);
            knowledgeService.update(knowledgeData);
            knowIndexService.updateKnowIndex(klId);
            ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
        }
        writeJSON(ajaxMessage);
    }
    
    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getKlId() {
        return klId;
    }

    public void setKlId(String klId) {
        this.klId = klId;
    }

    public List<KnowledgeData> getKnowls() {
        return knowls;
    }

    public void setKnowls(List<KnowledgeData> knowls) {
        this.knowls = knowls;
    }

    public KnowledgeService getKnowledgeService() {
        return knowledgeService;
    }

    public void setKnowledgeService(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    public KnowIndexService getKnowIndexService() {
        return knowIndexService;
    }

    public void setKnowIndexService(KnowIndexService knowIndexService) {
        this.knowIndexService = knowIndexService;
    }
}
