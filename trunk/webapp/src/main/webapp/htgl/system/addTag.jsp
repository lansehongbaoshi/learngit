<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<form action="/htgl/system/updateTag.action" method="post">
<input type="hidden" class="saveId" name="id" value="<s:property value='id'/>"/>
<table>
<s:iterator value="tagDatas" id="tag">
<tr><td>
    <input type="checkbox" name="tag" <s:if test="#tag.flag">checked</s:if> value="<s:property value='#tag.id'/>"/><s:property value="#tag.name"/> &nbsp;&nbsp;&nbsp;&nbsp;
</td></tr>
</s:iterator>
</table>
</form>