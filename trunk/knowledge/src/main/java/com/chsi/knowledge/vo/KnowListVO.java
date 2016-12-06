package com.chsi.knowledge.vo;

import java.io.Serializable;
import java.util.List;

import com.chsi.knowledge.util.Pagination;

/**
 * 问题列表
 * 
 * @author chenjian
 */
public class KnowListVO<T> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7487367204546209941L;
    private List<T> knows; // 知识列表
    private Pagination pagination; // 分页数据

    public KnowListVO(List<T> knows, Pagination pagination) {
        this.knows = knows;
        this.pagination = pagination;
    }

    public List<T> getKnows() {
        return knows;
    }

    public void setKnows(List<T> knows) {
        this.knows = knows;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}
