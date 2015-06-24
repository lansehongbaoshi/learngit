package com.chsi.knowledge.view.action;

import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.QueueService;
import com.chsi.knowledge.service.ServiceFactory;
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.service.TagService;
import com.chsi.knowledge.vo.ViewKnowVO;
import com.chsi.knowledge.vo.ViewKnowsVO;
import com.opensymphony.xwork2.ActionContext;
/**
 * 前台获取知识ACTION
 * @author chenjian
 */
public class KnowledgeAction extends AjaxAction{

    private static final long serialVersionUID = 1L;
    private KnowledgeService knowledgeService;
    private TagService tagService;
    private QueueService queueService = ServiceFactory.getQueueService();
    private SystemService systemService;
    private String id;
    private String systemId;
    private String tagId;
    private int curPage;   
    private String callback;

    public void getKnowledgeList() throws Exception{
        SystemData systemData = systemService.getSystemById(systemId);
        if (null == systemData) {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        } else {
            if (tagId != null && tagId.equals("normal")) {
                tagId = tagService.getTagData(systemId, "常见问题").getId();
            }
            ViewKnowsVO viewKnowsVO = knowledgeService.getViewKnowsVO(systemData, tagId, (curPage - 1) * Constants.PAGE_SIZE, Constants.PAGE_SIZE);
            ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
            ajaxMessage.setO(viewKnowsVO);
        }
        writeCallbackJSON(callback);
    }
    
    public void getKnowledge() throws Exception{
        ViewKnowVO viewKnowVO = knowledgeService.getKnowVOById(id, tagId);
        if (null == viewKnowVO)  {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        }else{
            if (null != session.get(Constants.DISCUSS + id)){
                viewKnowVO.getConKnow().setIfDiscussed(true);
            }
            //如果没访问过，向访问知识队列中插入ID
            if (null == session.get(Constants.VISIT + id)) {
                session.put(Constants.VISIT + id, id);
                queueService.addVisitKnowledgeId(id);
            }
            ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
            ajaxMessage.setO(viewKnowVO);
        }
        writeCallbackJSON(callback);
    }
    
    /**
     * 根据搜索结果访问知识
     * @return
     * @throws Exception
     */
    public String getKnowForSearch() throws Exception {
        ActionContext actionCon = ActionContext.getContext();
        ViewKnowVO viewKnowVO = knowledgeService.getKnowVOById(id, tagId);
        if (null == viewKnowVO) {
            return ERROR;
        }
        if (null != session.get(Constants.DISCUSS + id)) {
            viewKnowVO.getConKnow().setIfDiscussed(true);
        }
        // 如果没访问过，向访问知识队列中插入ID
        if (null == session.get(Constants.VISIT + id)) {
            session.put(Constants.VISIT + id, id);
            queueService.addVisitKnowledgeId(id);
        }
        actionCon.put("viewKnowVO", viewKnowVO);
        return SUCCESS;
    }
    
    public void setCallback(String callback) {
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

    public QueueService getQueueService() {
        return queueService;
    }

    public void setQueueService(QueueService queueService) {
        this.queueService = queueService;
    }

    public SystemService getSystemService() {
        return systemService;
    }

    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
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

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public TagService getTagService() {
        return tagService;
    }

    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }
     
    
}
