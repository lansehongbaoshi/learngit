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
import com.chsi.knowledge.dic.AType;
import com.chsi.knowledge.dic.QType;

/**
 * 
 * @author think
 *
 */
@Entity
@Table(name = "QA_LOG")
@DynamicUpdate(value = true)
public class QALogData extends PersistentObject {
    /**
     * 
     */
    private static final long serialVersionUID = -2321629795433161906L;
    
    private String id;
    private String sessionId;
    private QType qType;
    private String q;
    private AType aType;
    private Calendar createTime;

    public QALogData(){
        super();
    }
    
    public QALogData(String id, String sessionId, QType qType, String q, AType aType, Calendar createTime) {
        super();
        this.id = id;
        this.sessionId = sessionId;
        this.qType = qType;
        this.q = q;
        this.aType = aType;
        this.createTime = createTime;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "app_seq")
    @GenericGenerator(name = "app_seq", strategy = "com.chsi.framework.hibernate.StringRandomGenerator")
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String arg0) {
        this.id = arg0;
    }

    @Column(name = "SESSION_ID")
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Column(name = "Q_TYPE")
    public QType getQType() {
        return qType;
    }

    public void setQType(QType qType) {
        this.qType = qType;
    }

    @Column(name = "Q")
    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    @Column(name = "A_TYPE")
    public AType getaType() {
        return aType;
    }

    public void setaType(AType aType) {
        this.aType = aType;
    }

    @Column(name = "CREATE_TIME")
    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }
    
}
