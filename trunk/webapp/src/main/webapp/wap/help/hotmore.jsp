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
    <meta name="Keywords" content="学信网帮助中心，学信网常见问题">
    <meta name="Description" content="学信网帮助中心，学信网账号、学信档案、应征报名、四六级查分、研招统考、研招推免、学历与成绩认证、面向港澳台招生、高考统考招生、高考特殊类型招生、高考特殊类型报名">
  </head>
  <body>
    <img src='http://kl.chsi.com.cn/images/wap/wx_share.jpg' width='0' height='0' />
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
