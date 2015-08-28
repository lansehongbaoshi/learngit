<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
String ctxPath = request.getContextPath();
%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!doctype html>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>管理知识库</title>
</head>
<body>
         <form action="<%=ctxPath %>/manage/upload.action" method="post" enctype="multipart/form-data"  >
         id：<input type="text" name="id">
         名称：<input type="text" name="name">
         描述：<input type="text" name="desc">
                <input type="file"   name="upload" id="upload"/>
                <input type="submit"  value="上传"  />
        </form>
<form action="<%=ctxPath %>/manage/refreshIndex.action" method="post">
SystemId：<input type="text" name="systemId" >
<input type="submit" value="刷索引">
</form>
<form action="<%=ctxPath %>/manage/deleteIndex.action" method="post">
SystemId：<input type="text" name="systemId" >
<input type="submit" value="删索引">
</form>
<form action="<%=ctxPath %>/manage/delete.action" method="post">
SystemId：<input type="text" name="systemId" >
<input type="submit" value="删新闻系统里的数据">
</form>
<form action="<%=ctxPath %>/manage/updateStatus.action" method="post">
SystemId：<input type="text" name="systemId" >
<input type="submit" value="更新审核状态（包括本系统和新闻系统）">
</form>

</body>
</html>