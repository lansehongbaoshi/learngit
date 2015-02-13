package com.chsi.knowledge.action.knowledge;

import com.chsi.framework.page.Page;
import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.BasicAction;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.vo.KnowledgeVO;

public class KnowledgeAction  extends BasicAction{

    private static final long serialVersionUID = 1L;
    private KnowledgeService knowledgeService;
    private String id;
    private String systemId;
    private String tagName;
    private int start;
    private Page<KnowledgeVO> page;
    private KnowledgeVO knowledgeVO;

    //点击上一页下一页时 systemID会显示在这里，可以考虑systemId 从单点登录里取出，不从前台传入
     //systemId是否从数据库中读 一次？？？？还是写个静态变量，因为系统相对是比较固定的，更改很少。
    public String getKnowledgeList() throws Exception{
          page=knowledgeService.getKnowledgeVOPage(systemId, tagName, KnowledgeStatus.WSH, start, Constants.PAGE_SIZE);
          return SUCCESS;
    }
    
    public String getKnowledge() throws Exception{
        knowledgeVO = knowledgeService.getKnowledgeVOById(id);
        if (null == knowledgeVO) {
            return "global.error";
        }
        knowledgeService.updateVisitCntPlusOne(id);
        return SUCCESS;
    }
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public KnowledgeVO getKnowledgeVO() {
        return knowledgeVO;
    }

    public void setKnowledgeVO(KnowledgeVO knowledgeVO) {
        this.knowledgeVO = knowledgeVO;
    }

    public Page<KnowledgeVO> getPage() {
        return page;
    }

    public void setPage(Page<KnowledgeVO> page) {
        this.page = page;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public KnowledgeService getKnowledgeService() {
        return knowledgeService;
    }

    public void setKnowledgeService(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }
    
}
