package com.chsi.knowledge.util;

import java.util.List;

import com.chsi.knowledge.pojo.KnowTagRelationData;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.pojo.TagData;

public class ManageCacheUtil {

    private static final String CACHE_KEY_ = "knowledge." + ManageCacheUtil.class.getName();
    private static final String SEP = ".";
    //具体每个缓存项目的名称
    private static final String SYSTEM_PREFIX = CACHE_KEY_ + SEP + "system";
    private static final String TAG_PREFIX = CACHE_KEY_ + SEP + "tag";
    private static final String KNOWTAG_PREFIX = CACHE_KEY_ + SEP + "knowtag";
    
    //标签与知识关联关系的增删查
    public static boolean addKnowTag(String tagId, List<KnowTagRelationData> list) {
        String key = KNOWTAG_PREFIX + SEP + tagId ;
        if (list != null)
            return MemCachedUtil.add(key, list);
        else
            return false;
    }
    
    public static List<KnowTagRelationData>  getKnowTag(String tagId){
        String key = KNOWTAG_PREFIX + SEP + tagId ;
        return MemCachedUtil.get(key);
    }
    
    public static void removeKnowTag(String tagId){
        String key = KNOWTAG_PREFIX + SEP + tagId ;
        MemCachedUtil.removeByKey(key);
    }
    
    //标签的增删查
    public static boolean addTagList(String systemId, List<TagData> list) {
        String key = TAG_PREFIX + SEP + systemId ;
        if (null != list)
            return MemCachedUtil.add(key, list);
        else
            return false;
    }
    
    public static List<TagData>  getTagList(String systemId){
        String key = TAG_PREFIX + SEP + systemId ;
        return MemCachedUtil.get(key);
    }
    
    public static void removeTagList(String systemId){
        String key = TAG_PREFIX + SEP + systemId ;
        MemCachedUtil.removeByKey(key);
    }
    
    
    //系统的增删查
    public static boolean addSystem(String systemId, SystemData systemData){
        String key = SYSTEM_PREFIX + SEP + systemId;
        if (null != systemData)
            return MemCachedUtil.add(key, systemData);
        else
            return false;
    }
    
    public static SystemData  getSystem(String systemId){
        String key = SYSTEM_PREFIX + SEP + systemId;
        return MemCachedUtil.get(key);
    }
    
    public static void removeSystem(String systemId){
        String key = SYSTEM_PREFIX + SEP + systemId;
        MemCachedUtil.removeByKey(key);
    }
    
}
