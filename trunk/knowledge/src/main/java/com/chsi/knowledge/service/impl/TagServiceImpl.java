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
import com.chsi.knowledge.util.LevelData;
import com.chsi.knowledge.util.LevelUtil;
import com.chsi.knowledge.util.ManageCacheUtil;
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
        
        List<TagData> tagDataList = ManageCacheUtil.getTagList(systemId);
        if(null == tagDataList){
            tagDataList = tagDataDAO.getTagDataBySystemId(systemId);
            ManageCacheUtil.addTagList(systemId, tagDataList);
        }
        if (null == tagDataList || tagDataList.size() == 0)
            return null;
        List<TagListPageVO.TagVO> tagVOList = new ArrayList<TagListPageVO.TagVO>();
        TagListPageVO.TagVO tagVO = null;
        int count;
        List<KnowTagRelationData> list = null;
        for (TagData tagData : tagDataList) {
            list = ManageCacheUtil.getKnowTag(tagData.getId());
            //这里不加缓存，否则点开一个标签，所有的问题就加到缓存里
            if (null == list)              
                count = knowledgeDataDAO.countKnowledges(systemId, tagData.getId(), knowledgeStatus);
            else
                count = list.size();
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
    public List<TagData> get() {
        return tagDataDAO.getTagDataBySystemId("zb");
    }

}
