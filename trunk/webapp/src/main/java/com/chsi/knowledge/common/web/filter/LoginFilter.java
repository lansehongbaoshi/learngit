package com.chsi.knowledge.common.web.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chsi.account.client.AccountServiceClient;
import com.chsi.account.client.AccountServiceClientFactory;
import com.chsi.account.client.UserAccountData;
import com.chsi.account.client.UserOrganizationData;
import com.chsi.framework.remote.RemoteCallRs;
import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.util.RemoteCallUtil;
import com.chsi.knowledge.vo.LoginUserVO;
import com.chsi.knowledge.web.util.WebAppUtil;

/**
 * 用户过滤器，现在只是每次输出用户名，后面可能加其他限制
 * 
 * @author chenjian
 */
public class LoginFilter implements Filter {
    private String[] exclusionsPaths = null;

    public void destroy() {
        // TODO Auto-generated method stub

    }

    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) arg0;
        HttpServletResponse response = (HttpServletResponse) arg1;
        String uri = request.getRequestURI();
        if (exclusionsPaths != null) {
            for (String path : exclusionsPaths) {
                if (uri.contains(path)) {
                    arg2.doFilter(arg0, arg1);
                    return;
                }
            }
        }
        LoginUserVO user = WebAppUtil.getLoginUserVO(request);
        if (null == user) {
            String userId = WebAppUtil.getUserId();
            System.out.println("userId:" + userId + "登录");

            AccountServiceClient accountService = AccountServiceClientFactory.getAccountServiceClient();
            RemoteCallRs<UserAccountData> acc = accountService.getAccountById(userId);
            RemoteCallRs<UserOrganizationData> org = accountService.getUserOrganizationByUserId(userId);
            // TODO 权限控制
            if (acc != null && acc.getValue() != null && org != null && org.getValue() != null) {
                List<String> auths = RemoteCallUtil.getAuthsByUserId(userId);
                LoginUserVO vo = new LoginUserVO(acc.getValue(), org.getValue(), auths);
                WebAppUtil.setLoginUserVO(request, vo);
            } else {
                response.sendRedirect(request.getContextPath() + "/error/c403.jsp");
                return;
            }
            // 验证用户信息
        }
        arg2.doFilter(arg0, arg1);
    }

    public void init(FilterConfig config) throws ServletException {
        try {
            String exclusions = config.getInitParameter("exclusions");
            if (!ValidatorUtil.isNull(exclusions)) {
                exclusionsPaths = exclusions.split(",");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
