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
function synchronizationIndex(){
    $.getJSON("<%=ctxPath %>/htgl/robot/set/synchronizationIndex.action",function(date){
    	if(date.flag=="true"){
    		alert("数据同步成功！");
    	}
    });
}

function delRobotIndex(){
    $.getJSON("<%=ctxPath %>/htgl/robot/set/delRobotIndex.action",function(date){
        if(date.flag=="true"){
            alert("索引删除成功！");
        }
    });
}


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
<h2>机器人配置</h2>
<div class="row">
  <p style="font-weight:bold;">说明：支持txt格式上传，txt里问答各占一行，问答之间隔一空白行。一个提问多种回答的，可以按相同的提问不同的回答写多个。上传完毕后会自动建立上传对话的索引。</p>
    <form name="" action="<%=ctxPath %>/htgl/robot/set/updateCommit.action" method="post" onsubmit="" enctype="multipart/form-data">
      <div class="col-xs-12">
        文件：<input type="file" name="file">
        <input type="submit" value="提交">
      </div>
    </form>
    <br>
    <div >
        <a href="javascript:void(0)" onclick="synchronizationIndex();" target="_self" > 
            <span >同步数据库和索引</span>
        </a>
        &nbsp;&nbsp;
        <a href="javascript:void(0)" onclick="delRobotIndex();" target="_self" > 
            <span >刪除索引索引</span>
        </a> 
     </div>
  </div>
<hr>
<a href="<%=ctxPath %>/manage/tool/manageRole.jsp">管理知识库权限</a>
<hr>
<a href="<%=ctxPath %>/manage/tool/clearcache.jsp">缓存清理</a>
</body>
</html>