package com.chsi.knowledge.vo;

import java.io.Serializable;
import java.util.Calendar;

import com.chsi.knowledge.pojo.LogOperData;
import com.chsi.knowledge.util.DateUtil;

public class LogOperVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6414964738357105329L;
    private String id;
    private String createDate;
    private String userId;
    private String m1;
    private String m2;
    private String oper;
    private String message;
    private String keyId;
    
    
    public LogOperVO() {
        super();
    }
    public LogOperVO(LogOperData logOper) {
        setId(logOper.getId());
        setCreateDate(DateUtil.calendarToString(logOper.getCreateTime()));
        setUserId(logOper.getUserId());
        setM1(logOper.getM1());
        setM2(logOper.getM2());
        setOper(logOper.getOper());
        setMessage(logOper.getMessage());
        setKeyId(logOper.getKeyId());
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getM1() {
        return m1;
    }
    public void setM1(String m1) {
        this.m1 = m1;
    }
    public String getM2() {
        return m2;
    }
    public void setM2(String m2) {
        this.m2 = m2;
    }
    public String getOper() {
        return oper;
    }
    public void setOper(String oper) {
        this.oper = oper;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getKeyId() {
        return keyId;
    }
    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

}
