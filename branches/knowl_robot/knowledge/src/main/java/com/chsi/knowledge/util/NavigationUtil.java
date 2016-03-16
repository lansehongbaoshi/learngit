package com.chsi.knowledge.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.pojo.TagData;
/**
 * 导航工具类
 * @author chenjian
 */
public class NavigationUtil {
    //层级顺序
    public static final int systemLev = 1;
    public static final int tagLev = 2;
    public static final int knowLev = 3;
    
    //获取 系统-标签-知识 三层导航
    public static List<Navigation> getNavigation(SystemData systemData, TagData tagData, String title, String knowledgeId) {
        List<Navigation> list = new ArrayList<Navigation>();
        Navigation temp = getSystemLevel(systemData);
        if (null != temp)
            list.add(temp);
        temp = getTagLevel(tagData);
        if (null != temp)
            list.add(temp);
        temp = getKnowLevel(title, knowledgeId);
        if (null != temp)
            list.add(temp);
        return list;
    }
    
    //获取知识导航
    public static Navigation getKnowLevel(String title, String knowledgeId) {
        if (null != title && null != knowledgeId) {
            Map<String, String> tagPar = new HashMap<String, String>();
            tagPar.put("id", knowledgeId);
            if (null != title && title.length() > 100)
                title = title.substring(0, 100) + "...";
            return new Navigation(knowLev, title, title, tagPar);
        }
        return null;
            
    }
    
    //获取标签
    public static Navigation getTagLevel(TagData tagData){
        if (null != tagData){
            String tagName = tagData.getName();
            String tagDes = tagData.getDescription();
            Map<String, String> tagPar = new HashMap<String, String>();
            tagPar.put("systemId", tagData.getSystemData().getId());
            tagPar.put("tagId", tagData.getId());
            return new Navigation(tagLev, tagName, tagDes, tagPar);
        }
        return null;
    }
    
    //系统级导航
    public static Navigation getSystemLevel(SystemData systemData){
        if (null != systemData){
            String sysName = systemData.getName();
            String sysDes = systemData.getDescription();
            Map<String, String> sysPar = new HashMap<String, String>();
            sysPar.put("systemId", systemData.getId());
            return new Navigation(systemLev, sysName, sysDes, sysPar);
        }
        return null;
    }
    
}
