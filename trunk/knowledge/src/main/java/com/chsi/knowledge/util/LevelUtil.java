package com.chsi.knowledge.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.pojo.TagData;
/**
 * 层级工具类，方便获取层级数据
 * @author chenjian
 */
public class LevelUtil {
    public static final int systemLev = 0;
    public static final int tagLev = 1;
    public static final int knowLev = 2;
    
    
    //获取三级导航
    public static List<LevelData> getThreeLevel(TagData tagData, String title, String knowledgeId) {
        List<LevelData> list = null;
        if (null == tagData) {
            list = getTwoLevel(null);
            list.add(null);
        } else {
            // 获取一、二层级
            list = getTwoLevel(tagData);
            // 获取第三层
            Map<String, String> tagPar = new HashMap<String, String>();
            tagPar.put("id", knowledgeId);
            if (null != title && title.length() > 100)
            title = title.substring(0, 100) + "...";
            LevelData tag = new LevelData(knowLev, title, title, tagPar);
            list.add(tag);
        }
        return list;
    }
    
    
    //获取两级导航
    public static List<LevelData> getTwoLevel(TagData tagData){
        List<LevelData> list=null;
        if (null == tagData){
            list = getOneLevel(null);
            list.add(null);
        }else{
            // 第一个层次，系统的
             list = getOneLevel(tagData.getSystemData());
            // 第二个层次，标签的
            String tagName = tagData.getName();
            String tagDes = tagData.getDescription();
            Map<String, String> tagPar = new HashMap<String, String>();
            tagPar.put("systemId", tagData.getSystemData().getId());
            tagPar.put("tagId", tagData.getId());
            LevelData tag = new LevelData(tagLev, tagName, tagDes, tagPar);
            list.add(tag);
        }
        return list;
    }
    
    //获取 一级导航
    public static List<LevelData> getOneLevel(SystemData systemData){
        List<LevelData> list=new ArrayList<LevelData>();
        if (null == systemData){
            list.add(null);
        }else{
         // 第一个层次，系统的
            String sysName = systemData.getName();
            String sysDes = systemData.getDescription();
            Map<String, String> sysPar = new HashMap<String, String>();
            sysPar.put("systemId", systemData.getId());
            LevelData system = new LevelData(systemLev, sysName, sysDes, sysPar);
            list.add(system);
        }
        return list;
    }
    
}
