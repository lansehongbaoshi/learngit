package com.chsi.knowledge.util;

import java.io.Serializable;

/**
 * 分页信息
 * 
 * @author chenjian
 */
public class Pagination implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 9039433019859936799L;
    private int totalCount; // 总数
    private int pageCount;// 每页数量
    private int curPage;// 当前页

    public Pagination(int totalCount, int pageCount, int curPage) {
        this.totalCount = totalCount;
        this.pageCount = pageCount;
        this.curPage = curPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

}
