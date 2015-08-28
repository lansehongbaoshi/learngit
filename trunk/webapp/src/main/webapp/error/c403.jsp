<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ page import="com.chsi.knowledge.Constants"%>
<%
response.setStatus(HttpServletResponse.SC_FORBIDDEN);
request.setAttribute(Constants.REQUEST_ERROR, "您不具有当前操作的权限！");
request.getRequestDispatcher(Constants.ERRORURL).forward(request, response);
%>