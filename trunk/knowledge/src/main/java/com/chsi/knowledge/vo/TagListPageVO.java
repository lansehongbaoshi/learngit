package com.chsi.knowledge.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chsi.knowledge.util.LevelData;

/**
 * 标签PAGEVO，将数据与其他的信息封装起来方便传输
 * @author chenjian
 */
public class TagListPageVO {

    // 数据
    private List<TagVO> tagVOList;
    // 数据层级
    private List<LevelData> levelDataList;

    public TagListPageVO() {

    }

    public TagListPageVO(List<TagVO> tagVOList, List<LevelData> levelDataList) {
        this.tagVOList = tagVOList;
        this.levelDataList = levelDataList;
    }

    public static class TagVO {
        private String name;
        private String description;
        private int count;
        private Map<String,String> param;

        public TagVO(String tagId, String systemId, String name, String description, int count) {
            param = new HashMap<String,String>();
            param.put("tagId", tagId);
            param.put("systemId", systemId);
            this.name = name;
            this.description = description;
            this.count = count;
        }

        public Map<String, String> getParam() {
            return param;
        }

        public void setParam(Map<String, String> param) {
            this.param = param;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
        
        
    }

    public List<TagVO> getTagVOList() {
        return tagVOList;
    }

    public void setTagVOList(List<TagVO> tagVOList) {
        this.tagVOList = tagVOList;
    }

    public List<LevelData> getLevelDataList() {
        return levelDataList;
    }

    public void setLevelDataList(List<LevelData> levelDataList) {
        this.levelDataList = levelDataList;
    }
    
}
