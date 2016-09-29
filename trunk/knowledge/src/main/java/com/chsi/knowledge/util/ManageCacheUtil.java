package com.chsi.knowledge.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.chsi.knowledge.Constants;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.dic.KnowledgeType;
import com.chsi.knowledge.pojo.KnowTagRelationData;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.RobotASetData;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.pojo.SystemOpenTimeData;
import com.chsi.knowledge.pojo.TagData;
import com.chsi.knowledge.service.CommonService;
import com.chsi.knowledge.service.KnowTagRelationService;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.RobotService;
import com.chsi.knowledge.service.ServiceFactory;
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.service.TagService;
import com.chsi.knowledge.vo.KnowledgeVO;
import com.chsi.knowledge.vo.ViewKnowsVO;

/**
 * 缓存管理工具
 * 
 * @author chenjian
 */
public class ManageCacheUtil {
    private static final String SEP = ".";
    private static final String CACHE_KEY_ = ManageCacheUtil.class.getName() + SEP;
    
    
    public static KnowledgeData getKnowledgeDataById(String id) {
        String key = CACHE_KEY_ + "getKnowledgeDataById" + id;
        KnowledgeData data = MemCachedUtil.get(key);
        if(data==null) {
            KnowledgeService knowledgeService = ServiceFactory.getKnowledgeService();
            data = knowledgeService.getKnowledgeWithArticleById(id);
            if(data!=null) {
                MemCachedUtil.set(key, data);
            }
        }
        return data;
    }
    
    public static void removeKnowledgeDataById(String id) {
        String key = CACHE_KEY_ + "getKnowledgeDataById" + id;
        MemCachedUtil.removeByKey(key);
    }

    public static List<KnowTagRelationData> getKnowTag(String tagId) {
        String key = CACHE_KEY_ + "getKnowTag"+tagId;
        List<KnowTagRelationData> list = MemCachedUtil.get(key);
        if(list==null) {
            KnowledgeService knowledgeService = ServiceFactory.getKnowledgeService();
            list = knowledgeService.getKnowTagDatas(tagId, KnowledgeStatus.YSH, KnowledgeType.PUBLIC);
            MemCachedUtil.set(key, list);
        }
        return list;
    }
    
    public static void removeKnowTag(String tagId) {
        String key = CACHE_KEY_ + "getKnowTag"+tagId;
        MemCachedUtil.removeByKey(key);
    }
    
    public static List<KnowTagRelationData> getKnowTagRelationByKnowId(String knowId) {
        String key = CACHE_KEY_ + "getKnowTagRelationByKnowId"+knowId;
        List<KnowTagRelationData> list = MemCachedUtil.get(key);
        if(list==null) {
            KnowTagRelationService knowTagRelationService = ServiceFactory.getKnowTagRelationService();
            list = knowTagRelationService.getKnowTagRelationByKnowId(knowId);
            MemCachedUtil.set(key, list);
        }
        return list;
    }

    public static List<TagData> getTagList(String systemId) {
        String key = CACHE_KEY_ + "getTagList" + systemId;
        List<TagData> list = MemCachedUtil.get(key);
        if(list==null) {
            TagService tagService = ServiceFactory.getTagService();
            list = tagService.get(systemId);
            MemCachedUtil.set(key, list);
        }
        return list;
    }

    public static void removeTagList(String systemId) {
        String key = CACHE_KEY_ + "getTagList" + systemId;
        MemCachedUtil.removeByKey(key);
    }

    public static SystemData getSystem(String systemId) {
        String key = CACHE_KEY_ + "getSystem" + systemId;
        SystemData data = MemCachedUtil.get(key);
        if(data==null) {
            SystemService systemService = ServiceFactory.getSystemService();
            data = systemService.getSystemById(systemId);
            if(data!=null) {
                MemCachedUtil.set(key, data);
            }
        }
        return data;
    }

    public static void removeSystem(String systemId) {
        String key = CACHE_KEY_ + "getSystem" + systemId;
        MemCachedUtil.removeByKey(key);
    }

