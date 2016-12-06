package com.chsi.knowledge.vo;

import java.util.List;

import com.chsi.knowledge.util.Navigation;

/**
 * 前台展示具体知识VO
 * 
 * @author chenjian
 */
public class ViewKnowVO {

    private ConKnow conKnow; // 知识详细信息
    private List<Navigation> navigations; // 导航

    public ViewKnowVO(ConKnow conKnow, List<Navigation> navigations) {
        this.conKnow = conKnow;
        this.navigations = navigations;
    }

    public ConKnow getConKnow() {
        return conKnow;
    }

    public void setConKnow(ConKnow conKnow) {
        this.conKnow = conKnow;
    }

    public List<Navigation> getNavigations() {
        return navigations;
    }

    public void setNavigations(List<Navigation> navigations) {
        this.navigations = navigations;
    }

    public static class ConKnow {
        private String id;
        private String title;
        private String content;
        private String keywords;
        private String systems;
        private int visitCnt;
        private String updateTime;
        private boolean ifDiscussed;

        public ConKnow(String id, String title, String content, String keywords, int visitCnt, String updateTime, String systems) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.keywords = keywords;
            this.visitCnt = visitCnt;
            this.updateTime = updateTime;
            this.ifDiscussed = false;
            this.systems = systems;
        }

        public boolean isIfDiscussed() {
            return ifDiscussed;
        }

        public void setIfDiscussed(boolean ifDiscussed) {
            this.ifDiscussed = ifDiscussed;
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

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getSystems() {
            return systems;
        }

        public void setSystems(String systems) {
            this.systems = systems;
        }

    }

}
