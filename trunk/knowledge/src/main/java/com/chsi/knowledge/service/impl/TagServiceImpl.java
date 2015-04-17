package com.chsi.knowledge.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.chsi.framework.service.BaseDbService;
import com.chsi.knowledge.ServiceConstants;
import com.chsi.knowledge.dao.KnowledgeDataDAO;
import com.chsi.knowledge.dao.TagDataDAO;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.pojo.KnowTagRelationData;
import com.chsi.knowledge.pojo.TagData;
import com.chsi.knowledge.service.TagService;
import com.chsi.knowledge.util.ManageCacheUtil;
import com.chsi.knowledge.vo.ViewTagVO;

public class TagServiceImpl extends BaseDbService implements TagService{
    
    private TagDataDAO tagDataDAO;
    private KnowledgeDataDAO knowledgeDataDAO;
    
    @Override
    protected void doCreate() {
        tagDataDAO = getDAO(ServiceConstants.TAGDATA_DAO, TagDataDAO.class);
        knowledgeDataDAO = getDAO(ServiceConstants.KNOWLEDGEDATA_DAO, KnowledgeDataDAO.class);
    }

    @Override
    protected void doRemove() {
        
    }

    @Override
    public List<ViewTagVO> getTagVOsBySystemIdAndStatus(String systemId, KnowledgeStatus knowledgeStatus) {
        List<TagData> tagDataList = ManageCacheUtil.getTagList(systemId);
        if(null == tagDataList){
            tagDataList = tagDataDAO.getTagDataBySystemId(systemId);
            ManageCacheUtil.addTagList(systemId, tagDataList);
        }
        if (null == tagDataList || tagDataList.size() == 0)
            return null;
        List<ViewTagVO> tagVOList = new ArrayList<ViewTagVO>();
        ViewTagVO tagVO = null;
        int count;
        List<KnowTagRelationData> list = null;
        for (TagData tagData : tagDataList) {
            list = ManageCacheUtil.getKnowTag(tagData.getId());
            if (null == list)              
                count = knowledgeDataDAO.countKnowledges(tagData.getId(), knowledgeStatus);
            else
                count = list.size();
            tagVO = new ViewTagVO(tagData.getId(), tagData.getSystemData().getId(), tagData.getName(), tagData.getDescription(), count);
            tagVOList.add(tagVO);
        }
        return tagVOList;
    }

    @Override
    public void saveOrUpdate(TagData tagData) {
        tagDataDAO.saveOrUpdate(tagData);        
    }

    @Override
    public List<TagData> get() {
        return tagDataDAO.getTagDataBySystemId("zb");
    }

    @Override
    public TagData getTagData(String systemId, String name) {
        return tagDataDAO.getTagData(systemId, name);
    }

}
