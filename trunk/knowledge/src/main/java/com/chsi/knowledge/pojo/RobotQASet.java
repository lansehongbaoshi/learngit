package com.chsi.knowledge.pojo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.chsi.framework.pojos.PersistentObject;

public class RobotQASet extends PersistentObject {

    /**
     * 
     */
    private static final long serialVersionUID = -6578119023561696175L;

    private String id;
    private String q;
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

    @Column(name = "Q")
    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    @Column(name = "A")
    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }
}
