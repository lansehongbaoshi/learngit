<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String ctxPath = request.getContextPath();
%> 
<table cellpadding="5" cellspacing="1" border="1">
  <tr>
    <td>标签名称</td>
    <td>知识总数</td>
  </tr>
	<s:if test="tagVO!=null">
		<s:iterator value="tagVO" var="vo">
           <tr>
           <td><a href="<%=ctxPath%>/htgl/knowledge/getKnowledgeList.action?systemId=<s:property value="#vo.tagData.systemData.id" />&tagId=<s:property value="#vo.tagData.id" /> ">
           <s:property value="#vo.tagData.name" /></a></td>
           <td><s:property value="#vo.count" /></td>
           </tr>
		</s:iterator>
	</s:if>

</table>
 