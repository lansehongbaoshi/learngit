package com.chsi.knowledge.service;

import java.util.List;

import com.chsi.framework.pojos.PersistentObject;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.SystemOpenTimeData;
import com.chsi.knowledge.vo.LineChartVO;

public interface CommonService {
    void save(PersistentObject po);

    /**
     * 获取搜索频次最多的前n个关键词对应的知识
     * 
     * @param n
     * @return
     */
    List<KnowledgeData> getTopSearchKnow(int n);

    /**
     * 访问最多的前n个知识
     * 
     * @param n
     * @return
     */
    List<KnowledgeData> getTopKnowl(int n);

    /**
     * 清理重复的搜索日志
     */
    void removeDuplicatedDatas();

    /**
     * 清理垃圾关键词，比如全英文的关键词（都是自动完成功能记录的，比如打“年龄”时，会搜索nia、nian、nianl类似的）、特殊字符如“如何”等
     */
    void removeTrashKeywords();

    /**
     * 记录知识访问量日志
     */
    void recordVisitLog();

    /**
     * 获取某个系统或某个标签的最多访问知识
     * 
     * @param systemOpenTimeData
     * @param total
     * @return
     */
    List<KnowledgeData> getTopKnowlBySystem(SystemOpenTimeData systemOpenTimeData, int total);

    LineChartVO getTopKnowlVisitLog(String systemId, int topCnt, String startTime, String endTime);
}
