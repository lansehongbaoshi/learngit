package com.chsi.knowledge.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.chsi.framework.pojos.PersistentObject;

/**
 * 
 * @author think
 * 
 */
@Entity
@Table(name = "A_LOG")
@DynamicUpdate(value = true)
public class ALogData extends PersistentObject {

    /**
     * 
     */
    private static final long serialVersionUID = 635454186617449512L;
    private String id;
    private String qaLogId;
    private String cmsId;
    private String cmsVersion;

    public ALogData() {
        super();
    }

    public ALogData(String id, String qaLogId, String cmsId, String cmsVersion) {
        super();
        this.id = id;
        this.setQaLogId(qaLogId);
        this.setCmsId(cmsId);
        this.setCmsVersion(cmsVersion);
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

    @Column(name = "QA_LOG_ID")
    public String getQaLogId() {
        return qaLogId;
    }

    public void setQaLogId(String qaLogId) {
        this.qaLogId = qaLogId;
    }

    @Column(name = "CMS_ID")
    public String getCmsId() {
        return cmsId;
    }

    public void setCmsId(String cmsId) {
        this.cmsId = cmsId;
    }

    @Column(name = "CMS_VERSION")
    public String getCmsVersion() {
        return cmsVersion;
    }

    public void setCmsVersion(String cmsVersion) {
        this.cmsVersion = cmsVersion;
    }

}
