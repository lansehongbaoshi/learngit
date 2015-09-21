<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<script>
$(function(){
    
})
</script>
<ul id="systemIds">
<s:iterator value="systemDatas" id="data">
<li>
<s:property value="#data.id"/>
<s:property value="#data.name"/>
<s:property value="#data.description"/>
<a href="/htgl/system/updateSystemIndex.action?id=<s:property value="#data.id"/>">修改</a>
</li>
</s:iterator>
</ul>
<a href="/htgl/addSystemIndex.action">新增</a>