package com.chsi.knowledge.dao;

import com.chsi.knowledge.pojo.SystemData;

/**
 * 系统表操作DAO层
 * @author chenjian
 *
 */
public interface SystemDataDAO {

    SystemData getSystemById(String id);
}
