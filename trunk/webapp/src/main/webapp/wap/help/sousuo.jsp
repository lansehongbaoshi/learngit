<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" 
import="com.chsi.knowledge.pojo.KnowledgeData,com.chsi.knowledge.util.ManageCacheUtil,java.util.List,com.chsi.search.client.vo.KnowledgeVO"%>
<%List<KnowledgeVO> knows = ManageCacheUtil.getTopSearchKnow(); %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta charset="utf-8">
    <title>搜索</title>
    <link href="../../css/wap/help/reset.css" rel="stylesheet"/>
    <link href="../../css/wap/help/style.css" rel="stylesheet"/>
    <!--[if lt IE 9]>
        <script src="../../js/wap/respond.js"></script>
      <![endif]-->
      <script type="text/javascript" src="http://t4.chsi.com.cn/common/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript">
$(function(){
  $("#cancel").on("click", function(){
	 window.history.back();
  })
})
</script>
  </head>
  <body>
    <div id="wrap">
      <div class="section section_search">
        <div class="search_text">
          <div class="cancel"><a href="javascript:void(0)" id="cancel">取消</a></div>
          <div class="search_form">
            <input type="text"/>
            <span class="clearr">×</span>
            <button>搜索</button>
          </div>
        </div>
        <ul class="hot_search_questions">
        <%if(knows!=null){ 
           for(KnowledgeVO vo:knows){%>
           <li><a href="/wap/help/ckjjfa.jsp?id=<%=vo.getKnowledgeId() %>"><%=vo.getTitle() %></a></li>
           <%} %>
        <%} %>
        </ul>
      </div>
    </div>
  </body>
</html>
