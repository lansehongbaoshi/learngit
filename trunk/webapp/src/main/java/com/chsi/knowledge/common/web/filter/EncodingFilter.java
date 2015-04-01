package com.chsi.knowledge.common.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/**
 * 编码过滤器，均为UTF-8格式
 * @author chenjian
 *
 */
public class EncodingFilter implements Filter{

    public void destroy() {
        
    }

    public void doFilter(ServletRequest arg0, ServletResponse arg1,
            FilterChain arg2) throws IOException, ServletException {
        arg0.setCharacterEncoding("UTF-8");
        arg2.doFilter(arg0, arg1);
    }

    public void init(FilterConfig arg0) throws ServletException {
        
    }

}
