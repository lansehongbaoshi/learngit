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
import com.chsi.knowledge.dic.QAType;

/**
 * 
 * @author think
 *
 */
@Entity
@Table(name = "QA_SESSION")
@DynamicUpdate(value = true)
public class QASessionData extends PersistentObject {

    /**
     * 
     */
    private static final long serialVersionUID = 8111375469862226156L;
    
    private String id;
    private String systemId;
    private QAType type;
    private Calendar startTime;
    private Calendar endTime;
    private String qUserId;
    private String qUserIp;
    private String aUserId;

    public QASessionData(){
        super();
    }
    
    public QASessionData(String id, String systemId, QAType type, Calendar startTime, Calendar endTime,
            String qUserId, String qUserIp,String aUserId) {
        super();
        this.id = id;
        this.systemId = systemId;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.qUserId = qUserId;
        this.qUserIp = qUserIp;
        this.aUserId = aUserId;
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

    @Column(name = "SYSTEM_ID")
    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    @Column(name = "TYPE")
    public QAType getType() {
        return type;
    }

    public void setType(QAType type) {
        this.type = type;
    }

    @Column(name = "START_TIME")
    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    @Column(name = "END_TIME")
    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    @Column(name = "Q_USER_ID")
    public String getQUserId() {
        return qUserId;
    }

    public void setQUserId(String qUserId) {
        this.qUserId = qUserId;
    }

    @Column(name = "Q_USER_IP")
    public String getQUserIp() {
        return qUserIp;
    }

    public void setQUserIp(String qUserIp) {
        this.qUserIp = qUserIp;
    }

    @Column(name = "A_USER_ID")
    public String getAUserId() {
        return aUserId;
    }

    public void setAUserId(String aUserId) {
        this.aUserId = aUserId;
    }

    
}
