package com.chsi.knowledge.htgl.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.index.service.KnowIndexService;
import com.chsi.knowledge.index.service.LogOperService;
import com.chsi.knowledge.pojo.KnowTagRelationData;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.LogOperData;
import com.chsi.knowledge.pojo.TagData;
import com.chsi.knowledge.service.DiscussService;
import com.chsi.knowledge.service.KnowTagRelationService;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.service.TagService;
import com.chsi.knowledge.util.ManageCacheUtil;
import com.chsi.knowledge.vo.DiscussCountVO;
import com.chsi.knowledge.vo.DiscussInfoVO;
import com.chsi.knowledge.vo.KnowListVO;
import com.chsi.knowledge.vo.LogOperVO;
import com.chsi.knowledge.vo.LoginUserVO;
import com.chsi.knowledge.vo.ViewKnowVO;
import com.chsi.knowledge.web.util.AjaxMessage;

/**
 * 知识后台管理ACTION
 * 
 * @author zhangzh
 */
public class KnowledgeAction extends AjaxAction {
    private String id;
    private String tagId;
    private String keywords;
    private String[] tagIds;
    private String title;
    private String content;
    private String sort;
    private String type;
    private String systemId;
    private String keyId;

    private ViewKnowVO viewKnowVO;
    private KnowledgeData knowledgeData;
    private List<KnowTagRelationData> knowTagRelationList;
    
    private KnowledgeService knowledgeService;
    private KnowIndexService knowIndexService;
    private TagService tagService;
    private SystemService systemService;
    private KnowTagRelationService knowTagRelationService;
    private DiscussService discussService;
    private DiscussCountVO discussCountVO;
    private KnowListVO<DiscussInfoVO> contentList;
    private LogOperService logOperService;
    
    private int curPage;
    
    protected AjaxMessage ajaxMessage = new AjaxMessage();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public ViewKnowVO getViewKnowVO() {
        return viewKnowVO;
    }

    public void setViewKnowVO(ViewKnowVO viewKnowVO) {
        this.viewKnowVO = viewKnowVO;
    }

    public KnowledgeData getKnowledgeData() {
        return knowledgeData;
    }

    public void setKnowledgeData(KnowledgeData knowledgeData) {
        this.knowledgeData = knowledgeData;
    }

    public List<KnowTagRelationData> getKnowTagRelationList() {
        return knowTagRelationList;
    }

