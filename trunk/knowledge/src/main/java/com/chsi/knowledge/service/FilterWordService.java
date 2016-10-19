package com.chsi.knowledge.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

public interface FilterWordService {

    Set<String> getBadWords(String text) throws UnsupportedEncodingException;

    Object[] highlightBadWords(String text) throws UnsupportedEncodingException;

    List<String> getBadSentences(String keywords) throws UnsupportedEncodingException;

}
