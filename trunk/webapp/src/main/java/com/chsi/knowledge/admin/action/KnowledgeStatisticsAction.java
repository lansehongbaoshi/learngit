package com.chsi.knowledge.admin.action;

import java.io.IOException;
import java.util.List;

import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.service.DiscussService;
import com.chsi.knowledge.service.KnowTagRelationService;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.vo.DiscussCountVO;
import com.chsi.knowledge.vo.SystemStatisticsVO;
import com.chsi.knowledge.web.util.AjaxMessage;

public class KnowledgeStatisticsAction extends AjaxAction {

    /**
     * 只是统计ACTION
     * 
     * @author shikf
     */
    private static final long serialVersionUID = 5692015578456191376L;
    private KnowledgeService knowledgeService;
    private SystemService systemService;
    private KnowTagRelationService knowTagRelationService;
    private DiscussService discussService;
    private String systemId;
    private String systemName;
    private String seriesName;
    private String callback;
    
    public void getKnowledgeStatistics() throws Exception{
        List<SystemStatisticsVO> result = discussService.getSystemStatistics();
        ajaxMessage.setFlag(SUCCESS);
        ajaxMessage.setO(result);
        writeCallbackJSON(callback);
        
    }
    
    public void getBadKnowledgeRank() throws Exception{
        
        List<DiscussCountVO> result = discussService.getBadKnowledgeRank();
        ajaxMessage.setFlag(SUCCESS);
        ajaxMessage.setO(result);
        writeCallbackJSON(callback);
        
    }
    
    public void getGoodKnowledgeRank() throws Exception{
        
        List<DiscussCountVO> result = discussService.getGoodKnowledgeRank();
        ajaxMessage.setFlag(SUCCESS);
        ajaxMessage.setO(result);
        writeCallbackJSON(callback);
        
    }
    
    public void getKnowledgeInSystemTopGood() throws Exception{
        
        SystemData systemData = systemService.getSystemByName(systemName);
        if(systemData!=null){
            List<DiscussCountVO> result = discussService.getKnowledgeInSystemTopGood(systemData.getId());
            ajaxMessage.setFlag(SUCCESS);
            ajaxMessage.setO(result);
            writeCallbackJSON(callback);
        }
        
    }
    public void getKnowledgeInSystemTopBad() throws Exception{
        SystemData systemData = systemService.getSystemByName(systemName);
        if(systemData!=null){
            List<DiscussCountVO> result = discussService.getKnowledgeInSystemTopBad(systemData.getId());
            ajaxMessage.setFlag(SUCCESS);
            ajaxMessage.setO(result);
            writeCallbackJSON(callback);
        }
    }

    public KnowledgeService getKnowledgeService() {
        return knowledgeService;
    }

    public void setKnowledgeService(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    public SystemService getSystemService() {
        return systemService;
    }

    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    public KnowTagRelationService getKnowTagRelationService() {
        return knowTagRelationService;
    }

    public void setKnowTagRelationService(
            KnowTagRelationService knowTagRelationService) {
        this.knowTagRelationService = knowTagRelationService;
    }

    public DiscussService getDiscussService() {
        return discussService;
    }

    public void setDiscussService(DiscussService discussService) {
        this.discussService = discussService;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }
}
