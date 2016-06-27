<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%
String ctxPath = request.getContextPath();
%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!doctype html>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>控制台</title>
<script src="/assets/js/jquery.2.1.1.min.js"></script>
<script type="text/javascript">
$(function () {
$.getJSON("/htgl/system/listSystem.action",
        function showSystems(json) {
            if (json.flag == "true") {
                var options = "";
                for (var i = 0; i < json.o.length; i++) {
                    var option = json.o[i];
                    options += "<option value='" + option.id + "'>" + option.name + "</option>";
                }
                $(".systemIds").html(options);
            }
        }
    );
})
</script>
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
<input type="submit" value="刷所有索引">
</form>
<form action="<%=ctxPath %>/manage/refreshIndex.action" method="post">
SystemId：<select id="systemIds" class="systemIds form-control" name="systemId"></select>
<input type="submit" value="刷索引">
</form>
<form action="<%=ctxPath %>/manage/deleteIndex.action" method="post">
SystemId：<select id="systemIds" class="systemIds form-control" name="systemId"></select>
<input type="submit" value="删索引">
</form>
<form action="<%=ctxPath %>/manage/delete.action" method="post">
SystemId：<select id="systemIds" class="systemIds form-control" name="systemId"></select>
<input type="submit" value="删新闻系统里的数据">
</form>
<form action="<%=ctxPath %>/manage/updateStatus.action" method="post">
SystemId：<select id="systemIds" class="systemIds form-control" name="systemId"></select>
<input type="submit" value="更新审核状态（包括本系统和新闻系统）">
</form>
<hr>
<a href="<%=ctxPath %>/manage/tool/manageRole.jsp">管理知识库权限</a>
<hr>
<a href="<%=ctxPath %>/manage/tool/clearcache.jsp">缓存清理</a>
</body>
</html>