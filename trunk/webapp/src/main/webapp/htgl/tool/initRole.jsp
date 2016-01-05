<%--
  Created by IntelliJ IDEA.
  User: Li_Jian
  Date: 2015/10/27
  Time: 9:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ page import="com.chsi.account.client.AccountServiceClient" %>
<%@ page import="com.chsi.account.client.AccountServiceClientFactory" %>
<%@ page import="com.chsi.account.client.PersonalInfoData" %>
<%@ page import="com.chsi.account.client.support.GrantAuthority" %>
<%@ page import="com.chsi.security.client.AuthorityServiceClient" %>
<%@ page import="com.chsi.security.client.AuthorityServiceClientFactory" %>
<%@ page import="com.chsi.security.client.vo.AuthVo" %>
<%@ page import="com.chsi.security.client.vo.GrantedAuthorityVo" %>
<%@ page import="org.apache.commons.logging.Log" %>
<%@ page import="org.apache.commons.logging.LogFactory" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%!
    AuthorityServiceClient authorityServiceClient = AuthorityServiceClientFactory.getAuthorityServiceClient();
    AccountServiceClient accountServiceClient = AccountServiceClientFactory.getAccountServiceClient();
    Log log = LogFactory.getLog("initUser");

    /*创建用户-权限，保证不重复*/
    private void createAuthentication(String Authority, String userId) {
        GrantedAuthorityVo vo = authorityServiceClient.getAuthorityByName(Authority).getValue();

        List<AuthVo> list = authorityServiceClient.getGrantedAuth(userId).getValue();
        List<GrantAuthority> gaList = new ArrayList<GrantAuthority>();

        GrantAuthority grantAuthority = new GrantAuthority();
        grantAuthority.setAuthority(vo.getName());
        grantAuthority.setAssignable(true);
        gaList.add(grantAuthority);

        boolean flag = true;
        for (AuthVo v : list) {
            if (v.getId().equals(vo.getId())) {
                flag = false;
                break;
            }
        }
        if (flag) {
            Integer[] i = authorityServiceClient.grantAuthorities(userId, gaList).getValue();
            log.info(String.format("===================开始result:%s", i[0]));
        }
    }
%>
<%
    log.info("====================================================开始====================================================");
    String aLoginName = request.getParameter("loginName");
    log.info(String.format("===================开始accountLoginName:%s", aLoginName));
    try {
        String userId = accountServiceClient.getAccountByLoginname(aLoginName.trim()).getValue().getId();
        log.info(String.format("===================开始userId:%s", userId));

        if (userId != null && !"".equals(userId)) {
            createAuthentication("ROLE_KNOWLEDGE", userId);
            out.print("创建了");
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    log.info("====================================================结束====================================================");
%>
