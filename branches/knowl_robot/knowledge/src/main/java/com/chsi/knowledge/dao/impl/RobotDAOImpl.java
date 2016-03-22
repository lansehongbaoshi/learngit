package com.chsi.knowledge.dao.impl;

import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.framework.pojos.PersistentObject;
import com.chsi.knowledge.dao.RobotDAO;

public class RobotDAOImpl extends BaseHibernateDAO implements RobotDAO{

    @Override
    public void save(PersistentObject pojo) {
        hibernateUtil.getSession().save(pojo);
    }

}
