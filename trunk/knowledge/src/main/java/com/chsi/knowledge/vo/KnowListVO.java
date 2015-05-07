package com.chsi.knowledge.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chsi.knowledge.util.Pagination;

/**
 * 问题列表
 * @author chenjian
 */
public class KnowListVO {
   
    private List<Know> knows; // 知识列表
    private Pagination pagination; // 分页

    public KnowListVO(List<Know> knows, Pagination pagination) {
        this.knows = knows;
        this.pagination = pagination;
    }

    public List<Know> getKnows() {
        return knows;
    }

    public void setKnows(List<Know> knows) {
        this.knows = knows;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public static class Know {
        private String title;
        private Map<String, String> param;

        public Know(String title) {
            this.title = title;
            param = new HashMap<String, String>();
        }

        public void addParam(String name,String param) {
            this.param.put(name, param);
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
