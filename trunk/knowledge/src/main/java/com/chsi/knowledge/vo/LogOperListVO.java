package com.chsi.knowledge.vo;

import java.io.Serializable;
import java.util.List;

import com.chsi.knowledge.util.Pagination;

public class LogOperListVO<T> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7010839663348549603L;
    private List<T> logOpers; // 知识列表
    private Pagination pagination; // 分页数据

    public LogOperListVO(List<T> logOpers, Pagination pagination) {
        this.logOpers = logOpers;
        this.pagination = pagination;
    }

    public List<T> getLogOpers() {
        return logOpers;
    }

    public void setLogOpers(List<T> logOpers) {
        this.logOpers = logOpers;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}
