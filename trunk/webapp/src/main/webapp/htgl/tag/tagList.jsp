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
           <td><s:property value="#vo.count" />
           <input type="hidden" name="systemId" value="<s:property value='#vo.tagData.systemData.id' />">
           <input type="hidden" name="tagId" value="<s:property value='#vo.tagData.id'/>">
           <input type="button" onClick="_ajax(this)" >
           </td>
           </tr>
		</s:iterator>
	</s:if>

</table>
<script type="text/javascript">



function _ajax(obj){
var _url = "<%=ctxPath%>/htgl/knowledge/getKnowledgeList.action";
var _data = {};
_data.systemId=$(obj).parent().find("input[name=systemId]").val(); 
_data.tagId=$(obj).parent().find("input[name=tagId]").val(); 

var sss= _url+"?systemId="+_data.systemId+"&tagId="+_data.tagId+"&callback=?"
	
 $.getJSON( sss , function(data){
	alert(data.o.totalCount);
		}) 
 }
  
</script>