package com.chsi.knowledge.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.chsi.framework.service.BaseDbService;
import com.chsi.framework.util.StringUtil;
import com.chsi.gxjx.tool.WordsFilter;
import com.chsi.knowledge.service.FilterWordService;
import com.chsi.knowledge.util.SearchUtil;
import com.sun.jndi.toolkit.dir.SearchFilter;

public class FilterWordServiceImpl extends BaseDbService implements
        FilterWordService {

    @Override
    protected void doCreate() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void doRemove() {
        // TODO Auto-generated method stub

    }

    @Override
    public Set<String> getBadWords(String text) throws UnsupportedEncodingException {
        // TODO Auto-generated method stub
        return WordsFilter.getInstance().getBadWords(text);
    }

    @Override
    public Object[] highlightBadWords(String text) throws UnsupportedEncodingException {
        // TODO Auto-generated method stub
        return WordsFilter.getInstance().highlightBadWords(text);
    }

    @Override
    public List<String> getBadSentences(String keywords)
            throws UnsupportedEncodingException {
        // TODO Auto-generated method stub
        keywords = SearchUtil.resultFilter(keywords);
        List<String> sentences = splitContent(keywords);
        List<String> result = new ArrayList<String>();
        WordsFilter wordsFilter = WordsFilter.getInstance();
        for(String sentence : sentences){
            Object[] obj = wordsFilter.highlightBadWords(sentence);
            if((Boolean) obj[0]){
                result.add((String) obj[1]);
            }
        }
        return result;
    }
    
    public List<String> splitContent(String content){
        String regex = "[`~!！\"\':;,.?…‘’；：”“'。，、？]";
        List<String> sentences = null ;
        List<String> result = new ArrayList<String>() ;
        if(content!=null) {
            sentences = Arrays.asList(content.split(regex));
            int index=0;
            for(String sentence : sentences){
                index += sentence.length();
                result.add(sentence+String.valueOf(content.charAt(index++)));
            }
        }
        
        return result;
        
    }

}
