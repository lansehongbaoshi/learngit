<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ page import="com.chsi.knowledge.Constants"%>
<%
request.setAttribute(Constants.REQUEST_ERROR, "系统出现空调用异常，请通知中心相关人员！");
request.getRequestDispatcher(Constants.ERRORURL).forward(request, response);
%>