    public void setKnowTagRelationList(List<KnowTagRelationData> knowTagRelationList) {
        this.knowTagRelationList = knowTagRelationList;
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

    public TagService getTagService() {
        return tagService;
    }

    public void setTagService(TagService tagService) {
        this.tagService = tagService;
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

    public void setKnowTagRelationService(KnowTagRelationService knowTagRelationService) {
        this.knowTagRelationService = knowTagRelationService;
    }
    
    public DiscussService getDiscussService() {
        return discussService;
    }

    public void setDiscussService(DiscussService discussService) {
        this.discussService = discussService;
    }

    public DiscussCountVO getDiscussCountVO() {
        return discussCountVO;
    }

    public void setDiscussCountVO(DiscussCountVO discussCountVO) {
        this.discussCountVO = discussCountVO;
    }
    
    public KnowListVO<DiscussInfoVO> getContentList() {
        return contentList;
    }

    public void setContentList(KnowListVO<DiscussInfoVO> contentList) {
        this.contentList = contentList;
    }
    
    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }



    private static final long serialVersionUID = 464316546L;

    public String searchindex() throws Exception {
        return SUCCESS;
    }

    public String modifyindex() throws Exception {
        if (!ValidatorUtil.isNull(id)) {
            knowledgeData = knowledgeService.getKnowledgeWithArticleById(id);
            if (null != knowledgeData) {
                knowTagRelationList = knowTagRelationService.getKnowTagDatas(KnowledgeStatus.YSH, id);
                if (knowTagRelationList != null && knowTagRelationList.size() > 0) {
                    return SUCCESS;
                }
            }

        }
        request.put(Constants.REQUEST_ERROR, "参数不能为空");
        return ERROR;
    }
    public String modifycheck() throws Exception {
        if (!ValidatorUtil.isNull(id)) {
            knowledgeData = knowledgeService.getKnowledgeWithArticleById(id);
            if (null != knowledgeData) {
                knowTagRelationList = knowTagRelationService.getKnowTagDatas(KnowledgeStatus.DSH, id);
                if (knowTagRelationList != null && knowTagRelationList.size() > 0) {
                    return SUCCESS;
                }
            }else {
                request.put(Constants.REQUEST_ERROR, "该知识点已审核通过，或者该知识点为在待审核状态！");
                return ERROR;
            }

        }
        request.put(Constants.REQUEST_ERROR, "参数不能为空");
        return ERROR;
    }
    
    
    public String modifyadd() throws Exception {
        if (!ValidatorUtil.isNull(id)) {
            knowledgeData = knowledgeService.getKnowledgeWithArticleById(id);
            if (null != knowledgeData) {
                knowTagRelationList = knowTagRelationService.getKnowTagDatas(KnowledgeStatus.DSH, id);
                if (knowTagRelationList != null && knowTagRelationList.size() > 0) {
                    return SUCCESS;
                }
            }

        }
        request.put(Constants.REQUEST_ERROR, "参数不能为空");
        return ERROR;
    }

    public String getKnow() throws Exception {
        knowTagRelationList = knowTagRelationService.getKnowTagRelationByKnowId(id);
        knowledgeData = knowledgeService.getKnowledgeWithArticleById(id);
        String KId = knowledgeData.getId();
        discussCountVO = discussService.getDiscussCountVOByKId(KId);
        contentList = discussService.getDiscussInfoVOList(KId, 0, 10);
        
        return SUCCESS;
    }

    // 更新某个知识点，包括更新系统内的knowledge表、新闻系统里的知识点
    public String updateKnowledge() throws Exception {
        String error = "";
        if(ValidatorUtil.isNull(keywords)) {
            error = "请输入关键字";
        } else if(ValidatorUtil.isNull(title)) {
            error = "请输入标题";
        } else if(ValidatorUtil.isNull(content)) {
            error = "请输入回答";
        } else if(tagIds == null || tagIds.length == 0) {
            error = "请先到\"<a href='/htgl/tag/index.action'>便签管理</a>\"中增加标签";
        } else if(ValidatorUtil.isNull(sort)) {
            error = "请输入热点度";
        } else if(ValidatorUtil.isNull(type)) {
            error = "请设定知识类型";
        }
        if(!"".equals(error)) {
            request.put(Constants.REQUEST_ERROR, error);
            return ERROR;
        }
        LoginUserVO loginUserVO = getLoginUserVO();
        if (!ValidatorUtil.isNull(id) && !ValidatorUtil.isNull(title) && !ValidatorUtil.isNull(content) && !ValidatorUtil.isNull(sort) && tagIds != null && tagIds.length > 0 && !ValidatorUtil.isNull(keywords)&& !ValidatorUtil.isNull(type)) {
            KnowledgeData data = knowledgeService.getKnowledgeById(id);
            data.setKeywords(keywords);
            data.setSort(Integer.parseInt(sort));
            data.setUpdateTime(Calendar.getInstance());
            data.setUpdater(getLoginedUserId());
            data.setType(type);
            data.setKnowledgeStatus(KnowledgeStatus.YSH);
            knowledgeService.update(data, title, content, loginUserVO.getAcc().getId());
            
            knowTagRelationService.del(id);
            for (String one : tagIds) {
                TagData tagData = tagService.getTagData(one);
                if (tagData != null) {
                    KnowTagRelationData newKnowTagRelationData = new KnowTagRelationData();
                    newKnowTagRelationData.setKnowledgeData(data);
                    newKnowTagRelationData.setTagData(tagData);
                    knowTagRelationService.save(newKnowTagRelationData);
                    ManageCacheUtil.removeKnowTag(tagData.getId());
                }
            }
            knowIndexService.deleteKnowIndexBySolr(data.getId());
            knowIndexService.updateKnowIndex(data.getId());
            ManageCacheUtil.removeKnowledgeDataById(data.getId());
            LogOperData logOper = new LogOperData();
            logOper.setCreateTime(Calendar.getInstance());
            com.chsi.knowledge.vo.LoginUserVO user = com.chsi.knowledge.web.util.WebAppUtil.getLoginUserVO(httpRequest);
            logOper.setUserId(user.getAcc().getId());
            if(user.getAuths().contains(com.chsi.knowledge.Constants.ROLE_CTI_USER)) {
                logOper.setM1("知识新增");
            }
            if(user.getAuths().contains(com.chsi.knowledge.Constants.ROLE_KNOWLEDGE)) {
                logOper.setM1("知识管理");
            }
            
            logOper.setM2("");
            logOper.setOper("修改");
            logOper.setMessage("知识");
            logOper.setKeyId(id);
            logOperService.save(logOper);
            
            return SUCCESS;
        }
        request.put(Constants.REQUEST_ERROR, "参数不能为空");
        return ERROR;
    }
    
    public String updateSelfKnowledge() throws Exception {
        String error = "";
        if(ValidatorUtil.isNull(keywords)) {
            error = "请输入关键字";
        } else if(ValidatorUtil.isNull(title)) {
            error = "请输入标题";
        } else if(ValidatorUtil.isNull(content)) {
            error = "请输入回答";
        } else if(tagIds == null || tagIds.length == 0) {
            error = "请先到\"<a href='/htgl/tag/index.action'>便签管理</a>\"中增加标签";
        } else if(ValidatorUtil.isNull(sort)) {
            error = "请输入热点度";
        } else if(ValidatorUtil.isNull(type)) {
            error = "请设定知识类型";
        }
        if(!"".equals(error)) {
            request.put(Constants.REQUEST_ERROR, error);
            return ERROR;
        }
        LoginUserVO loginUserVO = getLoginUserVO();
        if (!ValidatorUtil.isNull(id) && !ValidatorUtil.isNull(title) && !ValidatorUtil.isNull(content) && !ValidatorUtil.isNull(sort) && tagIds != null && tagIds.length > 0 && !ValidatorUtil.isNull(keywords)&& !ValidatorUtil.isNull(type)) {
            KnowledgeData data = knowledgeService.getKnowledgeById(id);
            
            if(!loginUserVO.getAcc().getId().equals(data.getCreater())){
                request.put(Constants.REQUEST_ERROR, "没有权限对该知识进行操作");
                return ERROR;
            }
            
            data.setKeywords(keywords);
            data.setSort(Integer.parseInt(sort));
            data.setUpdateTime(Calendar.getInstance());
            data.setUpdater(getLoginedUserId());
            data.setType(type);
            data.setKnowledgeStatus(KnowledgeStatus.YSH);
            knowledgeService.update(data, title, content, loginUserVO.getAcc().getId());
            
            knowTagRelationService.del(id);
            for (String one : tagIds) {
                TagData tagData = tagService.getTagData(one);
                if (tagData != null) {
                    KnowTagRelationData newKnowTagRelationData = new KnowTagRelationData();
                    newKnowTagRelationData.setKnowledgeData(data);
                    newKnowTagRelationData.setTagData(tagData);
                    knowTagRelationService.save(newKnowTagRelationData);
                    ManageCacheUtil.removeKnowTag(tagData.getId());
                }
            }
            knowIndexService.deleteKnowIndexBySolr(data.getId());
            knowIndexService.updateKnowIndex(data.getId());
            ManageCacheUtil.removeKnowledgeDataById(data.getId());
            LogOperData logOper = new LogOperData();
            logOper.setCreateTime(Calendar.getInstance());
            com.chsi.knowledge.vo.LoginUserVO user = com.chsi.knowledge.web.util.WebAppUtil.getLoginUserVO(httpRequest);
            logOper.setUserId(user.getAcc().getId());
            if(user.getAuths().contains(com.chsi.knowledge.Constants.ROLE_CTI_USER)) {
                logOper.setM1("知识新增");
            }
            if(user.getAuths().contains(com.chsi.knowledge.Constants.ROLE_KNOWLEDGE)) {
                logOper.setM1("知识管理");
            }
            
            logOper.setM2("");
            logOper.setOper("修改");
            logOper.setMessage("知识");
            logOper.setKeyId(id);
            logOperService.save(logOper);
            
            return SUCCESS;
        }
        request.put(Constants.REQUEST_ERROR, "参数不能为空");
        return ERROR;
        
    }
    
    
 // 更新某个知识点，包括更新系统内的knowledge表、新闻系统里的知识点以及搜索引擎的索引
    public String checkUpdate() throws Exception {
        String error = "";
        if(ValidatorUtil.isNull(keywords)) {
            error = "请输入关键字";
        } else if(ValidatorUtil.isNull(title)) {
            error = "请输入标题";
        } else if(ValidatorUtil.isNull(content)) {
            error = "请输入回答";
        } else if(tagIds == null || tagIds.length == 0) {
            error = "请先到\"<a href='/htgl/tag/index.action'>便签管理</a>\"中增加标签";
        } else if(ValidatorUtil.isNull(sort)) {
            error = "请输入热点度";
        } else if(ValidatorUtil.isNull(type)) {
            error = "请设定知识类型";
        }
        if(!"".equals(error)) {
            request.put(Constants.REQUEST_ERROR, error);
            return ERROR;
        }
        LoginUserVO loginUserVO = getLoginUserVO();
        if (!ValidatorUtil.isNull(id) && !ValidatorUtil.isNull(title) && !ValidatorUtil.isNull(content) && !ValidatorUtil.isNull(sort) && tagIds != null && tagIds.length > 0 && !ValidatorUtil.isNull(keywords)&& !ValidatorUtil.isNull(type)) {
            KnowledgeData data = knowledgeService.getKnowledgeById(id);
            data.setKeywords(keywords);
            data.setSort(Integer.parseInt(sort));
            data.setUpdateTime(Calendar.getInstance());
            data.setUpdater(getLoginedUserId());
            data.setType(type);
            data.setKnowledgeStatus(KnowledgeStatus.YSH);
            knowledgeService.update(data, title, content, loginUserVO.getAcc().getId());
            
            knowTagRelationService.del(id);
            for (String one : tagIds) {
                TagData tagData = tagService.getTagData(one);
                if (tagData != null) {
                    KnowTagRelationData newKnowTagRelationData = new KnowTagRelationData();
                    newKnowTagRelationData.setKnowledgeData(data);
                    newKnowTagRelationData.setTagData(tagData);
                    knowTagRelationService.save(newKnowTagRelationData);
                    ManageCacheUtil.removeKnowTag(tagData.getId());
                }
            }
            knowIndexService.deleteKnowIndexBySolr(data.getId());
            knowIndexService.updateKnowIndex(data.getId());
            ManageCacheUtil.removeKnowledgeDataById(data.getId());
            LogOperData logOper = new LogOperData();
            logOper.setCreateTime(Calendar.getInstance());
            com.chsi.knowledge.vo.LoginUserVO user = com.chsi.knowledge.web.util.WebAppUtil.getLoginUserVO(httpRequest);
            logOper.setUserId(user.getAcc().getId());
            logOper.setM1("知识审核");
            logOper.setM2("");
            logOper.setOper("审核");
            logOper.setMessage("知识");
            logOper.setKeyId(id);
            logOperService.save(logOper);
            
            return SUCCESS;
        }
        request.put(Constants.REQUEST_ERROR, "参数不能为空");
        return ERROR;
    }
    
    
    
    
    
    //更新知识最后更新时间
    public String updateKnowledgeTime() throws Exception {
        if(!ValidatorUtil.isNull(id)) {
            KnowledgeData data = knowledgeService.getKnowledgeById(id);
            if(data!=null) {
                data.setUpdateTime(Calendar.getInstance());
                data.setUpdater(getLoginedUserId());
                knowledgeService.update(data);
            }
        }
        return SUCCESS;
    }
    
    //置顶知识
    public String topKnowledge() throws Exception {
        if(!ValidatorUtil.isNull(id)) {
            KnowledgeData data = knowledgeService.getKnowledgeById(id);
            if(data!=null) {
                data.setTopTime(Calendar.getInstance());
                data.setUpdater(getLoginedUserId());
                knowledgeService.update(data);
            }
        }
        return NONE;
    }
    
    //取消置顶知识
    public String untopKnowledge() throws Exception {
        if(!ValidatorUtil.isNull(id)) {
            KnowledgeData data = knowledgeService.getKnowledgeById(id);
            if(data!=null) {
                data.setTopTime(null);
                data.setUpdater(getLoginedUserId());
                knowledgeService.update(data);
            }
        }
        return NONE;
    }

    public String addKnowledge() throws Exception {
        String error = "";
        LogOperData logOper = new LogOperData();
        if(ValidatorUtil.isNull(keywords)) {
            error = "请输入关键字";
        } else if(ValidatorUtil.isNull(title)) {
            error = "请输入标题";
        } else if(ValidatorUtil.isNull(content)) {
            error = "请输入回答";
        } else if(tagIds == null || tagIds.length == 0) {
            error = "请先到\"<a href='/htgl/tag/index.action'>便签管理</a>\"中增加标签";
        } else if(ValidatorUtil.isNull(sort)) {
            error = "请输入热点度";
        } else if(ValidatorUtil.isNull(type)) {
            error = "请设定知识类型";
        }
        if(!"".equals(error)) {
            request.put(Constants.REQUEST_ERROR, error);
            return ERROR;
        }
        LoginUserVO loginUserVO = getLoginUserVO();
        // 保存知识
        knowledgeData = new KnowledgeData(null, keywords, null, 0, Integer.parseInt(sort), KnowledgeStatus.YSH, getLoginedUserId(), Calendar.getInstance(), null, null, type);
        knowledgeData.setCtiVisitCnt(0);
        knowledgeService.save(knowledgeData, title, content, loginUserVO.getOrg().getCode(), getLoginedUserId());
        for (String one : tagIds) {
            TagData tagData = tagService.getTagData(one);
            if (tagData != null) {
                // 保存知识与标签关系
                KnowTagRelationData knowTagRelationData = new KnowTagRelationData(null, knowledgeData, tagData);
                knowTagRelationService.save(knowTagRelationData);
                ManageCacheUtil.removeKnowTag(tagData.getId());
            }
        }
        knowIndexService.updateKnowIndex(knowledgeData.getId());
        id = knowledgeData.getId();
        logOper.setCreateTime(Calendar.getInstance());
        com.chsi.knowledge.vo.LoginUserVO user = com.chsi.knowledge.web.util.WebAppUtil.getLoginUserVO(httpRequest);
        logOper.setUserId(user.getAcc().getId());

        logOper.setM1("知识管理");
        
        logOper.setM2("");
        logOper.setOper("新增");
        logOper.setMessage("知识");
        logOper.setKeyId(id);
        logOperService.save(logOper);
        return SUCCESS;
        
    }
    public String addDSHKnowledge() throws Exception {
        String error = "";
        LogOperData logOper = new LogOperData();
        if(ValidatorUtil.isNull(keywords)) {
            error = "请输入关键字";
        } else if(ValidatorUtil.isNull(title)) {
            error = "请输入标题";
        } else if(ValidatorUtil.isNull(content)) {
            error = "请输入回答";
        } else if(tagIds == null || tagIds.length == 0) {
            error = "请先到\"<a href='/htgl/tag/index.action'>便签管理</a>\"中增加标签";
        } else if(ValidatorUtil.isNull(sort)) {
            error = "请输入热点度";
        } else if(ValidatorUtil.isNull(type)) {
            error = "请设定知识类型";
        }
        if(!"".equals(error)) {
            request.put(Constants.REQUEST_ERROR, error);
            return ERROR;
        }
        LoginUserVO loginUserVO = getLoginUserVO();
        // 保存知识
        knowledgeData = new KnowledgeData(null, keywords, null, 0, Integer.parseInt(sort), KnowledgeStatus.DSH, getLoginedUserId(), Calendar.getInstance(), null, null, type);
        knowledgeData.setCtiVisitCnt(0);
        knowledgeService.save(knowledgeData, title, content, loginUserVO.getOrg().getCode(), getLoginedUserId());
        for (String one : tagIds) {
            TagData tagData = tagService.getTagData(one);
            if (tagData != null) {
                // 保存知识与标签关系
                KnowTagRelationData knowTagRelationData = new KnowTagRelationData(null, knowledgeData, tagData);
                knowTagRelationService.save(knowTagRelationData);
                ManageCacheUtil.removeKnowTag(tagData.getId());
            }
        }
//        knowIndexService.updateKnowIndex(knowledgeData.getId());
        id = knowledgeData.getId();
        logOper.setCreateTime(Calendar.getInstance());
        com.chsi.knowledge.vo.LoginUserVO user = com.chsi.knowledge.web.util.WebAppUtil.getLoginUserVO(httpRequest);
        logOper.setUserId(user.getAcc().getId());

        logOper.setM1("知识新增");

        logOper.setM2("");
        logOper.setOper("新增");
        logOper.setMessage("知识");
        logOper.setKeyId(id);
        logOperService.save(logOper);
        return SUCCESS;
    }

    public void delKnowledge() throws Exception {
        if (!ValidatorUtil.isNull(id)) {
            KnowledgeData data = knowledgeService.getKnowledgeById(id);
            if(data!=null) {
                knowIndexService.deleteKnowIndexBySolr(data.getId());// 删索引
//                CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
//                cmsServiceClient.deleteArticle(data.getCmsId());// 从新闻系统删除
                data.setKnowledgeStatus(KnowledgeStatus.YSC);
                knowledgeService.update(data);// 从本系统删除,非真删除
//                knowTagRelationService.del(data.getId());
                List<KnowTagRelationData> ktrList = knowTagRelationService.getKnowTagRelationByKnowId(data.getId());
                for(KnowTagRelationData one:ktrList) {
                    ManageCacheUtil.removeKnowTag(one.getTagData().getId());
                }
                
                LogOperData logOper = new LogOperData();
                logOper.setCreateTime(Calendar.getInstance());
                com.chsi.knowledge.vo.LoginUserVO user = com.chsi.knowledge.web.util.WebAppUtil.getLoginUserVO(httpRequest);
                logOper.setUserId(user.getAcc().getId());
                logOper.setM1("知识管理");
                logOper.setM2("");
                logOper.setOper("删除");
                logOper.setMessage("知识");
                logOper.setKeyId(id);
                logOperService.save(logOper);
                
            }
            ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
        } else {
            ajaxMessage.addMessage("id不能为空！");
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        }
        writeJSON(ajaxMessage);
    }
    
    public void getLogOper() throws Exception{
        if (!ValidatorUtil.isNull(keyId)) {
            List<LogOperData> listData = logOperService.getLogOperByKeyId(keyId);
            List<LogOperVO> listVO = new ArrayList<LogOperVO>();
            for(LogOperData logOper : listData){
                LogOperVO logOperVO = new LogOperVO(logOper);
                listVO.add(logOperVO);
            }
            ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
            ajaxMessage.setO(listVO);
        }else {
            ajaxMessage.addMessage("keyId不能为空！");
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        }
        writeJSON(ajaxMessage);
        
    }
    
    public void showDiscussContent() throws Exception{
        String KId = id;
        contentList = discussService.getDiscussInfoVOList(KId, curPage, 10);
        ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
        ajaxMessage.setO(contentList);
        writeJSON(ajaxMessage);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getTagIds() {
        return tagIds;
    }

    public void setTagIds(String[] tagIds) {
        this.tagIds = tagIds;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public LogOperService getLogOperService() {
        return logOperService;
    }

    public void setLogOperService(LogOperService logOperService) {
        this.logOperService = logOperService;
    }
    
}
