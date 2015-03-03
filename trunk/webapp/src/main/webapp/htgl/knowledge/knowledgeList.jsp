<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String ctxPath = request.getContextPath();
%> 
<a href="<%=ctxPath%>/htgl/tag/getTagList?systemId=<s:property  value="#request.systemId" />" >返回主页面</a>
<table cellpadding="5" cellspacing="1" border="1">
  <tr>
    <td>问题</td>
  </tr>
    <s:if test="page!=null">
        <s:iterator value="page.list" var="vo">
           <tr>
           <td><a href="<%=ctxPath%>/htgl/knowledge/getKnowledge.action?id=<s:property value="#vo.knowledgeData.id" />"><s:property value="#vo.article.title" /></a></td>
           </tr>
        </s:iterator>
    </s:if>
</table>
<jsp:include page="/common/pageNavigator.jsp" flush="true"></jsp:include>
 