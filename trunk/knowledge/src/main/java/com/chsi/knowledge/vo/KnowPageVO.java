package com.chsi.knowledge.vo;

import java.util.Calendar;
import java.util.List;

import com.chsi.knowledge.util.LevelData;

/**
 * 一个问题详细情况PAGEVO
 * @author chenjian
 *
 */
public class KnowPageVO {

    //数据
    private KnowledgeVO knowledgeVO;
    //层级
    private List<LevelData> levelDataList;
    
    public KnowPageVO(){
        
    }
    
    public KnowPageVO(KnowledgeVO knowledgeVO, List<LevelData> levelDataList){
        this.knowledgeVO = knowledgeVO;
        this.levelDataList = levelDataList;
    }
    
    
    public KnowledgeVO getKnowledgeVO() {
        return knowledgeVO;
    }

    public void setKnowledgeVO(KnowledgeVO knowledgeVO) {
        this.knowledgeVO = knowledgeVO;
    }

    public List<LevelData> getLevelDataList() {
        return levelDataList;
    }

    public void setLevelDataList(List<LevelData> levelDataList) {
        this.levelDataList = levelDataList;
    }


    public static class KnowledgeVO {
        private String id;
        private String title;
        private String content;
        private String keywords;
        private int visitCnt;
        private Calendar updateTime;

        public KnowledgeVO(String id, String title, String content, String keywords, int visitCnt, Calendar updateTime) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.keywords = keywords;
            this.visitCnt = visitCnt;
            this.updateTime = updateTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public int getVisitCnt() {
            return visitCnt;
        }

        public void setVisitCnt(int visitCnt) {
            this.visitCnt = visitCnt;
        }

        public Calendar getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Calendar updateTime) {
            this.updateTime = updateTime;
        }

    }

    
    
    
    
    
}
