package com.chsi.knowledge.dao;

import com.chsi.knowledge.pojo.FileInfoData;

public interface FileDAO {
    void save(FileInfoData pojo);
    
    FileInfoData getFileInfoData(String id);
}
