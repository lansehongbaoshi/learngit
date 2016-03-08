package com.chsi.knowledge.util;

import java.util.List;

import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.pojo.KnowTagRelationData;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.pojo.SystemOpenTimeData;
import com.chsi.knowledge.pojo.TagData;
import com.chsi.knowledge.service.CommonService;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.ServiceFactory;
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.service.TagService;
import com.chsi.search.client.vo.KnowledgeVO;

/**
 * 缓存管理工具
 * 
 * @author chenjian
 */
public class ManageCacheUtil {

    private static final String CACHE_KEY_ = "knowledge." + ManageCacheUtil.class.getName();
    private static final String SEP = ".";
    // 具体每个缓存项目的名称
    private static final String SYSTEM_PREFIX = CACHE_KEY_ + SEP + "system";
    private static final String TAG_PREFIX = CACHE_KEY_ + SEP + "tag";
    private static final String KNOWTAG_PREFIX = CACHE_KEY_ + SEP + "knowtag";
    private static final String KNOW_PREFIX = CACHE_KEY_ + SEP + "know";
    
    public static KnowledgeData getKnowledgeDataById(String id) {
        String key = KNOW_PREFIX + SEP + id;
        KnowledgeData data = MemCachedUtil.get(key);
        if(data==null) {
            KnowledgeService knowledgeService = ServiceFactory.getKnowledgeService();
            data = knowledgeService.getKnowledgeWithArticleById(id);
            MemCachedUtil.set(key, data);
        }
        return data;
    }
    
    public static void removeKnowledgeDataById(String id) {
        String key = KNOW_PREFIX + SEP + id;
        MemCachedUtil.removeByKey(key);
    }

    // 标签与知识关联关系的增删查
    public static boolean addKnowTag(String tagId,
            List<KnowTagRelationData> list) {
        String key = KNOWTAG_PREFIX + SEP + tagId;
        if (null != list) {
            return MemCachedUtil.set(key, list);
        }
        return false;
    }

    public static List<KnowTagRelationData> getKnowTag(String tagId) {
        String key = KNOWTAG_PREFIX + SEP + tagId;
        List<KnowTagRelationData> list = MemCachedUtil.get(key);
        if(list==null) {
            KnowledgeService knowledgeService = ServiceFactory.getKnowledgeService();
            list = knowledgeService.getKnowTagDatas(tagId, KnowledgeStatus.YSH);
            MemCachedUtil.set(key, list);
        }
        return list;
    }

    public static void removeKnowTag(String tagId) {
        String key = KNOWTAG_PREFIX + SEP + tagId;
        MemCachedUtil.removeByKey(key);
    }

    // 标签的增删查
    public static boolean addTagList(String systemId, List<TagData> list) {
        String key = TAG_PREFIX + SEP + systemId;
        if (null != list) {
            return MemCachedUtil.set(key, list);
        }
        return false;
    }

    public static List<TagData> getTagList(String systemId) {
        String key = TAG_PREFIX + SEP + systemId;
        List<TagData> list = MemCachedUtil.get(key);
        if(list==null) {
            TagService tagService = ServiceFactory.getTagService();
            list = tagService.get(systemId);
            MemCachedUtil.set(key, list);
        }
        return list;
    }

    public static void removeTagList(String systemId) {
        String key = TAG_PREFIX + SEP + systemId;
        MemCachedUtil.removeByKey(key);
    }

    // 系统的增删查
    public static boolean addSystem(String systemId, SystemData systemData) {
        String key = SYSTEM_PREFIX + SEP + systemId;
        if (null != systemData) {
            return MemCachedUtil.set(key, systemData);
        }
        return false;
    }

    public static SystemData getSystem(String systemId) {
        String key = SYSTEM_PREFIX + SEP + systemId;
        SystemData data = MemCachedUtil.get(key);
        if(data==null) {
            SystemService systemService = ServiceFactory.getSystemService();
            data = systemService.getSystemById(systemId);
            MemCachedUtil.set(key, data);
        }
        return data;
    }

    public static void removeSystem(String systemId) {
        String key = SYSTEM_PREFIX + SEP + systemId;
        MemCachedUtil.removeByKey(key);
    }

    public static List<KnowledgeVO> getTopSearchKnow() {
        String key = CACHE_KEY_ + SEP + "getTopSearchKnow";
        List<KnowledgeVO> result = MemCachedUtil.get(key);
        if(result == null) {
            CommonService commonService = ServiceFactory.getCommonService();
            result = commonService.getTopSearchKnow(5);
            MemCachedUtil.set(key, result);
        }
        return result;
    }
    
    //热点问题
    public static List<KnowledgeData> getTopKnowl() {
        String key = CACHE_KEY_ + SEP + "getTopKnowl";
        List<KnowledgeData> result = MemCachedUtil.get(key);
        if(result == null) {
            CommonService commonService = ServiceFactory.getCommonService();
            result = commonService.getTopKnowl(5);
            MemCachedUtil.set(key, result);
        }
        return result;
    }
    
    //当前时间处于开放时期的系统
    public static List<SystemOpenTimeData> getUnderwaySystem() {
        String key = CACHE_KEY_ + SEP + "getUnderwaySystem";
        List<SystemOpenTimeData> underwaySystems = MemCachedUtil.get(key);
        if(underwaySystems==null) {
            SystemService systemService = ServiceFactory.getSystemService();
            underwaySystems = systemService.getSystemId();
            MemCachedUtil.set(key, underwaySystems);
        }
        return underwaySystems;
    }
    
    public static void removeUnderwaySystem() {
        String key = CACHE_KEY_ + SEP + "getUnderwaySystem";
        MemCachedUtil.removeByKey(key);
    }
}
