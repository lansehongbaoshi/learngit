<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ page import="com.chsi.knowledge.Constants"%>
<%
request.setAttribute(Constants.REQUEST_ERROR, "系统访问出现异常！");
request.getRequestDispatcher(Constants.ERRORURL).forward(request, response);
%>