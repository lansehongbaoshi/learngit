<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.chsi.knowledge.pojo.*,com.chsi.knowledge.service.*,com.chsi.knowledge.util.ManageCacheUtil,java.util.*" %>
<%@ page import="com.chsi.news.vo.Article" %>
<%@ page import="com.chsi.cms.client.*" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%

Map<SystemData, List<KnowledgeData>> result = ManageCacheUtil.getCatalogTopKnowl(10);
String id = request.getParameter("id");
SystemData theSystemData = null;
if(id!=null) {
    theSystemData = ManageCacheUtil.getSystem(id);
}
%>
<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta charset="utf-8">
    <title>热门问题_学信网帮助中心</title>
  </head>
  <body>
    <div id="wrap">
      <div class="section">
        <s:include value="searchbox.jsp"></s:include>
        <h2 class='hot_title'>热门问题列表</h2> 
        <%
            for(SystemData systemData:result.keySet()) {
                if(theSystemData!=null&&!theSystemData.equals(systemData)) continue;
        %>        
        <div class="question_part">
          <h2><%=systemData.getName() %></h2>
          <ul>
          <%
          List<KnowledgeData> list = result.get(systemData);
          for(int i=0;i<list.size();i++){
              KnowledgeData knowledgeData = list.get(i);
          %>
            <li>
            	<a href="/wap/help/ckjjfa.jsp?id=<%=knowledgeData.getId() %>">
            		<div>
	            		<span><img src="http://t1.chei.com.cn/common/wap/help/images/more.png"/></span>
	            		<i><%=knowledgeData.getArticle().getTitle() %></i>
            		</div>
            	</a>
            </li>
          <%} %>
          </ul>
        </div>
        <%} %>
      </div>
    </div>
  </body>
</html>
