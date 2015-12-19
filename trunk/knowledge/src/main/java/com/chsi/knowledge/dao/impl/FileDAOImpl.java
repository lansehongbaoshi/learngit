package com.chsi.knowledge.dao.impl;

import org.hibernate.Query;

import com.chsi.framework.hibernate.BaseHibernateDAO;
import com.chsi.knowledge.dao.FileDAO;
import com.chsi.knowledge.pojo.FileInfoData;

public class FileDAOImpl extends BaseHibernateDAO implements FileDAO {
    private static String select_file_info = "select p from FileInfoData p where p.id=:id";

    @Override
    public void save(FileInfoData pojo) {
        hibernateUtil.getSession().save(pojo);
    }

    @Override
    public FileInfoData getFileInfoData(String id) {
        Query query = hibernateUtil.getSession().createQuery(select_file_info);
        query.setString("id", id);
        return (FileInfoData)query.uniqueResult();
    }

}
