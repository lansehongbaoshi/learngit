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
            log.info(String.format("===================userId:%s,result:%s", userId, i[0]));
        }
    }
    /*收回用户-权限*/
    private void delAuthentication(String Authority, String userId) {
        List<AuthVo> list = authorityServiceClient.getGrantedAuth(userId).getValue();
        List<String> authorityIds = new ArrayList<String>();
        for(AuthVo auth:list){
            if(auth.getAuthority().equals(Authority)) {
                authorityIds.add(auth.getId());
            }
        }
        Integer[] i = authorityServiceClient.revokeAuthorities(userId, authorityIds).getValue();
        log.info(String.format("===================userId:%s,result:%s", userId, i[0]));
    }
%>
<%
    log.info("====================================================开始====================================================");
    String aLoginName = request.getParameter("loginName");
    String type = request.getParameter("type");
    String role = request.getParameter("role");
    log.info(String.format("===================开始accountLoginName:%s,type:%s", aLoginName,type));
    try {
        String userId = accountServiceClient.getAccountByLoginname(aLoginName.trim()).getValue().getId();

        if (userId != null && !"".equals(userId)) {
            if("yes".equals(type)) {
                createAuthentication(role, userId);
            }else{
                delAuthentication(role,userId);
            }
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    log.info("====================================================结束====================================================");
%>
