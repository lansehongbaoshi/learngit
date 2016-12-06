package com.chsi.knowledge.pojo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.chsi.framework.pojos.PersistentObject;

@Entity
@Table(name = "SEARCH_LOG")
@DynamicUpdate(value = true)
public class SearchLogData extends PersistentObject {

    /**
     * 
     */
    private static final long serialVersionUID = -755797029470701198L;

    private String id;
    private String systemId;
    private String keyword;
    private String searchResult;
    private Calendar createTime;
    private String userId;
    private String userIP;

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "app_seq")
    @GenericGenerator(name = "app_seq", strategy = "com.chsi.framework.hibernate.StringRandomGenerator")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "SYSTEM_ID")
    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    @Column(name = "KEYWORD")
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        keyword = keyword == null ? "" : keyword;
        this.keyword = keyword.length() > 50 ? keyword.substring(0, 50) : keyword;
    }

    @Column(name = "SEARCH_RESULT")
    public String getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(String searchResult) {
        this.searchResult = searchResult;
    }

    @Column(name = "CREATE_TIME")
    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }

    @Column(name = "USER_ID")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "USER_IP")
    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

}