    public static List<KnowledgeData> getTopSearchKnow() {
        String key = CACHE_KEY_ + "getTopSearchKnow";
        List<KnowledgeData> result = MemCachedUtil.get(key);
        if(result == null) {
            CommonService commonService = ServiceFactory.getCommonService();
            result = commonService.getTopSearchKnow(5);
            MemCachedUtil.set(key, result);
        }
        return result;
    }
    
    //首页的热点问题
    public static List<KnowledgeData> getIndexTopKnowl(int cnt) {
        String key = CACHE_KEY_ + "getIndexTopKnowl";
        List<KnowledgeData> result = MemCachedUtil.get(key);
        if(result == null) {
            CommonService commonService = ServiceFactory.getCommonService();
            result = commonService.getTopKnowl(cnt);
            MemCachedUtil.set(key, result);
        }
        return result;
    }
    
    //首页点击查看更多显示各个系统的热点问题
    public static Map<SystemData, List<KnowledgeData>> getCatalogTopKnowl(int cnt) {
        String key = CACHE_KEY_ + "getCatalogTopKnowl";
        Map<SystemData, List<KnowledgeData>> result = MemCachedUtil.get(key);
        if(result == null) {
            result = new LinkedHashMap<SystemData, List<KnowledgeData>>();
            CommonService commonService = ServiceFactory.getCommonService();
            SystemService systemService = ServiceFactory.getSystemService();
            List<SystemOpenTimeData> openSystems = systemService.getOpenSystems();
            for(SystemOpenTimeData data:openSystems) {
                List<KnowledgeData> list = commonService.getTopKnowlBySystem(data, cnt);
                if(list!=null && list.size()>0) {
                    SystemData systemData = getSystem(data.getSystemId());
                    result.put(systemData, list);
                }
            }
            
            List<SystemData> systems = systemService.getSystems();
            systems.removeAll(result.keySet());
            SystemOpenTimeData openData = new SystemOpenTimeData();
            for(SystemData data:systems) {
                openData.setSystemId(data.getId());//模拟SystemOpenTimeData
                List<KnowledgeData> list = commonService.getTopKnowlBySystem(openData, cnt);
                if(list!=null && list.size()>0) {
                    result.put(data, list);
                }
            }
            MemCachedUtil.set(key, result);
        }
        return result;
    }
    
    //当前时间处于开放时期的系统
    public static List<SystemOpenTimeData> getUnderwaySystem() {
        String key = CACHE_KEY_ + "getUnderwaySystem";
        List<SystemOpenTimeData> underwaySystems = MemCachedUtil.get(key);
        if(underwaySystems==null) {
            SystemService systemService = ServiceFactory.getSystemService();
            underwaySystems = systemService.getOpenSystems();
            MemCachedUtil.set(key, underwaySystems);
        }
        return underwaySystems;
    }
    
    public static void removeUnderwaySystem() {
        String key = CACHE_KEY_ + "getUnderwaySystem";
        MemCachedUtil.removeByKey(key);
    }
    
    //特殊回答配置，如：见面招呼语“#noanswer”、未找到答案时的回答“#hello”
    public static String getRobotABySpecialQ(String q) {
        String key = CACHE_KEY_ + "getRobotASetByQ" + q;
        List<RobotASetData> result = MemCachedUtil.get(key);
        if(result==null) {
            RobotService robotService = ServiceFactory.getRobotService();
            result = robotService.getAByExplicitQ(q);
            MemCachedUtil.set(key, result);
        }
        int randomIndex = (int)(Math.random()*result.size());
        String content = result.get(randomIndex).getA();
        return content;
    }
    
    public static void removeRobotABySpecialQ(String q) {
        String key = CACHE_KEY_ + "getRobotASetByQ" + q;
        MemCachedUtil.removeByKey(key);
    }
    
    //查询某系统下的所有开放知识
    public static List<KnowledgeVO> getKnowsBySystem(String systemId) {
        String key = CACHE_KEY_ + "getKnowsBySystem" + systemId;
        List<KnowledgeVO> result = MemCachedUtil.get(key);
        if(result==null) {
            KnowledgeService knowledgeService = ServiceFactory.getKnowledgeService();
            result = ConvertUtil.know2KnowledgeVO(knowledgeService.get(systemId, KnowledgeStatus.YSH, KnowledgeType.PUBLIC));
            MemCachedUtil.set(key, result);
        }
        return result;
    }
}
