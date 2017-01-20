package com.chsi.knowledge.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.chsi.framework.service.BaseDbService;
import com.chsi.gxjx.tool.WordsFilter;
import com.chsi.knowledge.service.FilterWordService;
import com.chsi.knowledge.util.SearchUtil;

public class FilterWordServiceImpl extends BaseDbService implements FilterWordService {
    private static final String HIGHLIGHT_FORMAT = "<span style=\"color:#F00;font-style:italic;\">%s</span>";

    @Override
    protected void doCreate() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void doRemove() {
        // TODO Auto-generated method stub

    }

    @Override
    public Set<String> getBadWords(String html) throws UnsupportedEncodingException {
        return WordsFilter.getInstance().getHtmlBadWords(html);
    }

    @Override
    public Object[] highlightBadWords(String text) throws UnsupportedEncodingException {
        return WordsFilter.getInstance().highlightBadWords(text, HIGHLIGHT_FORMAT);
    }

    @Override
    public List<String> getBadSentences(String keywords) throws UnsupportedEncodingException {
        keywords = SearchUtil.resultFilter(keywords);
        List<String> sentences = splitContent(keywords);
        List<String> result = new ArrayList<String>();
        WordsFilter wordsFilter = WordsFilter.getInstance();
        for (String sentence : sentences) {
            Object[] obj = wordsFilter.highlightBadWords(sentence, HIGHLIGHT_FORMAT);
            if ((Boolean) obj[0]) {
                result.add((String) obj[1]);
            }
        }
        return result;
    }

    public List<String> splitContent(String content) {
        String regex = "[`~!！\"\':;,.?…‘’；：”“'。，、？]";
        List<String> sentences = null;
        List<String> result = new ArrayList<String>();
        if (content != null) {
            sentences = Arrays.asList(content.split(regex));
            int index = 0;
            for (String sentence : sentences) {
                index += sentence.length();
                if (index < content.length()) {
                    result.add(sentence + String.valueOf(content.charAt(index++)));
                } else {
                    result.add(sentence);
                }
            }
        }

        return result;

    }

}
