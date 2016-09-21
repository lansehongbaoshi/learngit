package com.chsi.knowledge.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.framework.pojos.PersistentObject;
import com.chsi.knowledge.dao.WeatherCodeDataDAO;
import com.chsi.knowledge.pojo.WeatherCodeData;

public class WeatherCodeDataDAOImpl extends BaseHibernateDAO implements
        WeatherCodeDataDAO {

    @Override
    public void save(List<WeatherCodeData> weatherCodeDatas) {
        // TODO Auto-generated method stub
        Session session = hibernateUtil.getSessionFactory().openSession();
        for(PersistentObject pojo : weatherCodeDatas){
            session.save(pojo);
            session.flush();
        }
    }

    @Override
    public WeatherCodeData getWeatherCodeByName(String string) {
        // TODO Auto-generated method stub
        String hql = "FROM WeatherCodeData WHERE name=:name";
        return (WeatherCodeData) hibernateUtil.getSession().createQuery(hql).setString("name", string).uniqueResult();

    }

}
