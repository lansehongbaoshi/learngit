<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String ctxPath = request.getContextPath();
%> 
<html>
<body>
问题:${viewKnowVO.conKnow.title}  <br>
关键字：${viewKnowVO.conKnow.keywords} <br>
答案：${viewKnowVO.conKnow.content} <br>


</body>
</html>

 