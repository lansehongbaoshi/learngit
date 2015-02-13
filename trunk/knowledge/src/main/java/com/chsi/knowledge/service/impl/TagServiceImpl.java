package com.chsi.knowledge.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.chsi.framework.service.BaseDbService;
import com.chsi.knowledge.ServiceConstants;
import com.chsi.knowledge.dao.KnowledgeDataDAO;
import com.chsi.knowledge.dao.TagDataDAO;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.pojo.TagData;
import com.chsi.knowledge.service.TagService;
import com.chsi.knowledge.vo.TagVO;

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
    public List<TagVO> getTagVOsBySystemIdAndStatus(String systemId, KnowledgeStatus knowledgeStatus) {
        List<TagData> tagDataList = tagDataDAO.getTagDataBySystemId(systemId);
        if (null == tagDataList || tagDataList.size() == 0)
            return null;
        List<TagVO> tagVOList = new ArrayList<TagVO>();
        TagVO tagVO = null;
        Long count;
        for (TagData tagData : tagDataList) {
            count = knowledgeDataDAO.countKnowledges(systemId, tagData.getName(), knowledgeStatus);
            tagVO = new TagVO(tagData, count);
            tagVOList.add(tagVO);
        }
        return tagVOList;
    }

    @Override
    public void saveOrUpdate(TagData tagData) {
        tagDataDAO.saveOrUpdate(tagData);        
    }

    @Override
    public TagData getTagDataBySystemIdAndName(String systemId, String name) {
        return tagDataDAO.getTagDataBySystemIdAndName(systemId, name);
    }
    
}
