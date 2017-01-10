package com.chsi.knowledge.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.chsi.contact.client.ContactServiceClient;
import com.chsi.contact.client.ContactServiceClientFactory;
import com.chsi.contact.constant.client.ContactConstants;
import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.vo.KnowListVO;
import com.chsi.knowledge.vo.SearchVO;
import com.chsi.search.client.vo.KnowledgeVO;

/**
 * 搜索功能辅助类
 * 
 * @author chenjian
 * 
 */
public class SearchUtil {

    /**
     * 关键字中特殊字符过滤
     * 
     * @param keywords
     * @return
     */
    public static String keywordsFilter(String keywords) {
        String regex = "^如何|怎样|怎么|什么|为什么|我|关于|了|吗|呢|啊|嘛|呗|喽|呀|哟|啦$|[ `~!@#$^()=|'+-:;,\\%.<>/?￥…&*（）—【】‘；：”“'。，、\t？]";
        String goodKeywords = "";
        if (keywords != null) {
            goodKeywords = keywords.replaceAll(regex, "");
        }
        return goodKeywords;
    }

    /**
     * 机器人自动完成过滤关键字中特殊字符
     * 
     * @param keywords
     * @return
     */
    public static String keywordsFilter2(String keywords) {
        String regex = "[了|吗|呢|啊|嘛|呗|喽|呀|哟|啦]|[ `~!@#$^()=|'+-:;,\\%.<>/?￥…&*（）—【】‘；：”“'。，、\t？]";
        String goodKeywords = "";
        if (keywords != null) {
            goodKeywords = keywords.replaceAll(regex, "");
        }
        return goodKeywords;
    }

    /**
     * 过滤搜索结果中内容的html标签
     * 
     * @param content
     * @return
     */
    public static String resultFilter(String content) {
        if (content != null) {
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
        return "";
    }

    /**
     * 是否是规范的关键词搜索
     * 
     * @param keyword
     * @return
     */
    public static boolean isGoodKeyword(String keyword) {
        String regex = "^[A-Za-z0-9]+|如何|怎样|什么|为什么|我|关于$";
        if (keyword != null) {
            String result = keyword.replaceAll(regex, "");
            if (ValidatorUtil.isNull(result)) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 数据格式转换,截断知识保证不超过一定字符数
     * 
     * @param listVO
     * @param searchWords
     * @param length
     *            截断长度
     * @return
     */
    public static List<SearchVO> exchangeResultList(KnowListVO<KnowledgeVO> listVO, String searchWords, int length) {
        if (null == listVO || listVO.getKnows() == null || listVO.getKnows().size() == 0) {
            return new ArrayList<SearchVO>();
        }

        List<SearchVO> searchList = new ArrayList<SearchVO>();
        SearchVO tempVO = null;
        String summary = null;
        for (KnowledgeVO vo : listVO.getKnows()) {
            String htmlContent = vo.getContent();

            String txtContent = resultFilter(htmlContent);
            if (txtContent.length() > length) {
                summary = txtContent.substring(0, length) + "...";
            } else {
                summary = txtContent;
            }

            boolean hasImage = hasImgTag(htmlContent);
            KnowledgeData data = ManageCacheUtil.getKnowledgeDataById(vo.getKnowledgeId());
            if (data != null) {
                // ContactServiceClient contactService =
                // ContactServiceClientFactory.getContactServiceClient();
                // String userName =
                // contactService.getRealInfoSingleItemValue(data.getCreater(),
                // ContactConstants.ITEM_NAME_ID);
                // if("".equals(userName)||null==userName){
                // userName = "未注册";
                // }
                tempVO = new SearchVO(data.getSystemDatas(), vo.getTags(), vo.getTitle(), summary, txtContent, vo.getKnowledgeId(), vo.getTagIds(), searchWords, data.getVisitCnt(), data.getSort(), data.getType(), data.getTopTime() == null ? -1 : data.getTopTime().getTimeInMillis(), hasImage, (data.getUpdateTime() == null ? data.getCreateTime() : data.getUpdateTime()), data.getCreaterName(), data.getCreateTime(), data.getUpdaterName(), data.getUpdateTime());
                searchList.add(tempVO);
            }
        }
        return searchList;
    }

    public static String formatFullMatchKeyword(String keyword) {
        String result = "";
        if (!ValidatorUtil.isNull(keyword)) {
            String formatKeyword = keyword.replaceAll("\"", "\\\\\"");
            result = String.format("\"%s\"", formatKeyword);
        }
        return result;
    }

    public static boolean hasImgTag(String html) {
        if (ValidatorUtil.isNull(html))
            return false;
        Pattern p = Pattern.compile("<\\s*img\\s+([^>]*)\\s*>");
        Matcher m = p.matcher(html);
        boolean rs = m.find();
        return rs;
    }

}
