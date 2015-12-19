package com.chsi.knowledge.service;

import com.chsi.knowledge.pojo.FileInfoData;

public interface FileService {
    void save(FileInfoData pojo);
    
    FileInfoData getFileInfoData(String id);
}
