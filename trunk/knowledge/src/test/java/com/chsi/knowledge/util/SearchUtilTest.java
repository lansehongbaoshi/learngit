package com.chsi.knowledge.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class SearchUtilTest {
    @Test
    public void testTeywordsFilter() {
        String str = "【怎么撤销学历啊】?";
        String result = SearchUtil.keywordsFilter(str);
        assertEquals("撤销学历", result);
    }
}
