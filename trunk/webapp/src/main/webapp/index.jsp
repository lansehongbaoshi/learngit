<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
com.chsi.knowledge.vo.LoginUserVO user = com.chsi.knowledge.web.util.WebAppUtil.getLoginUserVO(request);
String ctxPath = request.getContextPath();

if(null==user || null==user.getAuths()){
    response.sendRedirect(ctxPath + "/htgl/index.action");
}else{
    if(user.getAuths().contains(com.chsi.knowledge.Constants.ROLE_CTI_USER)) {
        response.sendRedirect(ctxPath + "/cti/index.action");
    }else if(user.getAuths().contains(com.chsi.knowledge.Constants.ROLE_KNOWLEDGE_READONLY)) {
        response.sendRedirect(ctxPath + "/query/searchindex.action");
    }else{
        response.sendRedirect(ctxPath + "/htgl/index.action");
    }
}

%>
