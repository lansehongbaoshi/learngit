package com.chsi.knowledge.service.impl;

import com.chsi.framework.service.BaseDbService;
import com.chsi.knowledge.ServiceConstants;
import com.chsi.knowledge.dao.FileDAO;
import com.chsi.knowledge.pojo.FileInfoData;
import com.chsi.knowledge.service.FileService;

public class FileServiceImpl extends BaseDbService implements FileService {
    FileDAO fileDAO;

    @Override
    protected void doCreate() {
        fileDAO = getDAO(ServiceConstants.FILE_DAO, FileDAO.class);
    }

    @Override
    protected void doRemove() {
    }

    @Override
    public void save(FileInfoData pojo) {
        fileDAO.save(pojo);
    }

    @Override
    public FileInfoData getFileInfoData(String id) {
        return fileDAO.getFileInfoData(id);
    }
}
