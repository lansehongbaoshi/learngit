<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String ctxPath = request.getContextPath();
%>
<link href="<%=ctxPath%>/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="<%=ctxPath%>/umeditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=ctxPath%>/umeditor/umeditor.min.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/umeditor/lang/zh-cn/zh-cn.js"></script>
<script>
$(function(){
    $.getJSON("/htgl/system/listSystem.action",
        function showSystems(json){
            if(json.flag=="true"){
              var options = "";
               for(var i=0;i<json.o.length;i++){
                var option = json.o[i];
                options+="<option value='"+option.id+"'>"+option.name+"</option>";
               }
               $("#systemIds").html(options);
            }
        }
    );
    $("#systemIds").change(function(){
    	$.getJSON("/view/getKnowledgeList.action?systemId="+$(this).val()+"&tagId=normal",
  	        function showTags(json){
  	            if(json.flag=="true"){
  	              var options = "";
  	              var viewTagVOs = json.o.viewTagVOs;
  	               for(var i=0;i<viewTagVOs.length;i++){
  	                var option = viewTagVOs[i];
  	                options+="<option value='"+option.name+"'>"+option.description+"</option>";
  	               }
  	               $("#tagName").html(options);
  	            }
  	        }
  	    );
    });
    $("#modifyBtn").click(function(){
    	var html = editor.getContent();
	    $("#content").val(html);
	    $("#myform").submit();
    });
})
</script>
<div>
<form id="myform" action="<%=ctxPath%>/htgl/addKnowledge.action" method="post" enctype="multipart/form-data">
系统：<select id="systemIds" name="systemId">
</select>
<input type="hidden" id="content" name="content" value="">
<p>标题：<input id="title" type="text" name="title" style="width: 400px;" value=""></p>
<p>标签：<select id="tagName" name="tagName"></select></p>
<p>关键字：<input id="keywords" type="text" name="keywords" style="width: 200px;" value=""></p>
<p>热点度：<input id="sort" type="text" name="sort" style="width: 100px;" value=""></p>
</form></div>
<script type="text/plain" id="myEditor" style="width:1000px;height:240px;">
</script>
<div class="clear"></div>
<div><input id="modifyBtn" type="button" value="新增"></div>
<script type="text/javascript">
   var editor = UM.getEditor('myEditor',{});
</script>