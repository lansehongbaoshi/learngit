package com.chsi.knowledge.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WssqPage<T> implements Serializable{
    private static final long serialVersionUID = 5638744197244451419L;

    public static final int DEFAULT_PAGE = 15;

    private int page;
    private int pageSize;
    private int rowCount;
    private List<T> list;
    private boolean hasNext;
    private int pageCount;
    public WssqPage() {
        super();
        pageSize = DEFAULT_PAGE;
    }
    public WssqPage(int page, int pageSize, int rowCount, List<T> list) {
        super();
        this.page = page;
        this.pageSize = pageSize;
        this.rowCount = rowCount;
        this.list = list;
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getRowCount() {
        return rowCount;
    }
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }
    public List<T> getList() {
        if(null == list)list = new ArrayList<T>();
        return list;
    }
    public void setList(List<T> list) {
        this.list = list;
    }
    public void setHasNext(boolean hasNext) {
        this.hasNext = page < getPageCount();
    }
    public boolean isHasNext() {
        return page < getPageCount();
    }
    public int getPageCount(){
        return rowCount%pageSize == 0?(rowCount/pageSize):(rowCount/pageSize+1);
    }
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
    public String getNextUrl() {
        return getStr()+"&page="+(getPage()+1);
    }
    public String getCurrUrl() {
        return getStr()+"&page="+getPage();
    }
    public String getPrevUrl() {
        return getStr()+"&page="+(getPage()-1);
    }
    public String getFirstUrl(){
        return getStr()+"&page=1";
    }
    public String getLastUrl(){
        return getStr()+"&page="+getPageCount();
    }
    private String str;
    public String getStr(){
        return this.str;
    }
    public void setStr(String str){
        this.str = str;
    }
}
