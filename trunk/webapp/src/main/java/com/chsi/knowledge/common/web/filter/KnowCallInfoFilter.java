package com.chsi.knowledge.common.web.filter;

import javax.servlet.http.HttpServletRequest;

import com.chsi.account.client.filter.AccountFilter;

public class KnowCallInfoFilter extends AccountFilter {

    @Override
    public String getModuleId(HttpServletRequest arg0) {
        return null;
    }

    @Override
    public String getSystemId(HttpServletRequest arg0) {
        return "knowledge";
    }

}
