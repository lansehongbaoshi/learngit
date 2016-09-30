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
@Table(name = "LOG_OPER")
@DynamicUpdate(value = true)
public class LogOperData extends PersistentObject {

    /**
     * 
     */
    private static final long serialVersionUID = 7052104487129289016L;
    
    private String id;
    private Calendar createTime;
    private String userId;
    private String m1;
    private String m2;
    private String oper;
    private String message;
    private String keyId;
    
    
    
    public LogOperData() {
        super();
    }
    
    

    public LogOperData(Calendar createTime, String userId, String m1,
            String oper, String message) {
        super();
        this.createTime = createTime;
        this.userId = userId;
        this.m1 = m1;
        this.oper = oper;
        this.message = message;
    }



    public LogOperData(Calendar createTime, String userId, String m1,
            String m2, String oper, String message) {
        super();
        this.createTime = createTime;
        this.userId = userId;
        this.m1 = m1;
        this.m2 = m2;
        this.oper = oper;
        this.message = message;
    }
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "app_seq")
    @GenericGenerator(name = "app_seq", strategy = "com.chsi.framework.hibernate.StringRandomGenerator")
    public String getId() {
        // TODO Auto-generated method stub
        return id;
    }

    @Override
    public void setId(String id) {
        // TODO Auto-generated method stub
        this.id = id;
    }

    @Column(name = "CREATE_TIME")
    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }

    @Column(name = "USERID")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "M1")
    public String getM1() {
        return m1;
    }

    public void setM1(String m1) {
        this.m1 = m1;
    }

    @Column(name = "M2")
    public String getM2() {
        return m2;
    }

    public void setM2(String m2) {
        this.m2 = m2;
    }

    @Column(name = "OPER")
    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

    @Column(name = "MESSAGE")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    @Column(name = "KEY_ID")
    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

}
