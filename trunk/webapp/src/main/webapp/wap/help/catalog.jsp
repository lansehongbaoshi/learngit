<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.chsi.knowledge.pojo.*,com.chsi.knowledge.service.*,com.chsi.knowledge.util.ManageCacheUtil,java.util.List" %>
<%@ page import="com.chsi.news.vo.Article" %>
<%@ page import="com.chsi.cms.client.*" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String id = request.getParameter("id");
SystemData systemData = ManageCacheUtil.getSystem(id);
List<TagData> tags = ManageCacheUtil.getTagList(id);
CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
int tagNum = 3;//显示几个标签
int knowNum = 2;//显示几个标签
%>
<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

    <meta charset="utf-8">
    <title><%=systemData.getName() %>_学信网帮助中心</title>
    <link href="../../css/wap/help/reset.css" rel="stylesheet"/>
    <link href="../../css/wap/help/style.css" rel="stylesheet"/>
    <!--[if lt IE 9]>
        <script src="../../js/wap/respond.js"></script>
      <![endif]-->
  </head>
  <body>
    <div id="wrap">
      <div class="section">
        <s:include value="searchbox.jsp"></s:include>
        <%for(int i=0;i<tags.size();i++){
            TagData tagData = tags.get(i);
            %>
        <div class="question_part">
          <h2><%=i+1 %>、<%=tagData.getName() %></h2>
          <ul>
          <%List<KnowTagRelationData> list = ManageCacheUtil.getKnowTag(tagData.getId());
          for(int j=0;j<list.size();j++){
              KnowTagRelationData one = list.get(j);
              String knowId = one.getKnowledgeData().getId();
              Article article = cmsServiceClient.getArticle(one.getKnowledgeData().getCmsId());
          %>
            <li <%if(j>=knowNum) out.print("style=\"display:none;\"");%>>
            	<a href="/wap/help/ckjjfa.jsp?id=<%=knowId %>">
            		<div>
	            		<span><img src="../../images/wap/help/more.png"/></span>
	            		<i><%=article.getTitle() %></i>
            		</div>
            	</a>
            </li>
            <%} %>
          </ul>
          <%if(list.size()>knowNum){ %>
          <div class="more_que">
            <span class="">查看更多<img src="../../images/wap/help/ckgd.png"/></span>
          </div>
          <%} %>
        </div>
        <%} %>
      </div>
    </div>
<script type="text/javascript">
$(function(){
	$(".more_que").on("click", function(){
		$question_part = $(this).closest(".question_part");
		$question_part.find("li").slideDown();
		$(this).remove();
	})
})
</script>
  </body>
</html>
