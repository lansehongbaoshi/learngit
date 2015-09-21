<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<script>
$(function(){
})
</script>
<form name="" action="/htgl/tag/update.action" method="get">
<input type="hidden" name="id" value="<s:property value="tagData.id" />">
系统：<s:property value="tagData.systemData.name" />
名称：<input type="text" name="name" value="<s:property value="tagData.name" />">
描述：<input type="text" name="description" value="<s:property value="tagData.description" />">
热点度：<input type="text" name="sort" value="<s:property value="tagData.sort" />">（数字越大排序越往前）
<input type="submit" value="修改">
</form>