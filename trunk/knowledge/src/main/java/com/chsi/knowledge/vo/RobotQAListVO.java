package com.chsi.knowledge.vo;

import java.io.Serializable;
import java.util.List;

import com.chsi.knowledge.util.Pagination;
import com.chsi.search.client.vo.RobotQABean;

public class RobotQAListVO<T> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8318518810889404360L;
    private List<RobotQABean> robotBeans; // 知识列表
    private Pagination pagination; // 分页数据
    
    public RobotQAListVO(List<RobotQABean> robotBean,Pagination pagination){
        this.robotBeans=robotBean;
        this.pagination=pagination;
    }
    public List<T> getRobotBean() {
        return (List<T>) robotBeans;
    }
    public void setRobotBean(List<RobotQABean> robotBean) {
        this.robotBeans = robotBean;
    }
    public Pagination getPagination() {
        return pagination;
    }
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
    

}
