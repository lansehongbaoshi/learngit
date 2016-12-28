<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="">
    <p style="padding:20px;">欢迎进入学信网知识库管理系统!</p>
    <div class="">
      <div class="col-md-6"><jsp:include page="/admin/statistics/badknowledgerank.jsp"/></div>
      <div class="col-md-6"><jsp:include page="/admin/statistics/knowledgestatistics.jsp"/></div>      
   </div>
   <div class="">
      <div class="col-md-6"><jsp:include page="/admin/statistics/knowledgeDSHCount.jsp"/></div>
   </div>
</div>