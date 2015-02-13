package com.chsi.knowledge.vo;

import java.util.List;

import com.chsi.knowledge.pojo.DiscussData;
/**
 * 评价VO
 * @author chenjian
 *
 */
public class DiscussVO {

    private List<DiscussData> discussDataList;

    private KnowledgeVO knowledgeVO;

    public List<DiscussData> getDiscussDataList() {
        return discussDataList;
    }

    public void setDiscussDataList(List<DiscussData> discussDataList) {
        this.discussDataList = discussDataList;
    }

    public KnowledgeVO getKnowledgeVO() {
        return knowledgeVO;
    }

    public void setKnowledgeVO(KnowledgeVO knowledgeVO) {
        this.knowledgeVO = knowledgeVO;
    }

}
