<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
com.chsi.knowledge.vo.LoginUserVO user = com.chsi.knowledge.web.util.WebAppUtil.getLoginUserVO(request);
String ctxPath = request.getContextPath();

if(user.getAuths().contains(com.chsi.knowledge.Constants.ROLE_CTI_USER)) {
    response.sendRedirect(ctxPath + "/admin/cti/index.action");
}else if(user.getAuths().contains(com.chsi.knowledge.Constants.ROLE_KNOWLEDGE_READONLY)) {
    response.sendRedirect(ctxPath + "/admin/query/searchindex.action");
}else{
    response.sendRedirect(ctxPath + "/admin/index.action");
}

%>
