<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<script>
$(function(){
})
</script>
<form name="" action="/htgl/system/updateSystem.action" method="get">
ID：<input type="text" name="id" value="<s:property value="systemData.id" />">
名称：<input type="text" name="name" value="<s:property value="systemData.name" />">
描述：<input type="text" name="description" value="<s:property value="systemData.description" />">
<input type="submit" value="修改">
</form>