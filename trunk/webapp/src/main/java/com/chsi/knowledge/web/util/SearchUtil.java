package com.chsi.knowledge.web.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.vo.KnowListVO;
import com.chsi.knowledge.vo.SearchVO;
import com.chsi.search.client.vo.KnowledgeVO;

/**
 * 搜索功能辅助类
 * @author chenjian
 *
 */
public class SearchUtil {

    /**
     * 关键字中特殊字符过滤
     * @param keywords
     * @return
     */
    public static String keywordsFilter(String keywords) {
        String filterWords = "[`~!@#$^()=|'+-:;,\\%.<>/?￥…&*（）—【】‘；：”“'。，、？]";
        StringBuffer result = new StringBuffer();
        if (ValidatorUtil.isNull(keywords)) {
            return null;
        }
        for (int i = 0; i < keywords.length(); i++) {
            if (filterWords.indexOf(keywords.charAt(i)) == -1) {
                result.append(keywords.charAt(i));
            }
        }
        return result.toString();
    }
    
    /**
     * 过滤搜索结果中内容的html标签
     * @param content
     * @return
     */
    public static String resultFilter(String content) {
        String regxpForHtml = "<([^>]*)>";
        Pattern pattern = Pattern.compile(regxpForHtml);
        Matcher matcher = pattern.matcher(content); 
        StringBuffer sb = new StringBuffer();
        boolean result1 = matcher.find();
        while (result1) {
            matcher.appendReplacement(sb, "");
            result1 = matcher.find();
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    
    /**
     * 数据格式转换
     * @param listVO
     * @param searchWords
     * @return
     */
    public static List<SearchVO> exchangeResultList(KnowListVO<KnowledgeVO> listVO, String searchWords, int length)
    {
        if(null== listVO || listVO.getKnows() == null || listVO.getKnows().size()==0){
            return new ArrayList<SearchVO>();
        }
        
        List<SearchVO> searchList= new ArrayList<SearchVO>();
        SearchVO tempVO = null;
        String con = null;
        for(KnowledgeVO vo : listVO.getKnows()){
            vo.setContent(resultFilter(vo.getContent()));
            length = vo.getContent().length() < length ? vo.getContent().length() : length;
            con = vo.getContent().substring(0, length);
            tempVO = new SearchVO(vo.getTitle(), con, vo.getKnowledgeId(), vo.getTagIds(), searchWords);
            searchList.add(tempVO);
        }
        return searchList;
    }
    
}
