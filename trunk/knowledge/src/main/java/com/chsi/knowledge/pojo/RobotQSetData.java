package com.chsi.knowledge.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.chsi.framework.pojos.PersistentObject;

@Entity
@Table(name = "ROBOT_Q_SET")
@DynamicUpdate(value = true)
public class RobotQSetData extends PersistentObject {

    /**
     * 
     */
    private static final long serialVersionUID = -2979356167086428532L;

    private String id;
    private String q;
    private int num;

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

    @Column(name = "NUM")
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Transient
    public boolean isSystemDefined() {
        return this.q.startsWith("#");
    }
}
