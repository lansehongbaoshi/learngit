package com.chsi.knowledge.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chsi.knowledge.util.LevelData;

/**
 * 知识PAGEVO  将数据与其他的信息封装起来方便传输
 * @author chenjian
 */
public class KnowListPageVO {

    // 数据
    private List<KnowledgeVO> knowledgeVOList;
    // 数据层级
    private List<LevelData> levelDataList;
    // 分页
    private PaginationVO paginationVO;

    public KnowListPageVO(){
        
    }
    
    public KnowListPageVO(List<KnowledgeVO> knowledgeVOList, List<LevelData> levelDataList, PaginationVO paginationVO) {
        this.knowledgeVOList = knowledgeVOList;
        this.levelDataList = levelDataList;
        this.paginationVO = paginationVO;
    }

    public List<KnowledgeVO> getKnowledgeVOList() {
        return knowledgeVOList;
    }

    public void setKnowledgeVOList(List<KnowledgeVO> knowledgeVOList) {
        this.knowledgeVOList = knowledgeVOList;
    }

    public List<LevelData> getLevelDataList() {
        return levelDataList;
    }

    public void setLevelDataList(List<LevelData> levelDataList) {
        this.levelDataList = levelDataList;
    }

    public PaginationVO getPaginationVO() {
        return paginationVO;
    }

    public void setPaginationVO(PaginationVO paginationVO) {
        this.paginationVO = paginationVO;
    }
    
    public static class KnowledgeVO {
        private String title;
        private Map<String,String> param;

        public KnowledgeVO(String id, String title) {
            this.title = title;
            param = new HashMap<String,String>();
            param.put("id", id);
        }

        public Map<String, String> getParam() {
            return param;
        }

        public void setParam(Map<String, String> param) {
            this.param = param;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        
    }


}
