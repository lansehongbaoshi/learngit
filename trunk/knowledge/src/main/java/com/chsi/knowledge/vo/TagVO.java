package com.chsi.knowledge.vo;

import com.chsi.knowledge.pojo.TagData;
/**
 * 标签VO，方便前台显示标签问题数量
 * @author chsi-pc
 *
 */
public class TagVO {

    private TagData tagData;
    private Long count; //该标签下的问题总数

    public TagVO() {

    }
    
    public TagVO(TagData tagData, Long count) {
        this.tagData = tagData;
        this.count = count;
    }

    public TagData getTagData() {
        return tagData;
    }

    public void setTagData(TagData tagData) {
        this.tagData = tagData;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    
}
