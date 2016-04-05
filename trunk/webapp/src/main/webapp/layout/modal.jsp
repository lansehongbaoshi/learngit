<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title><tiles:getAsString name="title" /></title>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common.css"/>
<link rel="stylesheet" type="text/css" href="http://t4.chsi.com.cn/common/jqueryui/1.11.2/themes/cupertino/jquery-ui.min.css" />
<script type="text/javascript" src="http://t4.chsi.com.cn/common/jquery/1.11.1/jquery.min.js"></script>
</head>
<body>
	<div class="content">
	    <tiles:insertAttribute name="content" />
	</div>
</body>
</html>