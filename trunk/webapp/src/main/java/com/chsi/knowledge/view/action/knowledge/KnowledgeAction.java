package com.chsi.knowledge.view.action.knowledge;

import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.vo.KnowListPageVO;
import com.chsi.knowledge.vo.KnowPageVO;
import com.chsi.knowledge.web.util.WebAppUtil;
/**
 * 用户获取知识ACTION
 * @author chenjian
 */
public class KnowledgeAction extends AjaxAction{

    private static final long serialVersionUID = 1L;
    private KnowledgeService knowledgeService;
    private SystemService systemService;
    private String id;
    private String systemId;
    private String tagId;
    private int curPage;   
    private KnowListPageVO knowledgePageVO;
    private KnowPageVO knowPageVO;
    private String callback;

    public void getKnowledgeList() throws Exception{
        System.out.print(WebAppUtil.getUserId());
        SystemData systemData = systemService.getSystemById(systemId);
        if (null == systemData) {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        } else {
            knowledgePageVO = knowledgeService.getKnowledgeVOPage(systemId, tagId, KnowledgeStatus.WSH, (curPage - 1) * Constants.PAGE_SIZE, Constants.PAGE_SIZE);
            ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
            ajaxMessage.setO(knowledgePageVO);
        }
        writeCallbackJSON(ajaxMessage, callback);
    }
    
     public void getKnowledge() throws Exception{
         knowPageVO = knowledgeService.getKnowledgeVOById(id);
        if (null == knowPageVO)  {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        }else{
            knowledgeService.updateVisitCntPlusOne(id);
            ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
            ajaxMessage.setO(knowPageVO);
        }
        writeCallbackJSON(ajaxMessage, callback);
    } 
    
    public void setCallback(String callback) {
        if (ValidatorUtil.isNull(callback))
            this.callback = Constants.DEFAULT_CALLBACKNAME;
        else
            this.callback = callback;
    }
    
    public String getCallback() {
        return callback;
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

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public KnowListPageVO getKnowledgePageVO() {
        return knowledgePageVO;
    }

    public void setKnowledgePageVO(KnowListPageVO knowledgePageVO) {
        this.knowledgePageVO = knowledgePageVO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public KnowPageVO getKnowPageVO() {
        return knowPageVO;
    }

    public void setKnowPageVO(KnowPageVO knowPageVO) {
        this.knowPageVO = knowPageVO;
    }

    
}
