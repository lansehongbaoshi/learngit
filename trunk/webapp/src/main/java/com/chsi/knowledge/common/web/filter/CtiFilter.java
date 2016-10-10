package com.chsi.knowledge.common.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chsi.knowledge.util.RemoteCallUtil;
import com.chsi.knowledge.vo.LoginUserVO;
import com.chsi.knowledge.web.util.WebAppUtil;
/**
 * 用户过滤器，判断是否是客服普通用户
 * @author zhangzh
 */
public class CtiFilter  implements Filter {

    public void destroy() {
        // TODO Auto-generated method stub
        
    }

    public void doFilter(ServletRequest arg0, ServletResponse arg1,
            FilterChain arg2) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)arg0;
        HttpServletResponse response = (HttpServletResponse)arg1;
        LoginUserVO user = WebAppUtil.getLoginUserVO(request);
        if (null == user) {
            String userIp = WebAppUtil.getUserIp();
            System.out.println("userIp:" + userIp + "登录");
            
            //TODO 权限控制
            if(RemoteCallUtil.getAllowIp().equals(userIp)) {
                LoginUserVO vo = new LoginUserVO();
                WebAppUtil.setLoginUserVO(request, vo);
            } else {
                response.sendRedirect(request.getContextPath() + "/error/c403.jsp");
                return;
            }
            //  验证用户信息
        }
        arg2.doFilter(arg0, arg1);
    }

    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
        
    }

}
