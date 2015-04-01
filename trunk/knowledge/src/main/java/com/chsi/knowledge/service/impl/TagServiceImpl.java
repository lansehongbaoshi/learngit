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
import com.chsi.knowledge.util.LevelData;
import com.chsi.knowledge.util.LevelUtil;
import com.chsi.knowledge.vo.TagListPageVO;

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
    public  TagListPageVO getTagVOsBySystemIdAndStatus(String systemId, KnowledgeStatus knowledgeStatus) {
        List<TagData> tagDataList = tagDataDAO.getTagDataBySystemId(systemId);
        if (null == tagDataList || tagDataList.size() == 0)
            return null;
        List<TagListPageVO.TagVO> tagVOList = new ArrayList<TagListPageVO.TagVO>();
        TagListPageVO.TagVO tagVO = null;
        int count;
        for (TagData tagData : tagDataList) {
            count = knowledgeDataDAO.countKnowledges(systemId, tagData.getId(), knowledgeStatus);
            tagVO = new TagListPageVO.TagVO(tagData.getId(), tagData.getSystemData().getId(), tagData.getName(), tagData.getDescription(), count);
            tagVOList.add(tagVO);
        }
        List<LevelData> levelDataList = LevelUtil.getOneLevel(tagDataList.get(0).getSystemData());
        TagListPageVO TagPageVO = new TagListPageVO(tagVOList, levelDataList);
        return TagPageVO;
    }

    @Override
    public void saveOrUpdate(TagData tagData) {
        tagDataDAO.saveOrUpdate(tagData);        
    }

    @Override
    public TagData getTagDataById(String id) {
        return tagDataDAO.getTagDataById(id);
    }
    
    @Override
    public TagData getTagDataBySystemIdTagName(String systemId, String tagName) {
        return tagDataDAO.getTagDataBySystemIdTagName(systemId, tagName);
    }

}
