package com.chsi.knowledge.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.chsi.framework.pojos.PersistentObject;

@Entity
@Table(name = "ROBOT_A_SET")
@DynamicUpdate(value = true)
public class RobotASetData extends PersistentObject {
    /**
     * 
     */
    private static final long serialVersionUID = -2788510258592220956L;
    
    private String id;
    private String qId;
    private String a;
    
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

    @Column(name = "Q_ID")
    public String getqId() {
        return qId;
    }

    public void setqId(String qId) {
        this.qId = qId;
    }

    @Column(name = "A")
    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

}
